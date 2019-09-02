package com.xuxe.falconHeavy.database.framework;

import com.xuxe.falconHeavy.database.ConnectionManager;
import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.utils.Manipulators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBChecks {
    public static UserRank getRank(String userID) {
        Connection connection = ConnectionManager.getConnection();
        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_rank FROM users WHERE uid = ?;");
            preparedStatement.setString(1, userID.trim());
            ResultSet set = preparedStatement.executeQuery();
            set.first();
            return Manipulators.rankParser(set.getString("user_rank"));
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isBlacklisted(String userID) {
        Connection connection = ConnectionManager.getConnection();
        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT blacklisted FROM users WHERE uid = ?;");
            preparedStatement.setString(1, userID.trim());
            ResultSet set = preparedStatement.executeQuery();
            set.first();
            return set.getInt("blacklisted") != 0;
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
