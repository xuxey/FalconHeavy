package com.xuxe.falconHeavy;

import com.xuxe.falconHeavy.commands.misc.HelpCommand;
import com.xuxe.falconHeavy.commands.misc.PingCommand;
import com.xuxe.falconHeavy.config.Config;
import com.xuxe.falconHeavy.constants.FileNames;
import com.xuxe.falconHeavy.framework.command.CommandHandler;
import com.xuxe.falconHeavy.framework.listeners.MessageReceivers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;

import static com.xuxe.falconHeavy.config.Config.reload;

public class FalconHeavy {
    public static final Logger logger = LogManager.getLogger();
    private static Config config;
    FalconHeavy() {
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        logger.info("Falcon Heavy is starting");
        // load config
        config = reload(FileNames.CONFIG_MAIN);
        CommandHandler handler = new CommandHandler();
        assert config != null;
        JDA jda = new JDABuilder(config.getToken()).build().awaitReady();
        jda.addEventListener(new MessageReceivers());
        handler.addCommand(new PingCommand());
        handler.addCommand(new HelpCommand());
    }

    public static Config getConfig() {
        return config;
    }

}
