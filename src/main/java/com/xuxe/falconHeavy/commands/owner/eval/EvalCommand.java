package com.xuxe.falconHeavy.commands.owner.eval;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.requests.RestAction;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Shamelessly borrowed from HotLava: https://github.com/HotLava03/Baclava
 **/
public class EvalCommand extends Command {
    public EvalCommand() {
        this.name = "eval";
        this.rank = UserRank.ADMIN;
        this.help = "A nice eval based on Groovy Engine";
        this.syntax = "eval <code>";
        this.category = Category.Owner;
    }

    @Override
    public void run(CommandTrigger trigger) {
        if (!trigger.getUser().getId().equals(FalconHeavy.getConfig().getOwnerID())) {
            trigger.respond("Sorry admin, this is a dangerous command to grant, even to admins!");
            reactFail();
            return;
        }
        // Shortcut variables
        final Map<String, Object> shortcuts = new HashMap<>();

        shortcuts.put("jda", trigger.getJDA());
        shortcuts.put("event", trigger);
        shortcuts.put("trigger", trigger);
        shortcuts.put("e", trigger);

        shortcuts.put("channel", trigger.getChannel());
        shortcuts.put("server", trigger.getGuild());
        shortcuts.put("guild", trigger.getGuild());

        shortcuts.put("message", trigger.getMessage());
        shortcuts.put("msg", trigger.getMessage());
        shortcuts.put("author", trigger.getGuild().getMember(trigger.getUser()));
        shortcuts.put("bot", trigger.getJDA().getSelfUser());

        EvalComponent component = new EvalComponent();

        final Triple<Object, String, String> result = component.eval(
                shortcuts,
                Collections.emptyList(),
                trigger.getString()
        );

        if (result.getLeft() instanceof RestAction<?>)
            ((RestAction<?>) result.getLeft()).queue();

        final String out = result.toString().replace(",,)", "").replace("(", "");

        if (out.contains("ScriptException")) {
            trigger.getMessage().addReaction("\u274c").queue();
            trigger.getUser().openPrivateChannel().queue(c -> c.sendMessage(new EmbedBuilder()
                    .setTitle("Eval error")
                    .setColor(Color.RED)
                    .setDescription(out)
                    .setTimestamp(Instant.now())
                    .build()
            ).queue());
        } else if (!out.equals("null"))
            trigger.respond("```" + out + "```");
        else
            trigger.getMessage().addReaction("\u2705").queue();

    }
}
