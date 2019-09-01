package com.xuxe.falconHeavy.framework.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

public interface CommandInterface {
    HashMap<String, Command> getCommands();

    void addCommand(Command command);

    void removeCommand(String command);

    void onCommand(String commandName, MessageReceivedEvent event);
}
