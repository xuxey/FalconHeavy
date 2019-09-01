package com.xuxe.falconHeavy.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.constants.Constants;
import com.xuxe.falconHeavy.constants.FileNames;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class DBConfig {
    public static String USER_TABLE;
    private static String sqlID;
    private static String sqlPassword;
    private static String url;

    public static String getUrl() {
        return url;
    }

    public static String getSqlID() {
        return sqlID;
    }

    public static String getSqlPassword() {
        return sqlPassword;
    }

    public static void reload() {
        try {
            Gson gson = new Gson();
            JsonReader reader = null;
            reader = new JsonReader(new FileReader(FileNames.CONFIG_DATABASE));
            gson.fromJson(reader, Config.class);
        } catch (FileNotFoundException e) {
            FalconHeavy.logger.info(Constants.TASK_FAIL + " load " + FileNames.CONFIG_MAIN);
            e.printStackTrace();
        }
    }
}
