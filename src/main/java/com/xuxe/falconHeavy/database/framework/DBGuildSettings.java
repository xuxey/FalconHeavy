package com.xuxe.falconHeavy.database.framework;

import com.xuxe.falconHeavy.database.ConnectionManager;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import net.dv8tion.jda.api.entities.ChannelType;

import java.sql.*;

public class DBGuildSettings {
    public static void addColumn(String column) {
        Connection connection = ConnectionManager.getConnection();
        column = column.trim();
        try {
            assert connection != null;
            DatabaseMetaData md = connection.getMetaData();
            ResultSet r = md.getColumns(null, null, "guildsettings", column);
            if (r.next()) return;
            PreparedStatement addColumn = connection.prepareStatement("alter table guildsettings add " + column + " boolean");
            PreparedStatement alterColumn = connection.prepareStatement("alter table guildsettings modify " + column + " tinyint(1) NOT NULL DEFAULT 0");
            PreparedStatement updateColumn = connection.prepareStatement("update guildsettings set " + column + " = 0 where " + column + " IS NULL");
            if (addColumn.execute())
                if (updateColumn.execute())
                    alterColumn.execute();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void addGuild(String guildID) {
        Connection connection = ConnectionManager.getConnection();
        try {
            assert connection != null;
            PreparedStatement addColumn = connection.prepareStatement("insert into guildsettings (gid) values (" + guildID + ")");
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
            PreparedStatement fetch = connection.prepareStatement("select " + name + " from guildsettings where gid = " + guildID);
            ResultSet set = fetch.executeQuery();
            if (set == null || !set.next()) {
                addGuild(trigger.getGuild().getId());
                return false;
            }
            set.first();
            return set.getBoolean(name);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
