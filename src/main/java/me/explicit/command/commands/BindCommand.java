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
 *  org.lwjgl.input.Keyboard
 */
package me.explicit.command.commands;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import me.explicit.Explicit;
import me.explicit.module.Module;
import me.explicit.utils.ChatUtils;
import me.explicit.utils.Game;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Keyboard;

public class BindCommand
implements ICommand {
    public int compareTo(ICommand iCommand) {
        return 0;
    }

    public String getCommandName() {
        return "-bind";
    }

    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/-bind";
    }

    public List<String> getCommandAliases() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("-b");
        return arrayList;
    }

    public void processCommand(ICommandSender iCommandSender, String[] stringArray) throws CommandException {
        this.executeCommand(stringArray);
    }

    public List<String> executeCommand(String[] stringArray) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.clear();
        if (Explicit.destructed) {
            Game.Player().addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Unknown command. Try /help for a list of commands"));
            return arrayList;
        }
        if (stringArray.length == 0) {
            if (Explicit.consolegui) {
                arrayList.add("Arguments: ");
                arrayList.add("Set");
                arrayList.add("Remove");
                arrayList.add("RemoveAll");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Arguments: ");
                ChatUtils.sendMessage("Set");
                ChatUtils.sendMessage("Remove");
                ChatUtils.sendMessage("RemoveAll");
            }
        } else if (stringArray[0].equalsIgnoreCase("Set")) {
            if (stringArray.length >= 2 && Explicit.instance.mm.getModuleByName(stringArray[1]) != null) {
                if (stringArray.length == 3 && Keyboard.getKeyIndex((String)stringArray[2].toUpperCase()) != 0) {
                    Explicit.instance.mm.getModuleByName(stringArray[1]).setKey(Keyboard.getKeyIndex((String)stringArray[2].toUpperCase()));
                    if (Explicit.consolegui) {
                        arrayList.add("Successfully bound '" + stringArray[1] + "' to '" + stringArray[2].toUpperCase() + "'");
                    } else {
                        ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully bound '" + stringArray[1] + "' to '" + stringArray[2].toUpperCase() + "'");
                    }
                } else if (Keyboard.getKeyIndex((String)stringArray[2].toUpperCase()) == 0) {
                    if (Explicit.consolegui) {
                        arrayList.add("Sorry, the key '" + stringArray[2].toUpperCase() + "' does not exist");
                    } else {
                        ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Sorry, the key '" + stringArray[2].toUpperCase() + "' does not exist");
                    }
                } else if (Explicit.consolegui) {
                    arrayList.add("Usage: /bind set <Module> <Key>");
                } else {
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Usage: /bind set <Module> <Key>");
                }
            } else if (stringArray.length >= 2 && Explicit.instance.mm.getModuleByName(stringArray[1]) == null) {
                if (Explicit.consolegui) {
                    arrayList.add("Sorry, the module '" + stringArray[1].toUpperCase() + "' does not exist");
                } else {
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Sorry, the module '" + stringArray[1].toUpperCase() + "' does not exist");
                }
            } else if (stringArray.length == 1) {
                if (Explicit.consolegui) {
                    arrayList.add("Usage: /bind set <Module> <Key>");
                } else {
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Usage: /bind set <Module> <Key>");
                }
            }
        } else if (stringArray[0].equalsIgnoreCase("RemoveAll")) {
            Iterator<?> iterator = Explicit.instance.mm.getModules().iterator();
            if (iterator.hasNext()) {
                Module module = (Module)iterator.next();
                module.setKey(0);
                return null;
            }
            if (Explicit.consolegui) {
                arrayList.add("Successfully unbound all modules");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully unbound all modules");
            }
        } else if (stringArray[0].equalsIgnoreCase("Remove")) {
            if (stringArray.length >= 2 && Explicit.instance.mm.getModuleByName(stringArray[1]) != null) {
                Explicit.instance.mm.getModuleByName(stringArray[1]).setKey(0);
                if (Explicit.consolegui) {
                    arrayList.add("Successfully unbound '" + stringArray[1] + "'");
                } else {
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully unbound '" + stringArray[1] + "'");
                }
            } else if (stringArray.length >= 2) {
                if (Explicit.consolegui) {
                    arrayList.add("Sorry, the module '" + stringArray[1] + "' does not exist");
                } else {
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Sorry, the module '" + stringArray[1] + "' does not exist");
                }
            } else if (Explicit.consolegui) {
                arrayList.add("Usage: /bind remove <Module>");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Usage: /bind remove <Module>");
            }
        } else if (Explicit.consolegui) {
            arrayList.add("Arguments: ");
            arrayList.add("Set");
            arrayList.add("Remove");
            arrayList.add("RemoveAll");
        } else {
            ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Arguments: ");
            ChatUtils.sendMessage("Set");
            ChatUtils.sendMessage("Remove");
            ChatUtils.sendMessage("RemoveAll");
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

