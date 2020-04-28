package com.xuxe.falconHeavy.commands.admin;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.database.ConnectionManager;
import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import com.xuxe.falconHeavy.utils.Manipulators;
import net.dv8tion.jda.api.entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SetRankCommand extends Command {
    public SetRankCommand() {
        this.name = "setrank";
        this.aliases = new String[]{"sr", "rank"};
        this.category = Category.Admin;
        this.help = "Sets the rank of a person";
        this.syntax = "setrank <id/mention> <default/donator/admin>";
        this.rank = UserRank.ADMIN;
        this.privateAccessible = true;
    }

    public static boolean rankUser(String id, String rank) {
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(
                    "UPDATE users SET user_rank = ? WHERE uid = ?");
            statement.setString(1, rank);
            statement.setString(2, id);
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
        String rank = trigger.getArgs(trigger.getArgs().length - 1).toUpperCase();
        try {
            UserRank.valueOf(rank);
        } catch (IllegalArgumentException e) {
            throw new IncorrectSyntaxException("This rank does not exist! Allowed ranks: " + Arrays.toString(UserRank.values()));
        }
        if (!rank.equals("ADMIN"))
            rank = rank.substring(0, 3);
        for (User u : users) {
            if (!rankUser(u.getId(), rank))
                trigger.respond("The user " + u.getName() + "#" + u.getDiscriminator() + " could not be ranked");
            else
                trigger.respond("The user " + u.getName() + "#" + u.getDiscriminator() + " was ranked");
        }
    }
}
