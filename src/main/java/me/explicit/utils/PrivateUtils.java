/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.util.Timer
 */
package me.explicit.utils;

import java.lang.reflect.Field;
import me.explicit.module.player.FastPlace;
import me.explicit.utils.Game;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.Timer;

public class PrivateUtils {
    public static Timer timer() {
        try {
            Class<Minecraft> clazz = Minecraft.class;
            Field field = clazz.getDeclaredField(new String(new char[]{'t', 'i', 'm', 'e', 'r'}));
            field.setAccessible(true);
            return (Timer)field.get(Game.Minecraft());
        }
        catch (Exception exception) {
            try {
                Class<Minecraft> clazz = Minecraft.class;
                Field field = clazz.getDeclaredField(new String(new char[]{'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '2', '8', '_', 'T'}));
                field.setAccessible(true);
                return (Timer)field.get(Game.Minecraft());
            }
            catch (Exception exception2) {
                return null;
            }
        }
    }

    public static void setRightClickDelayTimer(int n) {
        FastPlace.placeDelay = n;
        try {
            Field field = Minecraft.class.getDeclaredField("field_71467_ac");
            field.setAccessible(true);
            field.set(Game.Minecraft(), n);
        }
        catch (Exception exception) {
            try {
                Field field = Minecraft.class.getDeclaredField("rightClickDelayTimer");
                field.setAccessible(true);
                field.set(Game.Minecraft(), n);
            }
            catch (Exception exception2) {
                // empty catch block
            }
        }
    }

    public static void setBlockDamage(float f) {
        try {
            Field field = PlayerControllerMP.class.getDeclaredField("field_78770_f");
            field.setAccessible(true);
            field.set(Game.Minecraft().playerController, Float.valueOf(f));
        }
        catch (Exception exception) {
            try {
                Field field = PlayerControllerMP.class.getDeclaredField("curBlockDamageMP");
                field.setAccessible(true);
                field.set(Game.Minecraft().playerController, Float.valueOf(f));
            }
            catch (Exception exception2) {
                // empty catch block
            }
        }
    }

    public static void setBlockHitDelay(int n) {
        try {
            Field field = PlayerControllerMP.class.getDeclaredField("field_78781_i");
            field.setAccessible(true);
            field.set(Game.Minecraft().playerController, n);
        }
        catch (Exception exception) {
            try {
                Field field = PlayerControllerMP.class.getDeclaredField("blockHitDelay");
                field.setAccessible(true);
                field.set(Game.Minecraft().playerController, n);
            }
            catch (Exception exception2) {

            }
        }
    }

    public static float getBlockDamage() {
        try {
            Class<PlayerControllerMP> clazz = PlayerControllerMP.class;
            Field field = clazz.getDeclaredField("field_78770_f");
            field.setAccessible(true);
            return ((Float)field.get(Game.Minecraft().playerController)).floatValue();
        }
        catch (Exception exception) {
            try {
                Class<PlayerControllerMP> clazz = PlayerControllerMP.class;
                Field field = clazz.getDeclaredField("curBlockDamageMP");
                field.setAccessible(true);
                return ((Float)field.get(Game.Minecraft().playerController)).floatValue();
            }
            catch (Exception exception2) {
                return -1.0f;
            }
        }
    }
}

