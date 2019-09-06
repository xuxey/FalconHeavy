package com.xuxe.falconHeavy.commands.misc;

import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;

public class PingCommand extends Command {
    public PingCommand() {
        this.name = "ping";
        this.aliases = new String[]{"pong", "peng"};
        this.cooldown = new int[]{30, 1, 1};
        this.cooldownScope = CooldownScope.USER;
        this.help = "Gets falcon heavy's response time";
        this.privateAccessible = false;
    }

    @Override
    public void run(CommandTrigger trigger) {
        trigger.getChannel().sendMessage("Pong").queue();
    }
}
