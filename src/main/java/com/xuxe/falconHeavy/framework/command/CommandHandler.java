package com.xuxe.falconHeavy.framework.command;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.constants.Responses;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Objects;

public class CommandHandler implements CommandInterface {
    private static HashMap<String, Command> commands;

    @Override
    public HashMap<String, Command> getCommands() {
        return commands;
    }

    @Override
    public void addCommand(Command command) {
        if (command.name.equals("")) return;
        commands.put(command.name.trim().toLowerCase(), command);
        String[] aliases = command.aliases;
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
        Command command = commands.get(commandName.toLowerCase());
        //todo blacklist check

        //todo cooldown check

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
        command.checkRun(new CommandTrigger(event));

    }
}
