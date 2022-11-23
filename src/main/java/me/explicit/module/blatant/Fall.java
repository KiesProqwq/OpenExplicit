/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook
 *  net.minecraft.util.BlockPos
 */
package me.explicit.module.blatant;

import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.Game;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

public class Fall
extends Module {
    public Fall() {
        super("NoFall", 0, Category.BLATANT, "Completely removes your fall damage");
    }

    public void onTick() {
        if (Game.Player().fallDistance > 3.0F && this.isBlockUnderneath()) {
            Fall.mc.getNetHandler().addToSendQueue(new C06PacketPlayerPosLook(Game.Player().posX, Game.Player().posY, Game.Player().posZ, Game.Player().rotationYaw, Game.Player().rotationPitch, true));
            Game.Player().fallDistance = 0.0F;
        }

    }

    private boolean isBlockUnderneath() {
        boolean flag = false;

        for (int i = 0; (double) i < Game.Player().posY + 2.0D; ++i) {
            BlockPos blockpos = new BlockPos(Game.Player().posX, (double) i, Game.Player().posZ);

            if (!(Module.mc.theWorld.getBlockState(blockpos).getBlock() instanceof BlockAir)) {
                flag = true;
            }
        }

        return flag;
    }

    public double getDistanceToGround() {
        int i = 0;

        for (int j = 0; (double) j < Game.Player().posY; ++j) {
            BlockPos blockpos = new BlockPos(Game.Player().posX, Game.Player().posY - (double) j, Game.Player().posZ);

            if (Game.World().isAirBlock(blockpos)) {
                ++i;
            }
        }
        return (double) i;
    }
}

