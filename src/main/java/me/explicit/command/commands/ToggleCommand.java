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

public class ToggleCommand
implements ICommand {
    public int compareTo(ICommand iCommand) {
        return 0;
    }

    public String getCommandName() {
        return "-toggle";
    }

    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/-toggle";
    }

    public List getCommandAliases() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("-t");
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
        if (stringArray.length == 0) {
            if (bl) {
                arrayList.add("Usage: '/-t <Module>'");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Usage: '/-t <Module>'");
            }
        } else if (Explicit.instance.mm.getModuleByName(stringArray[0]) != null) {
            Explicit.instance.mm.getModuleByName(stringArray[0]).toggle();
            if (bl) {
                arrayList.add("Successfully toggled " + stringArray[0]);
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully toggled " + stringArray[0]);
            }
        } else if (bl) {
            arrayList.add("Sorry, the module '" + stringArray[0] + "' doesn't exist");
        } else {
            ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Sorry, the module '" + stringArray[0] + "' doesn't exist");
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

