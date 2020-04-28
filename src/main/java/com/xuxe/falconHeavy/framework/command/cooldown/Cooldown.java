package com.xuxe.falconHeavy.framework.command.cooldown;

import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * This might seem like a complicated mess. That is because it is. But it works.
 * Anyway, here's the basic boilerplate for the following HashMaps:
 * HashMap<String [commandName], HashMap<String [user/guild/channelID], Long [epoch millisecond time]>>
 * CooldownScope.USER
 */

public class Cooldown {

    private static HashMap<String, HashMap<String, Long>> userCooldowns = new HashMap<>();
    // CooldownScope.GUILD
    private static HashMap<String, HashMap<String, Long>> guildCooldowns = new HashMap<>();
    //CooldownScope.CHANNEL
    private static HashMap<String, HashMap<String, Long>> channelCooldowns = new HashMap<>();

    public static int process(Command command, CommandTrigger trigger) {
        String userID = trigger.getUser().getId();
        String name = command.getName();
        HashMap<String, Long> cooldownList = getMap(command);
        if (cooldownList == null)
            return 0;
        Long endTime;
        if (cooldownList.containsKey(getEntityId(command, trigger)))
            endTime = cooldownList.get(getEntityId(command, trigger));
        else {
            add(command, trigger);
            return 0;
        }
        int finalTime;
        Long currentTime = System.currentTimeMillis();
        if (currentTime.compareTo(endTime) < 0) { // currentTime < endTime
            finalTime = (int) (endTime - currentTime) / 1000;
        } else {
            finalTime = 0;
        }
        cooldownList.remove(getEntityId(command, trigger));
        removeOutdated(command, cooldownList);
        return finalTime;
    }

    private static void add(Command command, CommandTrigger trigger) {
        HashMap<String, Long> cooldownList = getMap(command);
        int duration = getDuration(command.getCooldown(), trigger.getRank());
        long newTime = System.currentTimeMillis() + (duration * 1000);
        cooldownList.put(trigger.getUser().getId(), newTime);
        // It's 1:17 AM and I need a life - Xuxe 2nd September 2019
        put(command.getCooldownScope(), command.getName(), cooldownList);
    }

    private static HashMap<String, Long> getMap(@NotNull Command command) {
        CooldownScope scope = command.getCooldownScope();
        String name = command.getName();
        if (scope.equals(CooldownScope.USER))
            return userCooldowns.get(name);
        if (scope.equals(CooldownScope.GUILD))
            return guildCooldowns.get(name);
        if (scope.equals(CooldownScope.CHANNEL))
            return channelCooldowns.get(name);
        return null;
    }

    private static String getEntityId(Command command, CommandTrigger trigger) {
        CooldownScope scope = command.getCooldownScope();
        if (scope.equals(CooldownScope.USER))
            return trigger.getUser().getId();
        if (scope.equals(CooldownScope.GUILD))
            return trigger.getGuild().getId();
        if (scope.equals(CooldownScope.CHANNEL))
            return trigger.getChannel().getId();
        return null;
    }

    private static void removeOutdated(Command command, HashMap<String, Long> newMap) {
        CooldownScope scope = command.getCooldownScope();
        String name = command.getName();
        if (scope.equals(CooldownScope.USER))
            userCooldowns.replace(name, newMap);
        else if (scope.equals(CooldownScope.GUILD))
            guildCooldowns.replace(name, newMap);
        else if (scope.equals(CooldownScope.CHANNEL))
            channelCooldowns.replace(name, newMap);
    }

    private static int getDuration(int[] durations, UserRank rank) {
        if (rank.equals(UserRank.DEFAULT))
            return durations[0];
        if (rank.equals(UserRank.DONATOR))
            return durations[1];
        return 0;
    }

    private static void put(CooldownScope scope, String name, HashMap<String, Long> newMap) {
        if (scope.equals(CooldownScope.USER))
            userCooldowns.put(name, newMap);
        else if (scope.equals(CooldownScope.GUILD))
            guildCooldowns.put(name, newMap);
        else if (scope.equals(CooldownScope.CHANNEL))
            channelCooldowns.put(name, newMap);
    }

    public static void addStartupCommand(Command command) {
        CooldownScope scope = command.getCooldownScope();
        if (scope.equals(CooldownScope.USER)) {
            userCooldowns.put(command.getName(), new HashMap<>());
        }
        if (scope.equals(CooldownScope.GUILD)) {
            guildCooldowns.put(command.getName(), new HashMap<>());
        }
        if (scope.equals(CooldownScope.CHANNEL)) {
            channelCooldowns.put(command.getName(), new HashMap<>());
        }
    }
}
