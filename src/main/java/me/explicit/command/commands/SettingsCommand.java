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
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.ChatUtils;
import me.explicit.utils.Game;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class SettingsCommand
implements ICommand {
    public int compareTo(ICommand iCommand) {
        return 0;
    }

    public String getCommandName() {
        return "-set";
    }

    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/-set";
    }

    public List getCommandAliases() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("-setting");
        arrayList.add("-settings");
        return arrayList;
    }

    public void processCommand(ICommandSender iCommandSender, String[] stringArray) throws CommandException {
        this.executeCommand(stringArray);
    }

    @SuppressWarnings("unchecked")
	public List executeCommand(String[] var1) {
        boolean var2 = Explicit.consolegui;
        ArrayList var3;
        (var3 = new ArrayList()).clear();
        if (Explicit.destructed) {
            Game.Player().addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Unknown command. Try /help for a list of commands"));
            return var3;
        } else {
            if (var1.length < 3) {
                if (var2) {
                    var3.add("Usage: /-set <Module Name> <Settings Name> <Value>");
                } else {
                    ChatUtils.sendMessage("Usage: /-set <Module> <Settings Name> <Value>");
                }
            } else if (Explicit.instance.mm.getModuleByName(var1[0]) != null) {
                Module var4 = Explicit.instance.mm.getModuleByName(var1[0]);
                Setting var5 = Explicit.instance.sm.getSettingByName(var4, var1[1]);
                if (var5 == null) {
                    if (var2) {
                        var3.add("The setting '" + var1[1] + "' doesn't exist");
                    } else {
                        ChatUtils.sendMessage(EnumChatFormatting.GOLD + "The setting '" + var1[1] + "' doesn't exist");
                    }
                } else {
                    String var6 = var1[2];
                    if (var6 != null) {
                        try {
                            if (var5.isCheck()) {
                                if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
                                    if (var2) {
                                        var3.add("Sorry, " + var1[1] + "'s value cannot be '" + var6 + "'");
                                        var3.add("It can only be true or false");
                                    } else {
                                        ChatUtils.sendMessage("Sorry, " + var1[1] + "'s value cannot be '" + var6 + "'");
                                        ChatUtils.sendMessage("It can only be true or false");
                                    }
                                } else {
                                    var5.setValBoolean(Boolean.parseBoolean(var6));
                                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully set " + var5.getName() + " to " + var6);
                                }
                            } else if (var5.isCombo()) {
                                boolean var7 = false;
                                Iterator var8 = var5.getOptions().iterator();
                                if (var8.hasNext()) {
                                    String var16 = (String)var8.next();
                                    if (var16.equalsIgnoreCase(var6)) {
                                        var5.setValString(var6);
                                        var7 = true;
                                    }

                                    return null;
                                }

                                if (!var7) {
                                    if (var2) {
                                        var3.add("Sorry, " + var1[1] + "'s value cannot be '" + var6 + "'");
                                        var3.add("It can only be true or false");
                                    } else {
                                        ChatUtils.sendMessage("Sorry, " + var1[1] + "'s value cannot be '" + var6 + "'");
                                        String var13 = "It can only be ";
                                        Iterator var9 = var5.getOptions().iterator();
                                        if (var9.hasNext()) {
                                            String var10 = (String)var9.next();
                                            if (var10 == var5.getOptions().get(var5.getOptions().size() - 2)) {
                                                var13 = var13 + var10 + " or ";
                                            } else if (var10 == var5.getOptions().get(var5.getOptions().size() - 1)) {
                                                (new StringBuilder()).append(var13).append(var10).toString();
                                            } else {
                                                var13 = var13 + var10 + ", ";
                                            }

                                            return null;
                                        }

                                        ChatUtils.sendMessage(var13);
                                    }
                                } else {
                                    var5.setValString(var6);
                                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully set " + var5.getName() + " to " + var6);
                                }
                            } else if (var5.isSlider()) {
                                if (!(Double.parseDouble(var6) < var5.getMin()) && !(Double.parseDouble(var6) > var5.getMax())) {
                                    var5.setValDouble(Double.parseDouble(var6));
                                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Successfully set " + var5.getName() + " to " + var6);
                                } else if (var2) {
                                    var3.add("Sorry, " + var1[1] + "'s value cannot be '" + var6 + "'");
                                    var3.add("It can only be true or false");
                                } else {
                                    ChatUtils.sendMessage("Sorry, " + var1[1] + "'s value cannot be '" + var6 + "'");
                                    ChatUtils.sendMessage("It can only be a number between " + var5.getMin() + " and " + var5.getMax());
                                }
                            }
                        } catch (NumberFormatException var11) {
                            if (var2) {
                                var3.add("Sorry, " + var1[1] + "'s value cannot be '" + var6 + "'");
                                var3.add("It can only be true or false");
                            } else {
                                ChatUtils.sendMessage("Sorry, " + var1[1] + "'s value cannot be '" + var6 + "'");
                                ChatUtils.sendMessage("It can only be a number between " + var5.getMin() + " and " + var5.getMax());
                            }
                        }
                    }
                }
            } else if (var2) {
                var3.add("Sorry, the module '" + var1[0] + "' doesn't exist");
            } else {
                ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Sorry, the module '" + var1[0] + "' doesn't exist");
            }

            return var3;
        }
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

