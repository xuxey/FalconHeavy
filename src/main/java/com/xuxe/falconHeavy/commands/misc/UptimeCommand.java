package com.xuxe.falconHeavy.commands.misc;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import com.xuxe.falconHeavy.utils.Manipulators;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class UptimeCommand extends Command {
    public UptimeCommand() {
        this.name = "uptime";
        this.help = "Get the the bot uptime";
        this.category = Category.Miscellaneous;
        this.cooldown = new int[]{5, 5, 0};
        this.aliases = new String[]{"ut"};
        this.privateAccessible = true;
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        trigger.respond("FalconBot has been online for " + Manipulators.getTimeDisplay(rb.getUptime()));
    }
}
