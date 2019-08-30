package com.xuxe.falconHeavy.framework.command;

import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;

public abstract class Command {
    protected String name;
    protected String[] aliases;
    protected String help;
    protected String extraHelp;
    protected long[] disabledGuilds;

    abstract void run(CommandTrigger trigger);
}
