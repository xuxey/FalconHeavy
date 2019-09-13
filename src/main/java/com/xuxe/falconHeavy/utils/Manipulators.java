package com.xuxe.falconHeavy.utils;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.framework.UserRank;
import net.dv8tion.jda.api.JDA;

public class Manipulators {
    public static String[] popArray(String[] args) {
        if (args.length >= 2) {
            String[] newArray = new String[args.length - 1];
            System.arraycopy(args, 1, newArray, 0, newArray.length);
            return newArray;
        } else {
            return new String[0];
        }
    }

    public static UserRank rankParser(String rankKey) {
        if (rankKey.equals("DEF"))
            return UserRank.DEFAULT;
        if (rankKey.equals("DON"))
            return UserRank.DONATOR;
        if (rankKey.equals("ADMIN"))
            return UserRank.ADMIN;
        return UserRank.DEFAULT;
    }

    public static boolean isMention(String string) {
        string = string.trim();
        if (!string.startsWith("<@") || !string.endsWith(">"))
            return false;
        string = string.substring(2, string.length() - 1);
        string = string.replace("!", "");
        JDA jda = FalconHeavy.getJda();
        return jda.getUserById(string) != null;
    }

    public static boolean isValidId(String id) {
        JDA jda = FalconHeavy.getJda();
        try {
            return jda.getUserById(id.trim()) != null;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
