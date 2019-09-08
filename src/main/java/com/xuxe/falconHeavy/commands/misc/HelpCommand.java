package com.xuxe.falconHeavy.commands.misc;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.CommandHandler;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class HelpCommand extends Command {
    public HelpCommand() {
        this.name = "help";
        this.help = "Provides help related to commands";
        this.privateAccessible = true;
        this.syntax = "help [Command Name]";
        this.extraHelp = "Provides a categorized list of commands, as well as specific help for each command";
    }

    @Override
    public void run(CommandTrigger trigger) {
        EmbedBuilder helpBuilder = new EmbedBuilder();
        if (trigger.getArgs().length == 0)
            helpBuilder = getFullHelp();
        else
            helpBuilder = getHelp(trigger.getArgs(0));
        trigger.respond(helpBuilder.build());
    }

    private EmbedBuilder getFullHelp() {
        EmbedBuilder builder = new EmbedBuilder().setTitle("Commands available: ");
        HashMap<String, String> categories = CommandHandler.getCategories();
        for (Map.Entry<String, String> entry : categories.entrySet()) {
            builder.addField(entry.getKey(), entry.getValue(), false);
        }
        builder.setColor(Color.GRAY);
        return builder;
    }

    private EmbedBuilder getHelp(String commandName) {
        Command command = CommandHandler.getCommands().get(commandName);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(Character.toUpperCase(command.getName().charAt(0)) + command.getName().substring(1));
        builder.setColor(Color.DARK_GRAY);
        builder.setFooter(command.getCategory() + " | Cooldown: " + ((command.getCooldown()[0] == 0) ? "None" : command.getCooldown()[0]));
        if (!command.getExtraHelp().isEmpty())
            builder.setDescription(command.getExtraHelp());
        else
            builder.setDescription(command.getHelp());
        if (!command.getSyntax().isEmpty())
            builder.appendDescription("```yaml\n" + FalconHeavy.getConfig().prefix + command.getSyntax() + "```");
        if (!command.getHelpImageLink().isEmpty())
            builder.setImage(command.getHelpImageLink());
        return builder;
    }
}
