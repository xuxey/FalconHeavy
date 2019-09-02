package com.xuxe.falconHeavy.framework.command;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.constants.Responses;
import com.xuxe.falconHeavy.database.framework.DBChecks;
import com.xuxe.falconHeavy.database.framework.DBGuildSettings;
import com.xuxe.falconHeavy.framework.command.cooldown.Cooldown;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Objects;

public class CommandHandler implements CommandInterface {
    private static HashMap<String, Command> commands = new HashMap<>();

    @Override
    public HashMap<String, Command> getCommands() {
        return commands;
    }

    @Override
    public void addCommand(Command command) {
        if (command.getName().equals("")) return;
        commands.put(command.getName().trim().toLowerCase(), command);
        String[] aliases = command.aliases;
        System.out.println("Registered command: " + command.getName());
        for (String alias : aliases) {
            if (!commands.containsKey(alias))
                commands.put(alias.trim().toLowerCase(), command);
            else
                FalconHeavy.logger.info("Did not add command " + alias + ", Duplicate key.");
        }
    }

    @Override
    public void removeCommand(String command) {
        commands.remove(command.toLowerCase());
    }

    @Override
    public void onCommand(String commandName, MessageReceivedEvent event) {
        if (!commands.containsKey(commandName.toLowerCase().trim()))
            return;
        Command command = commands.get(commandName.toLowerCase());
        CommandTrigger trigger = new CommandTrigger(event);
        // Blacklist Check
        if (DBChecks.isBlacklisted(trigger.getUser().getId()))
            return;
        // Cooldown Check
        if (Cooldown.process(command, trigger) > 0) {
            event.getChannel().sendMessage(Responses.COOLDOWN).queue();
            return;
        }
        // guild settings check
        if (DBGuildSettings.isDisabled(command, trigger)) {
            event.getChannel().sendMessage(Responses.GUILD_DISABLED_COMMAND).queue();
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
        // private channel check
        if (!command.privateAccessible && event.getChannelType().equals(ChannelType.PRIVATE))
            event.getPrivateChannel().sendMessage(Responses.NO_DM_ALLOWED).queue();
        command.checkRun(trigger);
    }
}
