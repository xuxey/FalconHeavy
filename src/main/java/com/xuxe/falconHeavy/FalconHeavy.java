package com.xuxe.falconHeavy;

import com.xuxe.falconHeavy.commands.admin.eval.EvalCommand;
import com.xuxe.falconHeavy.commands.admin.eval.NashornEvalCommand;
import com.xuxe.falconHeavy.commands.misc.HelpCommand;
import com.xuxe.falconHeavy.commands.misc.PingCommand;
import com.xuxe.falconHeavy.commands.moderation.BanCommand;
import com.xuxe.falconHeavy.commands.moderation.KickCommand;
import com.xuxe.falconHeavy.commands.utilities.CatCommand;
import com.xuxe.falconHeavy.commands.utilities.JoinDateCommand;
import com.xuxe.falconHeavy.commands.utilities.YoutubeCommand;
import com.xuxe.falconHeavy.config.Config;
import com.xuxe.falconHeavy.constants.FileNames;
import com.xuxe.falconHeavy.database.ConnectionManager;
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
    private static JDA jda;
    private static CommandHandler handler;
    FalconHeavy() {
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        logger.info("Falcon Heavy is starting");
        // load config
        config = reload(FileNames.CONFIG_MAIN);
        if (config == null)
            System.exit(1);
        jda = new JDABuilder(config.getToken()).build().awaitReady();
        jda.addEventListener(new MessageReceivers());
        ConnectionManager.initializeConnection();
        handler = new CommandHandler();
        // Caveman style command initializing
        addMiscCommands();
        addModerationCommands();
        addUtilityCommands();
        addOwnerCommands();
    }

    public static JDA getJda() {
        return jda;
    }

    public static Config getConfig() {
        return config;
    }

    private static void addModerationCommands() {
        handler.addCommand(new BanCommand());
        handler.addCommand(new KickCommand());
    }

    private static void addMiscCommands() {
        handler.addCommand(new PingCommand());
        handler.addCommand(new HelpCommand());
    }

    private static void addUtilityCommands() {
        handler.addCommand(new YoutubeCommand());
        handler.addCommand(new CatCommand());
        handler.addCommand(new JoinDateCommand());
    }

    private static void addOwnerCommands() {
        handler.addCommand(new EvalCommand());
        handler.addCommand(new NashornEvalCommand());
    }
}
