package com.xuxe.falconHeavy.commands.config;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import net.dv8tion.jda.api.Permission;

public class EnableCommand extends Command {
    public EnableCommand() {
        this.name = "enable";
        this.help = "Enables a particular command that was previously disabled in a server";
        this.category = Category.Config;
        this.cooldown = new int[]{60, 60};
        this.cooldownScope = CooldownScope.GUILD;
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {

    }
}
