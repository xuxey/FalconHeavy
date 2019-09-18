package com.xuxe.falconHeavy;

import com.xuxe.falconHeavy.commands.admin.MemoryCommand;
import com.xuxe.falconHeavy.commands.admin.PresenceCommand;
import com.xuxe.falconHeavy.commands.admin.eval.EvalCommand;
import com.xuxe.falconHeavy.commands.admin.eval.NashornEvalCommand;
import com.xuxe.falconHeavy.commands.fun.DiceCommand;
import com.xuxe.falconHeavy.commands.fun.FactCommand;
import com.xuxe.falconHeavy.commands.fun.trivia.TriviaCommand;
import com.xuxe.falconHeavy.commands.misc.HelpCommand;
import com.xuxe.falconHeavy.commands.misc.PingCommand;
import com.xuxe.falconHeavy.commands.moderation.BanCommand;
import com.xuxe.falconHeavy.commands.moderation.KickCommand;
import com.xuxe.falconHeavy.commands.moderation.PurgeCommand;
import com.xuxe.falconHeavy.commands.utilities.CatCommand;
import com.xuxe.falconHeavy.commands.utilities.JoinDateCommand;
import com.xuxe.falconHeavy.commands.utilities.LyricsCommand;
import com.xuxe.falconHeavy.commands.utilities.YoutubeCommand;
import com.xuxe.falconHeavy.config.Config;
import com.xuxe.falconHeavy.constants.FileNames;
import com.xuxe.falconHeavy.database.ConnectionManager;
import com.xuxe.falconHeavy.framework.command.CommandHandler;
import com.xuxe.falconHeavy.framework.command.waiter.EventWaiter;
import com.xuxe.falconHeavy.framework.listeners.MessageReceivers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;

import static com.xuxe.falconHeavy.config.Config.reload;

public class FalconHeavy {
    public static final Logger logger = LogManager.getLogger();
    private static Config config;
    private static JDA jda;
    private static CommandHandler handler;
    private static EventWaiter waiter;

    public static void main(String[] args) throws LoginException, InterruptedException {
        logger.info("Falcon Heavy is starting");
        // load config
        config = reload(FileNames.CONFIG_MAIN);
        if (config == null)
            System.exit(1);
        jda = new JDABuilder(config.getToken()).build().awaitReady();
        jda.addEventListener(new MessageReceivers());
        jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.watching(FalconHeavy.getConfig().getPresence()));
        waiter = new EventWaiter();
        jda.addEventListener(waiter);
        ConnectionManager.initializeConnection();
        handler = new CommandHandler();
        // Caveman style command initializing
        addMiscCommands();
        addModerationCommands();
        addUtilityCommands();
        addAdminCommands();
        addFunCommands();
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
        handler.addCommand(new PurgeCommand());
    }

    private static void addMiscCommands() {
        handler.addCommand(new PingCommand());
        handler.addCommand(new HelpCommand());
    }

    private static void addUtilityCommands() {
        handler.addCommand(new YoutubeCommand());
        handler.addCommand(new CatCommand());
        handler.addCommand(new JoinDateCommand());
        handler.addCommand(new LyricsCommand());
    }

    private static void addAdminCommands() {
        handler.addCommand(new EvalCommand());
        handler.addCommand(new NashornEvalCommand());
        handler.addCommand(new PresenceCommand());
        handler.addCommand(new MemoryCommand());
    }

    private static void addFunCommands() {
        handler.addCommand(new TriviaCommand());
        handler.addCommand(new DiceCommand());
        handler.addCommand(new FactCommand());
    }

    public static EventWaiter getWaiter() {
        return waiter;
    }
}
