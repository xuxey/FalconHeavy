package com.xuxe.falconHeavy.framework.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandInterface {
    void addCommand(Command command);
    void onCommand(String commandName, MessageReceivedEvent event);
}
