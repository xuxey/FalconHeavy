package com.xuxe.falconHeavy;

import net.dv8tion.jda.api.JDABuilder;

import java.util.logging.Logger;

public class FalconHeavy {
    public static Logger logger;

    FalconHeavy() {
        logger = Logger.getGlobal();
    }

    public static void main(String[] args) {
        logger.info("Falcon Heavy is starting");
        JDABuilder jdaBuilder = new JDABuilder();
    }
}
