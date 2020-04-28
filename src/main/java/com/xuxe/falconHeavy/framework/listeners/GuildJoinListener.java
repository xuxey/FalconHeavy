package com.xuxe.falconHeavy.framework.listeners;

import com.xuxe.falconHeavy.database.ConnectionManager;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuildJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        System.out.println("I was executed");
        User user = event.getUser();
        String message = getMessage(event.getGuild().getId());
        if (message.isEmpty())
            return;
        System.out.println("I am not empty");
        message = message.replace("{user}", event.getUser().getAsMention());
        String finalMessage = message;
        user.openPrivateChannel().flatMap(c ->
                c.sendMessage(finalMessage)).queue();
    }

    private String getMessage(String gid) {
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(
                    "SELECT welcomedm FROM guildsettings WHERE gid = ?");
            statement.setString(1, gid);
            ResultSet set = statement.executeQuery();
            if (set.next())
                return set.getString("welcomedm");
            return "";
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return "";
    }

}
