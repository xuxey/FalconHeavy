package com.xuxe.falconHeavy.commands.utilities;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.database.ConnectionManager;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import net.dv8tion.jda.api.Permission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WelcomeDMCommand extends Command {
    public WelcomeDMCommand() {
        this.name = "welcomedm";
        this.category = Category.Utilities;
        this.help = "WelcomeDM lets you send an automated direct message to any new members joining your server";
        this.extraHelp = "You can use placeholders such as {server}, {user} and {self} to modify the message as you desire";
        this.aliases = new String[]{"joindm"};
        this.privateAccessible = false;
        this.userPermissions = new Permission[]{Permission.MANAGE_SERVER};
        this.cooldown = new int[]{30, 10, 0};
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        if (trigger.getArgs().length == 0)
            throw new IncorrectSyntaxException();
        String message = trigger.getString();
        message = message.replace("{server}", trigger.getGuild().getName());
        message = message.replace("{self}", trigger.getAuthor().getAsMention());
        if (addWelcomeDM(message, trigger.getGuild().getId()))
            trigger.respond("Welcome direct message was updated. ```Preview```" + message);
        else
            trigger.respond("An error has occurred. Please try again later.");
    }

    private boolean addWelcomeDM(String message, String gid) {
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(
                    "UPDATE guildsettings SET welcomedm = ? WHERE gid = ?");
            statement.setString(1, message);
            statement.setString(2, gid);
            return statement.executeUpdate() == 1;
        } catch (SQLException sql) {
            sql.printStackTrace();
            reactFail();
        }
        return false;
    }
}
