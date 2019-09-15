package com.xuxe.falconHeavy.commands.admin;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class PresenceCommand extends Command {
    public PresenceCommand() {
        this.name = "presence";
        this.aliases = new String[]{"game"};
        this.category = Category.Admin;
        this.rank = UserRank.ADMIN;
        this.syntax = "presence streaming <link> <stuff>\n" +
                FalconHeavy.getConfig().prefix + "presence watching <stuff>" +
                FalconHeavy.getConfig().prefix + "presence playing <stuff>";
        this.help = "Changes Falcon Heavy's rich presence";
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        String[] args = trigger.getArgs();
        if (args.length == 0)
            throw new IncorrectSyntaxException();
        switch (args[0]) {
            case "streaming":
            case "stream":
            case "s":
                trigger.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.streaming(trigger.getString().split(args[1])[1], args[1]));
                break;
            case "watching":
            case "watch":
            case "w":
                trigger.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.watching(trigger.getString().split(args[0])[1]));
                break;
            case "playing":
            case "play":
            case "p":
                trigger.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.playing(trigger.getString().split(args[0])[1]));
                break;
            case "listening":
            case "listen":
            case "l":
                trigger.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.listening(trigger.getString().split(args[0])[1]));
                break;
        }
        reactSuccess();
    }
}
