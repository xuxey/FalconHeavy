package com.xuxe.falconHeavy.framework.triggers;

import com.xuxe.falconHeavy.database.framework.DBChecks;
import com.xuxe.falconHeavy.framework.UserRank;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

import static com.xuxe.falconHeavy.constants.Constants.MAX_MESSAGES;
import static com.xuxe.falconHeavy.utils.Manipulators.popArray;

@SuppressWarnings("unused")
public class CommandTrigger {
    private String[] fullArgs;
    private String string;
    private String[] args;
    private String label;
    private Message message;
    private Guild guild;
    private MessageChannel channel;
    private MessageReceivedEvent event;
    private boolean hasFile;
    private User user;
    private UserRank rank;
    private boolean guildDisabled;
    private JDA jda;

    public CommandTrigger(MessageReceivedEvent event) {
        this.message = event.getMessage();
        this.fullArgs = message.getContentRaw().split(" ");
        this.string = message.getContentRaw();
        this.args = popArray(fullArgs);
        this.label = fullArgs[0];
        this.guild = event.getGuild();
        this.channel = event.getChannel();
        this.event = event;
        this.hasFile = message.getAttachments().size() > 0;
        this.user = event.getAuthor();
        this.rank = DBChecks.getRank(user.getId());
        this.jda = event.getJDA();
    }

    // Getters
    public JDA getJda() {
        return jda;
    }

    public UserRank getRank() {
        return rank;
    }

    public User getUser() {
        return user;
    }

    public String[] getFullArgs() {
        return fullArgs;
    }

    public String getString() {
        return string;
    }

    public String[] getArgs() {
        return args;
    }

    public String getLabel() {
        return label;
    }

    public Message getMessage() {
        return message;
    }

    public Guild getGuild() {
        return guild;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public boolean isHasFile() {
        return hasFile;
    }

    // Also adapted from JDAUtils
    private static ArrayList<String> splitMessage(String stringToSend) {
        ArrayList<String> msgs = new ArrayList<>();
        if (stringToSend != null) {
            stringToSend = stringToSend.replace("@everyone", "@\u0435veryone").replace("@here", "@h\u0435re").trim();
            while (stringToSend.length() > 2000) {
                int leeway = 2000 - (stringToSend.length() % 2000);
                int index = stringToSend.lastIndexOf("\n", 2000);
                if (index < leeway)
                    index = stringToSend.lastIndexOf(" ", 2000);
                if (index < leeway)
                    index = 2000;
                String temp = stringToSend.substring(0, index).trim();
                if (!temp.equals(""))
                    msgs.add(temp);
                stringToSend = stringToSend.substring(index).trim();
            }
            if (!stringToSend.equals(""))
                msgs.add(stringToSend);
        }
        return msgs;
    }

    /*
     * Taken from com.jagrosh.jdautilities
     */
    private void sendMessage(MessageChannel channel, String message, Consumer<Message> success, Consumer<Throwable> failure) {
        ArrayList<String> messages = splitMessage(message);
        for (int i = 0; i < MAX_MESSAGES && i < messages.size(); i++) {
            if (i + 1 == MAX_MESSAGES || i + 1 == messages.size()) {
                channel.sendMessage(messages.get(i)).queue(success, failure);
            } else {
                channel.sendMessage(messages.get(i)).queue();
            }
        }
    }

    /*
     *  respond methods adapted from JDAUtilities
     */
    public void respond(String message, Consumer<Message> success, Consumer<Throwable> failure) {
        sendMessage(event.getChannel(), message, success, failure);
    }

    public void respond(MessageEmbed embed) {
        event.getChannel().sendMessage(embed).queue();
    }

    public void respond(MessageEmbed embed, Consumer<Message> success) {
        event.getChannel().sendMessage(embed).queue(success);
    }

    public void respond(MessageEmbed embed, Consumer<Message> success, Consumer<Throwable> failure) {
        event.getChannel().sendMessage(embed).queue(success, failure);
    }

    public void respond(Message message) {
        event.getChannel().sendMessage(message).queue();
    }

    public void respond(Message message, Consumer<Message> success) {
        event.getChannel().sendMessage(message).queue(success);
    }

    public void respond(Message message, Consumer<Message> success, Consumer<Throwable> failure) {
        event.getChannel().sendMessage(message).queue(success, failure);
    }

    public void respond(File file, String filename) {
        event.getChannel().sendFile(file, filename).queue();
    }

    public void respond(String message, File file, String filename) {
        event.getChannel().sendFile(file, filename).content(message).queue();
    }

}
