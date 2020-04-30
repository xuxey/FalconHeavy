package com.xuxe.falconHeavy.commands.misc;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.commands.Category;
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
        EmbedBuilder helpBuilder;
        if (trigger.getArgs().length == 0)
            helpBuilder = getFullHelp();
        else
            helpBuilder = getCommandHelp(trigger.getArgs(0));
        if (helpBuilder == null) {
            reactFail();
            return;
        }
        trigger.respond(helpBuilder.build());
    }

    private EmbedBuilder getFullHelp() {
        EmbedBuilder builder = new EmbedBuilder().setTitle("Commands available: ");
        HashMap<Category, String> categories = CommandHandler.getCategories();
        for (Map.Entry<Category, String> entry : categories.entrySet()) {
            builder.addField(entry.getKey().toString(), entry.getValue(), false);
        }
        builder.setColor(Color.GRAY);
        return builder;
    }

    private EmbedBuilder getCommandHelp(String commandName) {
        Command command = CommandHandler.getCommands().get(commandName.toLowerCase());
        if (command == null)
            return null;
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(Character.toUpperCase(command.getName().charAt(0)) + command.getName().substring(1));
        builder.setColor(Color.DARK_GRAY);
        builder.setFooter(command.getCategory() + " | Cooldown: " + ((command.getCooldown()[0] == 0) ? "None" : command.getCooldown()[0]));
        builder.setDescription(command.getHelp());
        if (!command.getExtraHelp().isEmpty())
            builder.appendDescription("\n" + command.getExtraHelp());
        StringBuilder aliases = new StringBuilder();
        if (command.getAliases().length > 0)
            for (String s : command.getAliases()) {
                aliases.append('`').append(s).append("`, ");
            }
        if (!command.getSyntax().isEmpty())
            builder.appendDescription("```yaml\n" + FalconHeavy.getConfig().prefix + command.getSyntax() + "```");
        if (!command.getHelpImageLink().isEmpty())
            builder.setImage(command.getHelpImageLink());
        if (!aliases.toString().isEmpty())
            builder.appendDescription("**Aliases:** " + aliases.subSequence(0, aliases.length() - 2).toString());
        builder.appendDescription("\n`In syntax, [optional] <mandatory>`");
        return builder;
    }
}
