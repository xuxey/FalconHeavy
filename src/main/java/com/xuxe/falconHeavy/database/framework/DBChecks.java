package com.xuxe.falconHeavy.database.framework;

import com.xuxe.falconHeavy.database.ConnectionManager;
import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.utils.Manipulators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DBChecks {
    public static UserRank getRank(String userID) {
        Connection connection = ConnectionManager.getConnection();
        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_rank FROM users WHERE uid = ?");
            preparedStatement.setString(1, userID);
            ResultSet set = preparedStatement.executeQuery();
            set.first();
            return Manipulators.rankParser(set.getString("user_rank"));
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return UserRank.DEFAULT;
        }
    }

    public static boolean isBlacklisted(String userID) {
        Connection connection = ConnectionManager.getConnection();
        try {
            assert connection != null;
            if (!DBChecks.userExists(userID))
                DBChecks.makeUser(userID);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT blacklisted FROM users WHERE uid = ?");
            preparedStatement.setString(1, userID.trim());
            ResultSet set = preparedStatement.executeQuery();
            set.first();
            return set.getInt("blacklisted") != 0;
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isAdmin(String id) {
        return Objects.equals(getRank(id), UserRank.ADMIN);
    }

    public static boolean isDonator(String id) {
        return Objects.equals(getRank(id), UserRank.DONATOR);
    }

    public static boolean isDefault(String id) {
        return Objects.equals(getRank(id), UserRank.DEFAULT);
    }

    public static void makeUser(String id) {
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(
                    "insert into users(uid) values(?);");
            statement.setString(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists(String id) {
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(
                    "select points from users where uid=?");
            statement.setString(1, id);
            ResultSet set = statement.executeQuery();
            return set.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
