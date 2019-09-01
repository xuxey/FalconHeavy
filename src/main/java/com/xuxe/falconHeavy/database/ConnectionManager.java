package com.xuxe.falconHeavy.database;

import com.xuxe.falconHeavy.config.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection connection;

    public static boolean validate() {
        try {
            return connection.isValid(5);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Connection getConnection() {
        if (validate())
            return connection;
        if (connect())
            return connection;
        return null;
    }

    private static boolean connect() {
        try {
            connection = DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getSqlID(), DBConfig.getSqlPassword());
            return validate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
