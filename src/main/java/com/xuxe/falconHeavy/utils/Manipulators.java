package com.xuxe.falconHeavy.utils;

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
}
