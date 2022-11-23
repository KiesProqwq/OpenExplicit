/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.explicit.module.player;

import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.Game;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Safewalk
extends Module {
    public Safewalk() {
        super("Safewalk", 0, Category.PLAYER, "Auto Bridge");
    }

    @Override
    public void onTick() {
        if (mc.thePlayer == null || Game.World() == null || !Game.Player().onGround) {
            return;
        }
        double d0 = (double) ((int) Game.Player().posX) - Game.Player().posX;
        double d1 = Game.Player().posZ - (double) ((int) Game.Player().posZ);

        System.out.println(d0 + " " + d1);
        if (Game.World().getBlockState((new BlockPos(Game.Player())).add(0, -1, 0)).getBlock() == Blocks.air) {
            Safewalk.mc.thePlayer.motionX = 0.0D;
            Safewalk.mc.thePlayer.motionY = 0.0D;
            Safewalk.mc.thePlayer.motionZ = 0.0D;
            Safewalk.mc.thePlayer.jumpMovementFactor = 0.0F;
            Safewalk.mc.thePlayer.noClip = true;
            Safewalk.mc.thePlayer.onGround = false;
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent livingUpdateEvent) {
    }

    @Override
    public void onDisable() {
    }
}

