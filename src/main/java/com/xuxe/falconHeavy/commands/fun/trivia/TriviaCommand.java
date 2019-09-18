package com.xuxe.falconHeavy.commands.fun.trivia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.database.points.Points;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.command.waiter.EventWaiter;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.awt.*;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TriviaCommand extends Command {
    private EventWaiter waiter;

    public TriviaCommand() {
        this.name = "trivia";
        this.aliases = new String[]{"tr", "triv"};
        this.help = "Asks a trivia question.";
        this.cooldown = new int[]{10, 5};
        this.cooldownScope = CooldownScope.USER;
        this.waiter = FalconHeavy.getWaiter();
        this.category = Category.Fun;
        this.syntax = "trivia";
    }

    @Override
    public void run(CommandTrigger trigger) {
        Points points = new Points();
        EmbedBuilder trivia = new EmbedBuilder().setTitle("**Tr?via**");
        TriviaInstance triviaInstance = generate();
        if (triviaInstance == null) {
            trigger.respond("Oops, something went wrong!");
            return;
        }
        trivia.setColor(Color.DARK_GRAY);
        trivia.setDescription(triviaInstance.getQuestion());
        if (triviaInstance.getType().equals("multiple")) {
            trivia.appendDescription("\n" + triviaInstance.getAll_answers());
        } else {
            trivia.appendDescription("True or False? ");
        }
        trivia.setFooter("Level: " + triviaInstance.getDifficulty() + " | " + triviaInstance.getCategory(), null);
        String answer = triviaInstance.getCorrect_answer();
        trigger.getChannel().sendMessage(trivia.build()).queue
                (
                        e -> waiter.waitForEvent(
                                MessageReceivedEvent.class,
                                evt -> evt.getAuthor().equals(trigger.getAuthor()) && evt.getChannel().equals(trigger.getChannel()),
                                evt -> {
                                    String content = evt.getMessage().getContentRaw();
                                    if (content.toLowerCase().contains(answer.toLowerCase()) || content.toLowerCase().equalsIgnoreCase("" + triviaInstance.getCorrectAnswerLetter())) {
                                        points.addPoints(evt.getAuthor().getId(), triviaInstance.getPoints());
                                        trigger.getChannel().sendMessage("Correct! You have won: " + triviaInstance.getPoints() + " point(s)!").queue();
                                    } else {
                                        //noinspection OptionalGetWithoutIsPresent
                                        if (new Random().ints(1, (10 + 1)).limit(1).findFirst().getAsInt() > 2)
                                            trigger.respond("Wrong! The answer was: " + answer + ".");
                                        else {
                                            trigger.respond("Wrong!The answer was: " + answer + ". You lose a point :(");
                                            points.removePoints(evt.getAuthor().getId(), 1);
                                        }
                                    }
                                },
                                15,
                                TimeUnit.SECONDS,
                                () -> trigger.respond("Sorry, you took too long.")),
                        e -> {
                        }
                );
    }

    private TriviaInstance generate() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("https://opentdb.com/api.php?amount=1").build();
            JSONObject jsonObject = (JSONObject) JSONValue.parse(Objects.requireNonNull(client.newCall(request).execute().body()).string());
            String arrayS = jsonObject.get("results").toString();
            arrayS = arrayS.substring(1, arrayS.length() - 1);

            Gson gson = new GsonBuilder().create();
            return gson.fromJson(arrayS, TriviaInstance.class);
        } catch (Exception e) {
            return null;
        }
    }
}
