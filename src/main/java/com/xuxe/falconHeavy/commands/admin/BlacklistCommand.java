package com.xuxe.falconHeavy.commands.admin;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.database.ConnectionManager;
import com.xuxe.falconHeavy.database.framework.DBChecks;
import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import com.xuxe.falconHeavy.utils.Manipulators;
import net.dv8tion.jda.api.entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BlacklistCommand extends Command {
    public BlacklistCommand() {
        this.name = "blacklist";
        this.category = Category.Admin;
        this.help = "blacklists a user from all bot commands";
        this.privateAccessible = true;
        this.rank = UserRank.ADMIN;
        this.aliases = new String[]{"bl"};
        this.syntax = "blacklist <id/mention>";
    }

    public static boolean blacklistUser(String id) {
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(
                    "UPDATE users SET blacklisted = blacklisted ^ 1 WHERE uid = ?");
            statement.setString(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        List<User> users = Manipulators.getIntendedUsers(trigger.getMessage());
        if (users.size() == 0) throw new IncorrectSyntaxException("You need to specify user(s).");
        for (User u : users) {
            boolean wasBlacklisted = DBChecks.isBlacklisted(u.getId());
            if (!blacklistUser(u.getId()))
                trigger.respond("The user " + u.getName() + "#" + u.getDiscriminator() + " could not be blacklisted.");
            else
                trigger.respond("The user " + u.getName() + "#" + u.getDiscriminator() + " was " +
                        ((wasBlacklisted) ? "un" : "") + "blacklisted.");
        }
    }
}
