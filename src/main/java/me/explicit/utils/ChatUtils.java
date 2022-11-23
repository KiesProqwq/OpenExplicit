/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 */
package me.explicit.utils;

import me.explicit.utils.Game;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ChatUtils {
    public static void sendMessage(String string) {
        if (string == null) {
            return;
        }
        EntityPlayerSP entityPlayerSP = Game.Player();
        Object[] objectArray = new Object[2];
        StringBuilder stringBuilder = new StringBuilder().append(EnumChatFormatting.DARK_AQUA);
        objectArray[0] = stringBuilder.append("Explicit").append(EnumChatFormatting.GRAY).append(": ").toString();
        objectArray[1] = string;
        entityPlayerSP.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.DARK_GREEN + "[" + EnumChatFormatting.AQUA + "Explicit" + EnumChatFormatting.DARK_GREEN + "] " + EnumChatFormatting.GRAY + string));
    }
}

