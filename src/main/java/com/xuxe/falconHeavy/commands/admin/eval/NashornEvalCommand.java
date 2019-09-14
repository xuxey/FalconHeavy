package com.xuxe.falconHeavy.commands.admin.eval;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class NashornEvalCommand extends Command {
    private ScriptEngine engine;

    public NashornEvalCommand() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            engine.eval("var imports = new JavaImporter(" +
                    "java.io," +
                    "java.lang," +
                    "java.util," +
                    "Packages.net.dv8tion.jda.core," +
                    "Packages.net.dv8tion.jda.core.entities," +
                    "Packages.net.dv8tion.jda.core.entities.impl," +
                    "Packages.net.dv8tion.jda.core.managers," +
                    "Packages.net.dv8tion.jda.core.managers.impl," +
                    "Packages.net.dv8tion.jda.core.utils);");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        this.name = "nashorn";
        this.help = "evaluates arguments passed to it.";
        this.syntax = "nashorn <javascript>";
        this.help = "Evaluates javascript";
        this.extraHelp = help + " using Nashorn Engine";
        this.rank = UserRank.ADMIN;
        this.category = Category.Admin;
    }

    @Override
    public void run(CommandTrigger trigger) {
        String messageContent = trigger.getMessage().getContentDisplay();
        if (!trigger.getAuthor().getId().equalsIgnoreCase(FalconHeavy.getConfig().getOwnerID())) {
            trigger.respond("Sorry, this command is owner only!");
            return;
        }

        try {
            engine.put("trigger", trigger);
            engine.put("message", trigger.getMessage());
            engine.put("channel", trigger.getChannel());
            engine.put("args", trigger.getArgs());
            engine.put("api", trigger.getJDA());
            if (!trigger.isPrivate()) {
                engine.put("guild", trigger.getGuild());
                engine.put("member", trigger.getGuild().getMember(trigger.getUser()));
            }

            Object out = engine.eval(
                    "(function() {" +
                            "with (imports) {" +
                            messageContent.substring(messageContent.indexOf(' ')).trim() +
                            "}" +
                            "})();");
            trigger.getMessage().addReaction("\u2705").queue();
        } catch (Exception e1) {
            reactFail();
            trigger.respond(e1.getMessage());
        }
    }
}
