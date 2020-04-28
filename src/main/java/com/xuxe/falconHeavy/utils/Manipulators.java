package com.xuxe.falconHeavy.utils;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.framework.UserRank;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

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
            final User[] user = {null};
            jda.retrieveUserById(id.trim()).queue(s -> user[0] = s);
            return !(user[0] == null);
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean rankCheck(UserRank required, UserRank actual) {
        return rankLevelParser(required) <= rankLevelParser(actual);
    }

    private static int rankLevelParser(UserRank rank) {
        if (rank.equals(UserRank.ADMIN))
            return 3;
        if (rank.equals(UserRank.DONATOR))
            return 2;
        return 1;
    }

    public static ArrayList<User> getIntendedUsers(Message message) {
        ArrayList<User> users = new ArrayList<>(message.getMentionedUsers());
        JDA jda = FalconHeavy.getJda();
        for (String s : message.getContentDisplay().split(" ")) {
            try {
                jda.retrieveUserById(s).queue(users::add);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return users;
    }

    public static ArrayList<Member> getIntendedMembers(Message message) {
        ArrayList<Member> users = new ArrayList<Member>(message.getMentionedMembers());
        for (String s : message.getContentDisplay().split(" "))
            if (isValidId(s))
                users.add(message.getGuild().getMemberById(s));
        return users;
    }
}
