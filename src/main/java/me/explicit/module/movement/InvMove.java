/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.settings.KeyBinding
 *  org.lwjgl.input.Keyboard
 */
package me.explicit.module.movement;

import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.Game;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class InvMove
extends Module {
    public InvMove() {
        super("InvMove", "Lets you move while in your inventory", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (InvMove.mc.currentScreen instanceof GuiInventory || InvMove.mc.currentScreen instanceof GuiChest) {
            this.setKeys();
        }
    }

    @Override
    public void onDisable() {
        this.setKeys();
    }

    private void setKeys() {
        if (Game.Player() != null && Game.World() != null) {
            int i = InvMove.mc.gameSettings.keyBindForward.getKeyCode();
            int j = InvMove.mc.gameSettings.keyBindBack.getKeyCode();
            int k = InvMove.mc.gameSettings.keyBindRight.getKeyCode();
            int l = InvMove.mc.gameSettings.keyBindLeft.getKeyCode();
            int i1 = InvMove.mc.gameSettings.keyBindJump.getKeyCode();
            int j1 = InvMove.mc.gameSettings.keyBindSneak.getKeyCode();
            int k1 = InvMove.mc.gameSettings.keyBindSprint.getKeyCode();

            KeyBinding.setKeyBindState(i, Keyboard.isKeyDown(i));
            KeyBinding.setKeyBindState(j, Keyboard.isKeyDown(j));
            KeyBinding.setKeyBindState(k, Keyboard.isKeyDown(k));
            KeyBinding.setKeyBindState(l, Keyboard.isKeyDown(l));
            KeyBinding.setKeyBindState(i1, Keyboard.isKeyDown(i1));
            KeyBinding.setKeyBindState(j1, Keyboard.isKeyDown(j1));
            KeyBinding.setKeyBindState(k1, Keyboard.isKeyDown(k1));
        }
    }
}

