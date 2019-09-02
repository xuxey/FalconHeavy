package com.xuxe.falconHeavy.config;

public class Config {
    public String prefix = "//";
    private String token;
    private String ownerID;
    private String youTubeKey;
    private String dictionaryKey;
    private String sqlID;
    private String sqlPassword;
    private String url;

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
}
