package com.xuxe.falconHeavy.framework.command;

import java.util.HashMap;

public interface CommandInterface {
    HashMap<String, Command> getCommands();

    void addCommand(Command command);

    void removeCommand(String command);
}
