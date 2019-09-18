package com.xuxe.falconHeavy.commands.fun;

import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;

import java.util.Random;

public class DiceCommand extends Command {
    public DiceCommand() {
        this.name = "dice";
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        Random random = new Random();
        trigger.getMessage().addReaction("\uD83C").queue();
        trigger.respond("Your dice rolled a " + random.ints(1, 0, 6).findFirst());
    }
}
