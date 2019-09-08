package com.xuxe.falconHeavy.commands.misc;

import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.Permission;

public class PingCommand extends Command {
    public PingCommand() {
        this.name = "ping";
        this.aliases = new String[]{"pong", "peng"};
        this.cooldown = new int[]{30, 1, 1};
        this.cooldownScope = CooldownScope.USER;
        this.help = "Gets falcon heavy's response time";
        this.syntax = "ping";
        this.extraHelp = "Gets Falcon Heavy's delay from the time that it receives the command to the time that it responds.";
        this.privateAccessible = false;
        this.userPermissions = new Permission[]{Permission.MANAGE_SERVER, Permission.ADMINISTRATOR};
    }

    @Override
    public void run(CommandTrigger trigger) {
        trigger.getChannel().sendMessage("Pong").queue();
        trigger.getChannel().purgeMessages(trigger.getMessage());
    }
}
