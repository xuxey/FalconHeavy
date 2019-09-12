package com.xuxe.falconHeavy.commands.moderation;

import com.xuxe.falconHeavy.constants.Responses;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.Manipulators;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BanCommand extends Command {
    public BanCommand() {
        this.name = "ban";
        this.help = "Bans a mentioned mentioned from the server";
        this.extraHelp = "Bans a user from the server, and deletes their messages from the past 7 days if specified as -hard";
        this.category = "Moderation";
        this.userPermissions = new Permission[]{Permission.BAN_MEMBERS};
        this.syntax = "ban @user1 @user2 [userId] ... [reason]";
        this.privateAccessible = false;
    }

    @Override
    public void run(CommandTrigger trigger) {
        Guild guild = trigger.getGuild();
        if (guild == null) {
            trigger.respond(Responses.NO_DM_ALLOWED);
            return;
        }
        String[] args = trigger.getArgs();
        ArrayList<Member> mentionedMembers = new ArrayList<Member>(trigger.getMessage().getMentionedMembers());
        StringBuilder reason = new StringBuilder();
        for (String s : args) {
            if (Manipulators.isMention(s)) continue;
            if (Manipulators.isValidId(s)) {
                mentionedMembers.add(trigger.getGuild().getMemberById(s));
                continue;
            }
            reason.append(s).append(" ");
        }
        boolean isHard = trigger.getString().contains("-hard");
        List<Member> failed = new ArrayList<>();
        List<Member> successful = new ArrayList<>();
        for (Member member : mentionedMembers) {
            try {
                member.ban(isHard ? 7 : 0, reason.toString()).queue();
                successful.add(member);
            } catch (Exception e) {
                failed.add(member);
            }
        }
        if (!successful.isEmpty()) {
            trigger.respond("**Banned the following user(s):** " + listStringIterator(successful) + ((!reason.toString().isEmpty()) ? " **for reason:** " + reason :
                    reason.append("By ").append(trigger.getUser().getName()).append("#").append(trigger.getUser().getDiscriminator()).append(" on ").append(Date.from(Instant.now()).toString())));
            reactSuccess();
        }
        if (failed.size() > 0)
            trigger.respond("Could not ban the following user(s): " + listStringIterator(failed));

    }

    private String listStringIterator(List<Member> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Member member : list) {
            stringBuilder.append('`').append(member.getEffectiveName()).append("#").append(member.getUser().getDiscriminator()).append("` ");
        }
        return stringBuilder.toString();
    }
}
