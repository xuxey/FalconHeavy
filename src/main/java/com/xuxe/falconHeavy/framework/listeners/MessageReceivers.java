package com.xuxe.falconHeavy.framework.listeners;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.framework.command.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivers extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
        if (!event.getMessage().getContentRaw().startsWith(FalconHeavy.getConfig().getPrefix()))
            return;
        String label = "";
        //ignore spaces before command label because its user friendly
        for (String element : event.getMessage().getContentRaw().split(" ")) {
            if (element.equalsIgnoreCase(FalconHeavy.getConfig().getPrefix()) || element.equalsIgnoreCase(""))
                continue;
            else
                label = element;
            break;
        }
        label = label.replace(FalconHeavy.getConfig().prefix, "");
        new CommandHandler().onCommand(label, event);
    }
}
