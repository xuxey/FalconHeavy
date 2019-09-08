package com.xuxe.falconHeavy.commands.misc;

import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.MessageBuilder;

import java.time.temporal.ChronoUnit;

public class PingCommand extends Command {
    public PingCommand() {
        this.name = "ping";
        this.aliases = new String[]{"pong", "peng"};
        this.cooldown = new int[]{3, 1};
        this.cooldownScope = CooldownScope.USER;
        this.help = "Gets falcon heavy's response time";
        this.syntax = "ping";
        this.extraHelp = "Gets Falcon Heavy's delay from the time that it receives the command to the time that it responds.";
        this.privateAccessible = true;
    }

    @Override
    public void run(CommandTrigger trigger) {
        trigger.respond(trigger.getEvent(), new MessageBuilder("Gateway Ping: " + trigger.getJda().getGatewayPing() + "ms\nResponse ping: ...").build(), m -> m.editMessage("Gateway Ping: " + trigger.getJda().getGatewayPing() + "ms\nResponse ping: " + trigger.getMessage().getTimeCreated().until(m.getTimeCreated(), ChronoUnit.MILLIS) + "ms").queue());
    }
}
