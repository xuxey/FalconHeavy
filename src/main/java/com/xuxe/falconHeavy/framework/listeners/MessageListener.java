package com.xuxe.falconHeavy.framework.listeners;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.framework.command.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
        if (event.getMessage().getMentionedUsers().contains(event.getJDA().getSelfUser()))
            event.getChannel().sendMessage("My prefix is `>`. To learn more commands, use `>help`").queue();
        String content = event.getMessage().getContentRaw();
        if (!content.startsWith(FalconHeavy.getConfig().getPrefix()) || content.charAt(1) == '>')
            return;
        String label = "";
        //ignore spaces before command label because its user friendly
        for (String element : content.split(" ")) {
            if (element.equalsIgnoreCase(FalconHeavy.getConfig().getPrefix()) || element.equalsIgnoreCase(""))
                continue;
            else
                label = element;
            break;
        }
        label = label.replace(FalconHeavy.getConfig().prefix, "");
        FalconHeavy.setJda(event.getJDA());
        new CommandHandler().onCommand(label, event);
    }
}
