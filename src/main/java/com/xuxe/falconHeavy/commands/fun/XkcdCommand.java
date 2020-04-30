package com.xuxe.falconHeavy.commands.fun;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;

import java.util.Random;

public class XkcdCommand extends Command {
    public XkcdCommand() {
        this.name = "xkcd";
        this.aliases = new String[]{"xk", "comic"};
        this.category = Category.Fun;
        this.cooldown = new int[]{5, 2, 0};
        this.syntax = "xkcd [number]";
        this.privateAccessible = true;
        this.cooldownScope = CooldownScope.CHANNEL;
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        int number = new Random().nextInt(2300) + 1;
        if (trigger.getArgs().length >= 1)
            try {
                number = Integer.parseInt(trigger.getArgs(0));
            } catch (NumberFormatException ignored) {
            }
        trigger.respond("https://xkcd.com/" + number);
    }
}
