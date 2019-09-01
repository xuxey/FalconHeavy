package com.xuxe.falconHeavy;

import com.xuxe.falconHeavy.config.Config;
import net.dv8tion.jda.api.JDABuilder;

import java.util.logging.Logger;

public class FalconHeavy {
    public static Logger logger;

    FalconHeavy() {
        logger = Logger.getGlobal();
    }

    public static void main(String[] args) {
        logger.info("Falcon Heavy is starting");
        Config.reload();
        JDABuilder jdaBuilder = new JDABuilder();
    }
}
