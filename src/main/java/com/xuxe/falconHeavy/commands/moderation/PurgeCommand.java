package com.xuxe.falconHeavy.commands.moderation;

import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import net.dv8tion.jda.api.entities.MessageChannel;

public class PurgeCommand extends Command {
    public PurgeCommand() {
        this.name = "purge";
        this.syntax = "purge <number>";
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        MessageChannel messageChannel = trigger.getChannel();
        if (trigger.getArgs().length == 0)
            throw new IncorrectSyntaxException();
        messageChannel.getHistory().retrievePast(Integer.parseInt(trigger.getArgs()[0]) + 1).queue(
                messageChannel::purgeMessages, failure -> {
                    reactFail();
                    trigger.respond("Cannot perform this action: " + failure.getMessage());
                }
        );
        trigger.getMessage().delete().queue();
    }
}