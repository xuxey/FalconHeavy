package com.xuxe.falconHeavy.commands.utilities;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.entities.Member;

import java.time.OffsetDateTime;

public class JoinDateCommand extends Command {
    public JoinDateCommand() {
        this.name = "joindate";
        this.aliases = new String[]{"join", "jd"};
        this.help = "Gets most recent join date for a member in the server.";
        this.category = Category.Utilities;
        this.syntax = "joindate [@mention]";
    }

    @Override
    public void run(CommandTrigger trigger) {
        Member member = trigger.getGuild().getMember(trigger.getAuthor());
        if (trigger.getArgs().length != 0) {
            member = trigger.getMessage().getMentionedMembers().get(0);
        }
        OffsetDateTime date = null;
        if (member == null) {
            reactFail();
            return;
        }
        date = member.getTimeJoined();
        trigger.respond(member.getAsMention() + "'s most recent join date is: " + date.getDayOfMonth() + " " + date.getMonth() + ", " + date.getYear());
    }
}
