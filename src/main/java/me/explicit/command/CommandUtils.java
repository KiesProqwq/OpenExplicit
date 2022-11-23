/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package me.explicit.command;

import java.util.ArrayList;
import java.util.List;
import me.explicit.command.commands.BindCommand;
import me.explicit.command.commands.ConfigCommand;
import me.explicit.command.commands.FriendCommand;
import me.explicit.command.commands.HelpCommand;
import me.explicit.command.commands.SettingsCommand;
import me.explicit.command.commands.ToggleCommand;
import org.apache.commons.lang3.ArrayUtils;

public class CommandUtils {
	
    @SuppressWarnings("unchecked")
	public static List sendCommand(String string) {
        List<String> list = new ArrayList<String>();
        list.clear();
        list.add("NULL");
        string = string.replaceFirst("/", "");
        string = string.replaceFirst("-", "");
        
//        switch
        
        if (string.startsWith("bind")) {
            list = new BindCommand().executeCommand((String[])ArrayUtils.remove((Object[])string.split(" "), (int)0));
        } else if (string.startsWith("toggle") || string.startsWith("t")) {
            list = new ToggleCommand().executeCommand((String[])ArrayUtils.remove((Object[])string.split(" "), (int)0));
        } else if (string.startsWith("friend") || string.startsWith("f") || string.startsWith("friends")) {
            list = new FriendCommand().executeCommand((String[])ArrayUtils.remove((Object[])string.split(" "), (int)0));
        } else if (string.startsWith("config")) {
            list = new ConfigCommand().executeCommand((String[])ArrayUtils.remove((Object[])string.split(" "), (int)0), false);
        } else if (string.startsWith("settings") || string.startsWith("setting") || string.startsWith("set")) {
            list = new SettingsCommand().executeCommand((String[])ArrayUtils.remove((Object[])string.split(" "), (int)0));
        } else if (string.startsWith("help")) {
            list = new HelpCommand().executeCommand((String[])ArrayUtils.remove((Object[])string.split(" "), (int)0));
        } else {
            list.clear();
            list.add("ERROR: '" + string + "' is not a command");
        }
        return list;
    }
}

