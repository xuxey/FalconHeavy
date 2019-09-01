package com.xuxe.falconHeavy.utils;

import com.xuxe.falconHeavy.framework.UserRank;

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
}
