package com.xuxe.falconHeavy.commands.utilities;

import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import com.xuxe.falconHeavy.utils.Manipulators;
import net.dv8tion.jda.api.EmbedBuilder;

public class AvatarCommand extends Command {
    public AvatarCommand() {
        this.name = "avatar";
        this.aliases = new String[]{"av", "pfp"};
        this.syntax = "avatar <ID/Mention>";
        this.help = "Gets the profile picture of a mentioned user";
        this.privateAccessible = false;
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        EmbedBuilder builder = new EmbedBuilder().setImage(Manipulators.getIntendedUsers(trigger.getMessage()).get(0).getAvatarUrl());
        trigger.respond(builder.build());
    }
}
