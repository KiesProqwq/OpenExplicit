/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.explicit.module.blatant;

import java.util.concurrent.ThreadLocalRandom;

import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.CombatUtils;
import me.explicit.utils.Game;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Criticals
extends Module {
    public Criticals() {
        super("Criticals", "WARNING: This will get you banned on Hypixel", Category.BLATANT);
    }

    @SubscribeEvent
    public void mous(MouseEvent mouseEvent) {
        if (Game.Player() != null && Game.World() != null && ThreadLocalRandom.current().nextInt(100) < 85 && mc.thePlayer.onGround && mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null && CombatUtils.canTarget(mc.objectMouseOver.entityHit, true) && mouseEvent.button == 0) {
            for (int i = 0; i < 2; ++i) {
                double d0 = ThreadLocalRandom.current().nextDouble(4.0E-6D, 6.0E-6D);

                mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + d0, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            }
        }
    }
}

