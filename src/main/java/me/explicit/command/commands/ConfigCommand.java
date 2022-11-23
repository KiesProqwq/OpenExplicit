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

import java.io.File;
import java.io.IOException;
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

public class ConfigCommand
implements ICommand {
    public int compareTo(ICommand iCommand) {
        return 0;
    }

    public String getCommandName() {
        return "-config";
    }

    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/-config";
    }

    public List getCommandAliases() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("-configs");
        arrayList.add("-c");
        return arrayList;
    }

    public void processCommand(ICommandSender iCommandSender, String[] stringArray) throws CommandException {
        this.executeCommand(stringArray, true);
    }

    public List executeCommand(String[] stringArray, boolean bl) {
        boolean bl2;
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.clear();
        if (Explicit.destructed) {
            Game.Player().addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Unknown command. Try /help for a list of commands"));
            return arrayList;
        }
        bl2 = Explicit.consolegui || !bl;
        if (stringArray.length == 0) {
            if (bl2) {
                arrayList.add("Arguments: ");
                arrayList.add("Save");
                arrayList.add("Load");
                arrayList.add("Delete");
                arrayList.add("List");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Arguments: ");
                ChatUtils.sendMessage("Save");
                ChatUtils.sendMessage("Load");
                ChatUtils.sendMessage("Delete");
                ChatUtils.sendMessage("List");
            }
            return arrayList;
        }
        if (stringArray.length >= 2 && stringArray[1].equalsIgnoreCase("Default")) {
            if (bl2) {
                arrayList.add("Sorry the config with the name '" + stringArray[1] + "' is inaccessible");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Sorry the config with the name '" + stringArray[1] + "' is inaccessible");
            }
            return arrayList;
        }
        if (stringArray.length >= 1) {
            if (stringArray[0].equalsIgnoreCase("List")) {
                if (ConfigManager.GetConfigs().size() == 0) {
                    if (bl2) {
                        arrayList.add("You have no configs");
                    } else {
                        ChatUtils.sendMessage(EnumChatFormatting.GOLD + "You have no configs");
                    }
                } else {
                    if (bl2) {
                        arrayList.add("Configs: ");
                    } else {
                        ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Configs: ");
                    }
                    Iterator iterator = ConfigManager.GetConfigs().iterator();
                    if (iterator.hasNext()) {
                        String string = (String)iterator.next();
                        if (bl2) {
                            arrayList.add(string);
                        } else {
                            ChatUtils.sendMessage(string);
                        }
                        return null;
                    }
                }
            } else if (stringArray[0].equalsIgnoreCase("Save")) {
                if (stringArray.length >= 2) {
                    ConfigManager.getConfigFile(stringArray[1], true);
                    ConfigManager.SaveConfigFile(stringArray[1]);
                    if (bl2) {
                        arrayList.add("Successfully saved the config with the name '" + stringArray[1] + "'");
                    } else {
                        ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully saved the config with the name '" + stringArray[1] + "'");
                    }
                } else if (bl2) {
                    arrayList.add("Usage: /config save <Config Name>");
                } else {
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Usage: /config save <Config Name>");
                }
            } else if (stringArray[0].equalsIgnoreCase("Load")) {
                stringArray[1].replaceAll(".cfg", "");
                if (stringArray.length >= 2) {
                    boolean bl4 = true;
                    File file = new File(ConfigManager.dir, String.format("%s.cfg", stringArray[1]));
                    if (!file.exists()) {
                        bl4 = false;
                        try {
                            file.createNewFile();
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                    }
                    if (bl4) {
                        ConfigManager.LoadConfig(stringArray[1]);
                        if (bl2) {
                            arrayList.add("Successfully loaded the config with the name '" + stringArray[1] + "'");
                        } else {
                            ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully loaded the config with the name '" + stringArray[1] + "'");
                        }
                    } else if (bl2) {
                        arrayList.add("The config with the name '" + stringArray[1] + "' does not exist");
                    } else {
                        ChatUtils.sendMessage(EnumChatFormatting.GOLD + "The config with the name '" + stringArray[1] + EnumChatFormatting.GOLD + "' does not exist");
                    }
                } else if (bl2) {
                    arrayList.add("Usage: /config load <Config Name>");
                } else {
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Usage: /config load <Config Name>");
                }
            } else if (stringArray[0].equalsIgnoreCase("Delete")) {
                if (stringArray.length >= 2) {
                    boolean bl5 = true;
                    File file = new File(ConfigManager.dir, String.format("%s.cfg", stringArray[1]));
                    if (!file.exists()) {
                        bl5 = false;
                    }
                    if (bl5) {
                        if (file.delete()) {
                            if (bl2) {
                                arrayList.add("Successfully deleted the config with the name '" + stringArray[1] + "'");
                            } else {
                                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully deleted the config with the name '" + stringArray[1] + "'");
                            }
                        } else if (bl2) {
                            arrayList.add("Deleting the config with the name '" + stringArray[1] + "' failed");
                        } else {
                            ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Deleting the config with the name '" + stringArray[1] + "' failed");
                        }
                    } else if (bl2) {
                        arrayList.add("The config with the name '" + stringArray[1] + "' does not exist");
                    } else {
                        ChatUtils.sendMessage(EnumChatFormatting.GOLD + "The config with the name '" + stringArray[1] + EnumChatFormatting.GOLD + "' does not exist");
                    }
                } else if (bl2) {
                    arrayList.add("Usage: /config delete <Config Name>");
                } else {
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Usage: /config delete <Config Name>");
                }
            } else if (bl2) {
                arrayList.add("Arguments: ");
                arrayList.add("Save");
                arrayList.add("Load");
                arrayList.add("Delete");
                arrayList.add("List");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Arguments: ");
                ChatUtils.sendMessage("Save");
                ChatUtils.sendMessage("Load");
                ChatUtils.sendMessage("Delete");
                ChatUtils.sendMessage("List");
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

