/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  org.lwjgl.input.Keyboard
 */
package me.explicit.module.movement;

import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.Game;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class Parkour
extends Module {
    public Parkour() {
        super("Parkour", "Automatically jumps at the edge of blocks", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (Game.Player() == null || Game.World() == null || !Parkour.mc.gameSettings.keyBindForward.isKeyDown()) {
            KeyBinding.setKeyBindState((int)Parkour.mc.gameSettings.keyBindJump.getKeyCode(), (boolean)Keyboard.isKeyDown((int)Parkour.mc.gameSettings.keyBindJump.getKeyCode()));
            return;
        }
        if (Game.World().getBlockState(new BlockPos((Entity)Game.Player()).add(0, -1, 0)).getBlock() == Blocks.air && mc.thePlayer.onGround) {
            KeyBinding.setKeyBindState((int)Parkour.mc.gameSettings.keyBindJump.getKeyCode(), (boolean)true);
        } else {
            KeyBinding.setKeyBindState((int)Parkour.mc.gameSettings.keyBindJump.getKeyCode(), (boolean)Keyboard.isKeyDown((int)Parkour.mc.gameSettings.keyBindJump.getKeyCode()));
        }
    }
}

