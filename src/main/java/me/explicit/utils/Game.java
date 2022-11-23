/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 */
package me.explicit.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public class Game {
    public static EntityPlayerSP Player() {
        return Game.Minecraft().thePlayer;
    }

    public static WorldClient World() {
        return Game.Minecraft().theWorld;
    }

    public static Minecraft Minecraft() {
        return Minecraft.getMinecraft();
    }
}

