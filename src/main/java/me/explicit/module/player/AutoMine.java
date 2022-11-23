/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Mouse
 */
package me.explicit.module.player;

import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.Game;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

import org.lwjgl.input.Mouse;

public class AutoMine
extends Module {
    private TimerUtils timer = new TimerUtils();

    public AutoMine() {
        super("AutoMine", 0, Category.PLAYER, "Automatically mines for you");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.timer.reset();
        System.out.println();
    }

    @SubscribeEvent
    public void rty(RenderTickEvent rendertickevent) {
        int i;

        if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null && !Game.World().isAirBlock(mc.objectMouseOver.getBlockPos()) && !Mouse.isButtonDown(0)) {
            i = mc.gameSettings.keyBindAttack.getKeyCode();
            KeyBinding.setKeyBindState(i, true);
            KeyBinding.onTick(i);
            this.timer.reset();
        } else if (this.timer.hasReached(30.0D)) {
            i = mc.gameSettings.keyBindAttack.getKeyCode();
            KeyBinding.setKeyBindState(i, false);
        }

    }
}

