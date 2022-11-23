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
import java.util.Iterator;
import java.util.List;
import me.explicit.Explicit;
import me.explicit.config.ConfigManager;
import me.explicit.utils.ChatUtils;
import me.explicit.utils.Game;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class FriendCommand
implements ICommand {
    public int compareTo(ICommand iCommand) {
        return 0;
    }

    public String getCommandName() {
        return "-friend";
    }

    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/-friend";
    }

    public List getCommandAliases() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("-friends");
        arrayList.add("-f");
        return arrayList;
    }

    public void processCommand(ICommandSender iCommandSender, String[] stringArray) throws CommandException {
    }

    public List executeCommand(String[] stringArray) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.clear();
        if (Explicit.destructed) {
            Game.Player().addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Unknown command. Try /help for a list of commands"));
            return arrayList;
        }
        if (stringArray.length == 0) {
            if (Explicit.consolegui) {
                arrayList.add("Arguments: ");
                arrayList.add("Add");
                arrayList.add("Remove");
                arrayList.add("Clear");
                arrayList.add("List");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Arguments: ");
                ChatUtils.sendMessage("Add");
                ChatUtils.sendMessage("Remove");
                ChatUtils.sendMessage("Clear");
                ChatUtils.sendMessage("List");
            }
            return arrayList;
        }
        if (stringArray[0].equalsIgnoreCase("Clear")) {
            Explicit.instance.friendManager.getFriends().clear();
            ConfigManager.SaveFriendsFile();
            if (Explicit.consolegui) {
                arrayList.add("Successfully cleared your friends list");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully cleared your friends list");
            }
        } else if (!(stringArray.length <= 0 || stringArray[0].equalsIgnoreCase("add") || stringArray[0].equalsIgnoreCase("remove") || stringArray[0].equalsIgnoreCase("clear") || stringArray[0].equalsIgnoreCase("list"))) {
            if (Explicit.consolegui) {
                arrayList.add("Arguments: ");
                arrayList.add("Add");
                arrayList.add("Remove");
                arrayList.add("Clear");
                arrayList.add("List");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Arguments: ");
                ChatUtils.sendMessage("Add");
                ChatUtils.sendMessage("Remove");
                ChatUtils.sendMessage("Clear");
                ChatUtils.sendMessage("List");
            }
        } else if (stringArray[0].equalsIgnoreCase("add") && stringArray.length > 1) {
            Explicit.instance.friendManager.addFriend(stringArray[1]);
            if (Explicit.consolegui) {
                arrayList.add("Successfully added '" + stringArray[1] + "' to your friends list");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully added '" + stringArray[1] + "' to your friends list");
            }
        } else if (stringArray[0].equalsIgnoreCase("add")) {
            if (Explicit.consolegui) {
                arrayList.add("/friends add <Name>");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "/friends add <Name>");
            }
        } else if (stringArray[0].equalsIgnoreCase("remove") && stringArray.length > 1) {
            if (Explicit.instance.friendManager.getFriends().contains(stringArray[1])) {
                Explicit.instance.friendManager.getFriends().remove(stringArray[1]);
                ConfigManager.SaveFriendsFile();
                if (Explicit.consolegui) {
                    arrayList.add("Successfully removed '" + stringArray[1] + "' from your friends list");
                } else {
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully removed '" + stringArray[1] + EnumChatFormatting.GOLD + "' from your friends list");
                }
            } else if (Explicit.consolegui) {
                arrayList.add("'" + stringArray[1] + "' was not found on your friends list");
            } else {
                ChatUtils.sendMessage("'" + stringArray[1] + EnumChatFormatting.GOLD + "' was not found on your friends list");
            }
        } else if (stringArray[0].equalsIgnoreCase("list")) {
            if (Explicit.instance.friendManager.getFriends().size() == 0) {
                if (Explicit.consolegui) {
                    arrayList.add("You have no friends :(");
                } else {
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "You have no friends :(");
                }
            } else {
                if (Explicit.consolegui) {
                    arrayList.add("Friends: ");
                }
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Friends: ");
            }
            Iterator iterator = Explicit.instance.friendManager.getFriends().iterator();
            if (iterator.hasNext()) {
                String string = (String)iterator.next();
                if (Explicit.consolegui) {
                    arrayList.add(string);
                } else {
                    ChatUtils.sendMessage(string);
                }
                return null;
            }
        } else if (stringArray[0].equalsIgnoreCase("remove")) {
            if (Explicit.consolegui) {
                arrayList.add("/-friends remove <Name>");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "/-friends remove <Name>");
            }
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

