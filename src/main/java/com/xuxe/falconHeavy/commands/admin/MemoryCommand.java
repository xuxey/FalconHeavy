package com.xuxe.falconHeavy.commands.admin;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;

public class MemoryCommand extends Command {
    public MemoryCommand() {
        this.name = "memory";
        this.aliases = new String[]{"mem", "ram"};
        this.help = "Gets the memory usage statistics";
        this.rank = UserRank.ADMIN;
        this.category = Category.Admin;
    }

    @Override
    public void run(CommandTrigger trigger) {
        int dataSize = 1024 * 1024;
        String builder = "Used Memory   : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / dataSize + " MB" +
                "Free Memory   : " + Runtime.getRuntime().freeMemory() / dataSize + " MB" +
                "Total Memory  : " + Runtime.getRuntime().totalMemory() / dataSize + " MB" +
                "Max Memory    : " + Runtime.getRuntime().maxMemory() / dataSize + " MB";
        trigger.respond(builder);
    }
}
