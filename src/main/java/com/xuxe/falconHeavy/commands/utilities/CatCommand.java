package com.xuxe.falconHeavy.commands.utilities;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public class CatCommand extends Command {
    public CatCommand() {
        this.name = "cat";
        this.aliases = new String[]{"kitty", "pussy", "pussycat"};
        this.help = "Gets a random picture of a cat";
        this.extraHelp = this.help + " from [here](http://aws.random.cat/meow)\n";
        this.syntax = "cat";
        this.cooldown = new int[]{6, 2,};
        this.cooldownScope = CooldownScope.USER;
        this.category = "Utility";
    }

    @Override
    public void run(CommandTrigger trigger) {
        MessageChannel messageChannel = trigger.getChannel();
        Unirest.get("http://aws.random.cat/meow").asJsonAsync(new Callback<JsonNode>() {
            @Override
            public void completed(HttpResponse<JsonNode> hr) {
                messageChannel.sendMessage(new EmbedBuilder()
                        .setColor(Color.BLACK)
                        .setImage(hr.getBody().getObject().getString("file"))
                        .build()).queue();
            }

            @Override
            public void failed(UnirestException ue) {
                messageChannel.sendMessage("The cats are asleep right now!").queue();
            }

            @Override
            public void cancelled() {
                messageChannel.sendMessage("The cats don't like to see your face :(").queue();
            }
        });
    }
}


