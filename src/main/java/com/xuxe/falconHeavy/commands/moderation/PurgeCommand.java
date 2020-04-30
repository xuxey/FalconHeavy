package com.xuxe.falconHeavy.commands.moderation;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;

public class PurgeCommand extends Command {
    public PurgeCommand() {
        this.name = "purge";
        this.syntax = "purge <number>";
        this.userPermissions = new Permission[]{Permission.MESSAGE_MANAGE};
        this.category = Category.Moderation;
        this.aliases = new String[]{"clear"};
        this.help = "Deletes a number of recent messages in a channel";
        this.privateAccessible = false;
        this.botPermissions = new Permission[]{Permission.MESSAGE_MANAGE};
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        MessageChannel messageChannel = trigger.getChannel();
        if (trigger.getArgs().length == 0)
            throw new IncorrectSyntaxException();
        int count = Integer.parseInt(trigger.getArgs(0));
        trigger.getChannel().getHistoryBefore(trigger.getMessage().getId(), count).queue(messageHistory -> {
            messageChannel.purgeMessages(messageHistory.getRetrievedHistory());
            trigger.getMessage().delete().queue();
        }, fail -> {
            reactFail();
            trigger.respond("Could not complete purge: " + fail.getLocalizedMessage());
        });
    }
}
