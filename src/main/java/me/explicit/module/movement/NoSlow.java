/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.explicit.module.movement;

import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.Game;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlow
extends Module {
    public NoSlow() {
        super("NoSlow", 0, Category.MOVEMENT, "Move while blocking");
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent livingupdateevent) {
        if (Game.Player() != null && Game.Player().isUsingItem() && !Game.Player().isRiding()) {
            EntityPlayerSP entityplayersp = Game.Player();

            entityplayersp.moveForward *= 5.0F;
            entityplayersp = Game.Player();
            entityplayersp.moveStrafing *= 5.0F;
            MovementInput movementinput = Game.Player().movementInput;

            movementinput.moveForward *= 5.0F;
            movementinput = Game.Player().movementInput;
            movementinput.moveStrafe *= 5.0F;
            entityplayersp = Game.Player();
            entityplayersp.motionX *= 1.1D;
            entityplayersp = Game.Player();
            entityplayersp.motionY *= 1.1D;
        }

    }
}

