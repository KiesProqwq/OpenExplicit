/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandException
 *  net.minecraft.command.ICommand
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 */
package me.explicit.command.commands;

import java.util.ArrayList;
import java.util.List;
import me.explicit.Explicit;
import me.explicit.utils.ChatUtils;
import me.explicit.utils.Game;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class HelpCommand
implements ICommand {
    public int compareTo(ICommand iCommand) {
        return 0;
    }

    public String getCommandName() {
        return "-help";
    }

    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/-help";
    }

    public List getCommandAliases() {
        ArrayList arrayList = new ArrayList();
        return arrayList;
    }

    public void processCommand(ICommandSender iCommandSender, String[] stringArray) throws CommandException {
        this.executeCommand(stringArray);
    }

    public List executeCommand(String[] stringArray) {
        boolean bl = Explicit.consolegui;
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.clear();
        if (Explicit.destructed) {
            Game.Player().addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Unknown command. Try /help for a list of commands"));
            return arrayList;
        }
        if (bl) {
            arrayList.add("----- Help Page -----");
            arrayList.add("/-toggle : Toggles a module");
            arrayList.add("/-bind : Set the keybind to a module");
            arrayList.add("/-friend : Adds a user to your friends list so you don't target them");
            arrayList.add("/-config : Manage your configs");
            arrayList.add("/-setting : Change the value of a setting");
        } else {
            ChatUtils.sendMessage(EnumChatFormatting.GOLD + "----- Help Page -----");
            ChatUtils.sendMessage("/-toggle : Toggles a module");
            ChatUtils.sendMessage("/-bind : Set the keybind to a module");
            ChatUtils.sendMessage("/-friend : Adds a user to your friends list so you don't target them");
            ChatUtils.sendMessage("/-config : Manage your configs");
            ChatUtils.sendMessage("/-setting : Change the value of a setting");
        }
        return arrayList;
    }

    public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
        return true;
    }

    public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring, BlockPos blockpos) {
        return null;
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }
}

