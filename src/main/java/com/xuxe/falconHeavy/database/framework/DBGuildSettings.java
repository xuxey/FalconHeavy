package com.xuxe.falconHeavy.database.framework;

import com.xuxe.falconHeavy.database.ConnectionManager;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.entities.ChannelType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBGuildSettings {
    public static void addColumn(String column) {
        Connection connection = ConnectionManager.getConnection();
        try {
            assert connection != null;
            PreparedStatement addColumn = connection.prepareStatement("alter table guildsettings add ? boolean;");
            PreparedStatement alterColumn = connection.prepareStatement("alter table guildsettings modify ? tinyint(1) NOT NULL DEFAULT 0;");
            addColumn.setString(1, column);
            alterColumn.setString(1, column);
            if (addColumn.execute())
                alterColumn.execute();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void addGuild(String guildID) {
        Connection connection = ConnectionManager.getConnection();
        try {
            assert connection != null;
            PreparedStatement addColumn = connection.prepareStatement("insert into guildsettings (gid) values (?);");
            addColumn.setString(1, guildID);
            addColumn.execute();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDisabled(Command command, CommandTrigger trigger) {
        String name = command.getName();
        if (trigger.getChannel().getType().equals(ChannelType.PRIVATE))
            return false;
        String guildID = trigger.getGuild().getId();
        Connection connection = ConnectionManager.getConnection();
        try {
            assert connection != null;
            PreparedStatement fetch = connection.prepareStatement("select ? from guildsettings where gid = ?;");
            fetch.setString(1, name);
            fetch.setString(2, guildID);
            ResultSet set = fetch.executeQuery();
            if (set == null)
                return false;
            set.first();
            return set.getBoolean(name);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
