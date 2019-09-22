package com.xuxe.falconHeavy.commands.utilities;

import com.jagrosh.jlyrics.Lyrics;
import com.jagrosh.jlyrics.LyricsClient;
import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.constants.Responses;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.command.waiter.EventWaiter;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LyricsCommand extends Command {
    private EventWaiter waiter;

    public LyricsCommand() {
        this.waiter = FalconHeavy.getWaiter();
        this.name = "lyrics";
        this.syntax = "lyrics <song name>";
        this.cooldown = new int[]{5, 1};
        this.category = Category.Utilities;
        this.help = "Gets the lyrics to any song";
        this.cooldownScope = CooldownScope.USER;
    }

    @Override
    public void run(CommandTrigger trigger) {
        try {
            trigger.getChannel().sendTyping().queue();
            LyricsClient client = new LyricsClient();
            Lyrics lyric = client.getLyrics(trigger.getString()).get();
            System.out.println(lyric.getContent());
            int splitLength = 600;
            trigger.getChannel().sendMessage(getEmbed(1, lyric, splitLength)).queue(success -> {
                addReactions(success, lyric, splitLength);
                paginate(success, lyric, trigger, splitLength, System.currentTimeMillis() + (1000 * 60 * 5));
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
            trigger.respond("That song is either unavailable or you may have incorrectly spelled it.");
        } catch (Exception e) {
            trigger.respond(Responses.ERROR);
            e.printStackTrace();
        }
    }

    private void paginate(@NotNull Message success, Lyrics lyric, CommandTrigger trigger, int splitLength, long end) {
        waiter.waitForEvent(
                MessageReactionAddEvent.class,
                reactAddEvent -> (end > System.currentTimeMillis()) && (reactAddEvent.getMessageId().equals(success.getId())) && (reactAddEvent.getUser().getId().equals(trigger.getUser().getId())),
                reactAddEvent -> {
                    int page = getPageFromEmote(reactAddEvent.getReaction().getReactionEmote().getName());
                    success.editMessage(getEmbed(page, lyric, splitLength)).queue();
                    reactAddEvent.getReaction().removeReaction(trigger.getUser()).queue();
                    paginate(success, lyric, trigger, splitLength, end);
                },
                end - System.currentTimeMillis(),
                TimeUnit.MILLISECONDS,
                () -> success.clearReactions().queue()
        );
    }

    private MessageEmbed getEmbed(int page, Lyrics lyric, int splitLength) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(lyric.getTitle().replace('"', ' '), lyric.getURL());
        embedBuilder.setColor(Color.GRAY);
        embedBuilder.setDescription(getSplitString(lyric.getContent(), splitLength).get(page - 1));
        embedBuilder.setFooter(lyric.getAuthor().replace("Lyrics", "") + " | " + lyric.getSource());
        return embedBuilder.build();
    }

    private List<String> getSplitString(String content, int splitLength) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= (content.length() / splitLength); i++) {
            list.add(content.substring(i * splitLength, Math.min((i * splitLength) + splitLength, content.length())));
        }
        System.out.println(list.toString());
        return list;
    }

    private void addReactions(Message message, Lyrics lyric, int splitLength) {
        int pages = getSplitString(lyric.getContent(), splitLength).size();
        for (int i = 1; i <= pages; i++) {
            message.addReaction(getEmoteFromPage(i)).queue();
        }
    }

    private String getEmoteFromPage(int page) {
        switch (page) {
            case 1:
                return "\u0031\u20E3";
            case 2:
                return "\u0032\u20E3";
            case 3:
                return "\u0033\u20E3";
            case 4:
                return "\u0034\u20E3";
            case 5:
                return "\u0035\u20E3";
            case 6:
                return "\u0036\u20E3";
            case 7:
                return "\u0037\u20E3";
            case 8:
                return "\u0038\u20E3";
            case 9:
                return "\u0039\u20E3";
            case 0:
            case 10:
            default:
                return "\u0030";
        }
    }

    private int getPageFromEmote(String emote) {
        switch (emote) {
            case "\u0031\u20E3":
                return 1;
            case "\u0032\u20E3":
                return 2;
            case "\u0033\u20E3":
                return 3;
            case "\u0034\u20E3":
                return 4;
            case "\u0035\u20E3":
                return 5;
            case "\u0036\u20E3":
                return 6;
            case "\u0037\u20E3":
                return 7;
            case "\u0038\u20E3":
                return 8;
            case "\u0039\u20E3":
                return 9;
            case "\u0030\u20E3":
            default:
                return 0;
        }
    }
}
