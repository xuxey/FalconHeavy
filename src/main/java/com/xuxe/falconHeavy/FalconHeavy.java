package com.xuxe.falconHeavy;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.xuxe.falconHeavy.commands.misc.PingCommand;
import com.xuxe.falconHeavy.config.Config;
import com.xuxe.falconHeavy.constants.Constants;
import com.xuxe.falconHeavy.constants.FileNames;
import com.xuxe.falconHeavy.framework.command.CommandHandler;
import com.xuxe.falconHeavy.framework.listeners.MessageReceivers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Logger;

public class FalconHeavy {
    public static Logger logger;
    private static Config config;
    FalconHeavy() {
        logger = Logger.getGlobal();
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        //logger.info("Falcon Heavy is starting");
        // load config
        config = reload(FileNames.CONFIG_MAIN);
        CommandHandler handler = new CommandHandler();
        assert config != null;
        JDA jda = new JDABuilder(config.getToken()).build().awaitReady();
        jda.addEventListener(new MessageReceivers());
        handler.addCommand(new PingCommand());
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

    public static Config getConfig() {
        return config;
    }

}
