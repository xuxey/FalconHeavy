package com.xuxe.falconHeavy.commands.utilities;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import com.xuxe.falconHeavy.utils.Manipulators;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

public class AvatarCommand extends Command {
    public AvatarCommand() {
        this.name = "avatar";
        this.aliases = new String[]{"av", "pfp"};
        this.syntax = "avatar [ID/Mention]";
        this.help = "Gets the profile picture of a mentioned user";
        this.privateAccessible = false;
        this.category = Category.Utilities;
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        ArrayList<User> userList = Manipulators.getIntendedUsers(trigger.getMessage());
        User users = userList.size() > 0 ? userList.get(0) : trigger.getUser();
        EmbedBuilder builder = new EmbedBuilder().setImage(users.getAvatarUrl());
        trigger.respond(builder.build());
    }
}
