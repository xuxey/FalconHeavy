package com.xuxe.falconHeavy.commands.moderation;

import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.Permission;

public class BanCommand extends Command {
    public BanCommand() {
        this.name = "ban";
        this.aliases = new String[]{"BAN", "Ban"};
        this.help = "Bans mentioned account from the server";
        this.category = "Moderation";
        this.userPermissions = new Permission[]{Permission.BAN_MEMBERS};
        this.syntax = "ban @user/id";
    }

    @Override
    public void run(CommandTrigger trigger) {

        trigger.respond("lol works");
        return;
        /*if (trigger.getGuild() == null) {
            trigger.respond("You must run this command in a server");
            return;
        }
        Member author = trigger.getMessage().getMember();

        if (!author.hasPermission(Permission.BAN_MEMBERS)) {
            trigger.respond("You don't have permission to ban!");
            return;
        }
        List<Member> mentionedMembers = trigger.getMessage().getMentionedMembers();
        if (mentionedMembers.isEmpty()) {
            trigger.respond("You must mention who you want to ban");
            return;
        }
        Member bannedUser = mentionedMembers.get(0);
        try {
            //trigger.getGuild().ban (bannedUser).queue(success -> trigger.respond(bannedUser.getEffectiveName() + " has been shown the door."), error -> trigger.respond("Cannot kick this person."));
        } catch (Exception e) {
            trigger.respond("Cannot kick this person.");
        }*/
    }
}
