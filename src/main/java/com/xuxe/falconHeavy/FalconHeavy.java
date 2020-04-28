package com.xuxe.falconHeavy;

import com.xuxe.falconHeavy.commands.admin.BlacklistCommand;
import com.xuxe.falconHeavy.commands.admin.MemoryCommand;
import com.xuxe.falconHeavy.commands.admin.PresenceCommand;
import com.xuxe.falconHeavy.commands.admin.SetRankCommand;
import com.xuxe.falconHeavy.commands.admin.eval.EvalCommand;
import com.xuxe.falconHeavy.commands.admin.eval.NashornEvalCommand;
import com.xuxe.falconHeavy.commands.config.DisableCommand;
import com.xuxe.falconHeavy.commands.config.EnableCommand;
import com.xuxe.falconHeavy.commands.fun.DiceCommand;
import com.xuxe.falconHeavy.commands.fun.FactCommand;
import com.xuxe.falconHeavy.commands.fun.trivia.TriviaCommand;
import com.xuxe.falconHeavy.commands.misc.HelpCommand;
import com.xuxe.falconHeavy.commands.misc.PingCommand;
import com.xuxe.falconHeavy.commands.moderation.BanCommand;
import com.xuxe.falconHeavy.commands.moderation.GetPermsCommand;
import com.xuxe.falconHeavy.commands.moderation.KickCommand;
import com.xuxe.falconHeavy.commands.moderation.PurgeCommand;
import com.xuxe.falconHeavy.commands.utilities.*;
import com.xuxe.falconHeavy.config.Config;
import com.xuxe.falconHeavy.constants.FileNames;
import com.xuxe.falconHeavy.database.ConnectionManager;
import com.xuxe.falconHeavy.framework.command.CommandHandler;
import com.xuxe.falconHeavy.framework.command.waiter.EventWaiter;
import com.xuxe.falconHeavy.framework.listeners.GuildJoinListener;
import com.xuxe.falconHeavy.framework.listeners.MessageReceivers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

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
        jda = JDABuilder.createDefault(config.getToken())
                .enableIntents(EnumSet.of(GatewayIntent.GUILD_MEMBERS))
                .setBulkDeleteSplittingEnabled(false)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .build().awaitReady();
        jda.addEventListener(new MessageReceivers());
        jda.addEventListener(new GuildJoinListener());
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
        addConfigCommands();
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
        handler.addCommand(new GetPermsCommand());
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
        handler.addCommand(new MathCommand());
        handler.addCommand(new AvatarCommand());
        handler.addCommand(new WelcomeDMCommand());
    }

    private static void addAdminCommands() {
        handler.addCommand(new EvalCommand());
        handler.addCommand(new NashornEvalCommand());
        handler.addCommand(new PresenceCommand());
        handler.addCommand(new MemoryCommand());
        handler.addCommand(new SetRankCommand());
        handler.addCommand(new BlacklistCommand());
    }

    private static void addFunCommands() {
        handler.addCommand(new TriviaCommand());
        handler.addCommand(new DiceCommand());
        handler.addCommand(new FactCommand());
    }

    private static void addConfigCommands() {
        handler.addCommand(new EnableCommand());
        handler.addCommand(new DisableCommand());
    }

    public static void setJda(JDA jda) {
        FalconHeavy.jda = jda;
    }

    public static EventWaiter getWaiter() {
        return waiter;
    }
}
