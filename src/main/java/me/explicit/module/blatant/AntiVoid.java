/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 */
package me.explicit.module.blatant;

import io.netty.util.internal.ThreadLocalRandom;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.Game;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.BlockPos;

public class AntiVoid
extends Module {
    public AntiVoid() {
        super("AntiVoid", "Prevents you from falling in the void. Only works on servers with anti-cheats", Category.BLATANT);
    }

    public void onUpdate() {
        if (Game.Player() != null && Game.Player().fallDistance > 4.0F && !this.isBlockUnderneath()) {
            Game.Player().sendQueue.addToSendQueue(new C04PacketPlayerPosition(Game.Player().posX, Game.Player().posY + ThreadLocalRandom.current().nextDouble(8.0D, 12.0D), Game.Player().posZ, false));
        }
    }
    
    public boolean isBlockUnderneath() {
        for (int i = 0; (double) i < AntiVoid.mc.thePlayer.posY + 1.0D; ++i) {
            if (AntiVoid.mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, (double) i, AntiVoid.mc.thePlayer.posZ)).getBlock().getMaterial() != Material.air) {
                return true;
            }
        }
        return false;
    }
}

