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

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class Eagle
extends Module {
    private TimerUtils sneakTimer = new TimerUtils();

    public Eagle() {
        super("Eagle", 0, Category.MOVEMENT, "Auto Bridge");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("OnSneak", this, true));
        Explicit.instance.sm.rSetting(new Setting("ChestSneak", this, false));
        Explicit.instance.sm.rSetting(new Setting("SneakTime", this, 500.0, 50.0, 1500.0, true));
    }

    @Override
    public void onTick() {
        if (Game.Player() == null || Game.World() == null) {
            KeyBinding.setKeyBindState((int)Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)false);
            return;
        }
        if (!Explicit.instance.sm.getSettingByName(this, "ChestSneak").getValBoolean() && Eagle.mc.currentScreen != null) {
            KeyBinding.setKeyBindState((int)Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)false);
            return;
        }
        if (Explicit.instance.sm.getSettingByName(this, "OnSneak").getValBoolean() && !Keyboard.isKeyDown((int)Eagle.mc.gameSettings.keyBindSneak.getKeyCode())) {
            KeyBinding.setKeyBindState((int)Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)false);
            return;
        }
        if (Game.World().getBlockState(new BlockPos((Entity)Game.Player()).add(0, -1, 0)).getBlock() == Blocks.air && Eagle.mc.thePlayer.onGround) {
            KeyBinding.setKeyBindState((int)Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)true);
            this.sneakTimer.reset();
        } else if (this.sneakTimer.hasReached(Explicit.instance.sm.getSettingByName(this, "SneakTime").getValInt())) {
            KeyBinding.setKeyBindState((int)Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)Keyboard.isKeyDown((int)Eagle.mc.gameSettings.keyBindSneak.getKeyCode()));
        } else {
            KeyBinding.setKeyBindState((int)Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)false);
        }
    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState((int)Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)false);
    }
}

