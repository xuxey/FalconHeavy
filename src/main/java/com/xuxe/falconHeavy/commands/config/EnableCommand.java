package com.xuxe.falconHeavy.commands.config;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.constants.Constants;
import com.xuxe.falconHeavy.constants.Responses;
import com.xuxe.falconHeavy.database.framework.DBGuildSettings;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.CommandHandler;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.time.Instant;

public class EnableCommand extends Command {
    public EnableCommand() {
        this.name = "enable";
        this.help = "Enables a particular command that was previously disabled in a server";
        this.category = Category.Config;
        this.cooldown = new int[]{60, 60};
        this.syntax = "enable <commandName>";
        this.cooldownScope = CooldownScope.GUILD;
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        if (trigger.getArgs().length == 0)
            throw new IncorrectSyntaxException();
        String command = CommandHandler.getNameFromAlias(trigger.getArgs(0));
        if (DBGuildSettings.enableCommand(trigger.getGuild().getId(), command)) {
            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle(Constants.CONFIG_HEADER);
            embedBuilder.setTimestamp(Instant.now());
            embedBuilder.setDescription("**Command Update**: The " + command + " command has been enabled");
            trigger.respond(embedBuilder.build());
        } else {
            trigger.respond(Responses.ERROR);
            reactFail();
        }
    }
}
