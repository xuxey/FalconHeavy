package com.xuxe.falconHeavy.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.constants.Constants;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {
    public String prefix = "//";
    private String token;
    private String ownerID;
    private String youTubeKey;
    private String dictionaryKey;
    private String sqlID;
    private String sqlPassword;
    private String url;
    private String presence;

    public String getPresence() {
        return presence;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getUrl() {
        return url;
    }

    public String getSqlID() {
        return sqlID;
    }

    public String getSqlPassword() {
        return sqlPassword;
    }

    public String getToken() {
        return token;
    }

    public String getDictionaryKey() {
        return dictionaryKey;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getYouTubeKey() {
        return youTubeKey;
    }

    public static Config reload(String configName) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(configName));
            return gson.fromJson(reader, Config.class);
        } catch (FileNotFoundException e) {
            FalconHeavy.logger.info(Constants.TASK_FAIL + " load " + configName);
            e.printStackTrace();
        }
        return null;
    }
}

