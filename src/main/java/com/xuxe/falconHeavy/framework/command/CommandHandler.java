package com.xuxe.falconHeavy.framework.command;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

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

    public void onCommand(String commandName, MessageReceivedEvent event) {
        Command command = commands.get(commandName.toLowerCase());
        command.run(new CommandTrigger(event));
    }
}
