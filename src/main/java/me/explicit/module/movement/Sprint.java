/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.settings.KeyBinding
 */
package me.explicit.module.movement;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.MoveUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class Sprint
extends Module {
    public Sprint() {
        super("Sprint", 0, Category.MOVEMENT, "Allows you to always sprint");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Omni", this, false));
    }
    
    @SubscribeEvent
    public void PlayerTick(PlayerTickEvent playertickevent) {
        if (mc.inGameHasFocus && !Game.Player().isSneaking()) {
            EntityPlayerSP entityplayersp = Game.Player();
            if (MoveUtils.PlayerMoving() && entityplayersp.getFoodStats().getFoodLevel() > 6) {
                entityplayersp.setSprinting(true);
            }
            
//            if (Explicit.instance.sm.getSettingByName(this, "0mni").getValBoolean()) {
//
//            } else {
//                KeyBinding.setKeyBindState(Sprint.mc.gameSettings.keyBindSprint.getKeyCode(), true);
//            }
        }
    }
}

