package com.xuxe.falconHeavy.framework.command;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.constants.Responses;
import com.xuxe.falconHeavy.database.framework.DBChecks;
import com.xuxe.falconHeavy.database.framework.DBGuildSettings;
import com.xuxe.falconHeavy.framework.command.cooldown.Cooldown;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.Manipulators;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Objects;

public class CommandHandler implements CommandInterface {
    private static HashMap<String, Command> commands = new HashMap<>();
    private static HashMap<Category, String> categories = new HashMap<>();

    public static void removeCommand(String command) {
        commands.remove(command.toLowerCase());
    }

    public static HashMap<Category, String> getCategories() {
        return categories;
    }

    public static HashMap<String, Command> getCommands() {
        return commands;
    }

    public void addCommand(Command command) {
        if (command.getName().equals("")) return;
        commands.put(command.getName().trim().toLowerCase(), command);
        DBGuildSettings.addColumn(command.getName().trim());
        String[] aliases = command.aliases;
        Cooldown.addStartupCommand(command);
        for (String alias : aliases) {
            if (!commands.containsKey(alias))
                commands.put(alias.trim().toLowerCase(), command);
            else
                FalconHeavy.logger.info("Did not add command " + alias + ", Duplicate key.");
        }
        String categoryList;
        if (categories.containsKey(command.getCategory())) {
            categoryList = categories.get(command.getCategory());
            categoryList += ", " + command.getName();
            categories.replace(command.getCategory(), categoryList);
        } else {
            categoryList = command.getName();
            categories.put(command.getCategory(), categoryList);
        }
        //System.out.println("Registered command: " + command.getName());
    }

    public void onCommand(String commandName, MessageReceivedEvent event) {
        if (!commands.containsKey(commandName.toLowerCase().trim()))
            return;
        Command command = commands.get(commandName.toLowerCase());
        CommandTrigger trigger = new CommandTrigger(event);
        if (!DBChecks.userExists(event.getAuthor().getId()))
            DBChecks.makeUser(event.getAuthor().getId());
        // Blacklist Check
        if (DBChecks.isBlacklisted(trigger.getUser().getId()))
            return;
        // private channel check
        if (!command.privateAccessible && event.getChannelType().equals(ChannelType.PRIVATE)) {
            event.getPrivateChannel().sendMessage(Responses.NO_DM_ALLOWED).queue();
            return;
        }
        // guild settings check
        if (DBGuildSettings.isDisabled(command, trigger)) {
            event.getChannel().sendMessage(Responses.GUILD_DISABLED_COMMAND).queue();
            return;
        }
        // Rank check
        if (!Manipulators.rankCheck(command.getRank(), trigger.getRank())) {
            event.getChannel().sendMessage("You must be rank " +
                    command.getRank().toString().charAt(0) + command.getRank().toString().substring(1).toLowerCase() +
                    " or above to use this command.").queue();
            command.reactFail(trigger.getMessage());
            return;
        }
        // Permissions check
        if (!event.getChannelType().equals(ChannelType.PRIVATE)) {
            try {
                EnumSet<Permission> permissions = Objects.requireNonNull(event.getMember(), "Member not found").getPermissions();
                for (Permission permission : command.userPermissions) {
                    if (!permissions.contains(permission)) {
                        event.getChannel().sendMessage(Responses.NO_USER_PERMISSION + permission.getName()).queue();
                        return;
                    }
                }
            } catch (NullPointerException npe) {
                event.getChannel().sendMessage(Responses.ERROR).queue();
            }
        }
        // Cooldown Check
        int timeLeft = Cooldown.process(command, trigger);
        if (timeLeft > 0) {
            event.getChannel().sendMessage(String.format(Responses.COOLDOWN, timeLeft)).queue();
            return;
        }
        command.checkRun(trigger);
    }

    public static String getNameFromAlias(String alias) {
        if (!commands.containsKey(alias.toLowerCase()))
            return "";
        return commands.get(alias).getName();
    }
}
