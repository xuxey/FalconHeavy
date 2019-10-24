package com.xuxe.falconHeavy.commands.moderation;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import com.xuxe.falconHeavy.utils.Manipulators;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class GetPermsCommand extends Command {
    public GetPermsCommand() {
        this.name = "getperms";
        this.aliases = new String[]{"perms", "findperms", "getperm"};
        this.category = Category.Moderation;
        this.help = "Lists the perms of a mentioned user";
        this.syntax = "getperms <mention/ID>";
        this.privateAccessible = false;
        this.cooldown = new int[]{5, 5};
        this.cooldownScope = CooldownScope.USER;
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        Member member = trigger.getGuild().getMember(Manipulators.getIntendedUsers(trigger.getMessage()).get(0));
        assert member != null;
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle(member.getEffectiveName() + "'s Permissions");
        StringBuilder stringBuilder = new StringBuilder();
        for (Permission p : member.getPermissions()) {
            stringBuilder.append(p.getName()).append(", ");
        }
        stringBuilder.subSequence(0, stringBuilder.lastIndexOf(",") - 1);
        embedBuilder.setDescription(stringBuilder);
        trigger.respond(embedBuilder.build());
    }
}
