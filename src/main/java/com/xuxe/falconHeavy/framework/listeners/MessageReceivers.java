package com.xuxe.falconHeavy.framework.listeners;

import com.xuxe.falconHeavy.framework.command.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivers extends ListenerAdapter {
    private CommandHandler handler;

    public MessageReceivers(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String label = "";
        //ignore spaces before command label because its user friendly
        for (String element : event.getMessage().getContentRaw().split(" ")) {
            if (element.equalsIgnoreCase("//") || element.equalsIgnoreCase(""))
                continue;
            else
                label = element;
            break;
        }
        handler.onCommand(label, event);
    }
}
