package com.xuxe.falconHeavy.database;

import com.xuxe.falconHeavy.FalconHeavy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection connection;

    private static boolean validate() {
        try {
            return connection.isValid(5);
        } catch (SQLException | NullPointerException e) {
            System.out.println("Connection invalid!!");
            System.exit(0);
        }
        return false;
    }

    public static Connection getConnection() {
        if (validate())
            return connection;
        if (connect())
            return connection;
        return null;
    }

    public static void initializeConnection() {
        connect();
    }

    private static boolean connect() {
        try {
            System.out.println("Connecting to Database: " + FalconHeavy.getConfig().getUrl());
            connection = DriverManager.getConnection(FalconHeavy.getConfig().getUrl(),
                    FalconHeavy.getConfig().getSqlID(),
                    FalconHeavy.getConfig().getSqlPassword());
            return validate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
