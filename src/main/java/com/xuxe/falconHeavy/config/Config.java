package com.xuxe.falconHeavy.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.constants.Constants;
import com.xuxe.falconHeavy.constants.FileNames;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {
    private static String token;
    private static String ownerID;
    private static String youTubeKey;
    private static String dictionaryKey;

    public static String getToken() {
        return token;
    }

    public static String getDictionaryKey() {
        return dictionaryKey;
    }

    public static void reload() {
        try {
            Gson gson = new Gson();
            JsonReader reader = null;
            reader = new JsonReader(new FileReader(FileNames.CONFIG_MAIN));
            gson.fromJson(reader, Config.class);
        } catch (FileNotFoundException e) {
            FalconHeavy.logger.info(Constants.TASK_FAIL + " load " + FileNames.CONFIG_MAIN);
            e.printStackTrace();
        }
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getYouTubeKey() {
        return youTubeKey;
    }
}
