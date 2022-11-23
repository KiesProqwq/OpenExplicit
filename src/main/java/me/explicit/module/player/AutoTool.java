/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Mouse
 */
package me.explicit.module.player;

import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.Game;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AutoTool
extends Module {
    private int oldSlot = -1;
    private boolean wasBreaking = false;

    public AutoTool() {
        super("AutoTool", "Automatically switches to the best tool to use", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.oldSlot = mc.thePlayer.inventory.currentItem;
        this.wasBreaking = false;
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent renderTickEvent) {
    	if (mc.currentScreen == null && Mouse.isButtonDown(0) && Game.Player() != null && Game.World() != null && mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null && mc.objectMouseOver.entityHit == null) {
            try {
                float f = 1.0F;
                int i = -1;
                Block block = mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();

                for (int j = 0; j < 8; ++j) {
                    ItemStack itemstack = Game.Player().inventory.getStackInSlot(j);

                    if (itemstack != null) {
                        float f1 = itemstack.getStrVsBlock(block);

                        if (f1 > f) {
                            f = f1;
                            i = j;
                        }
                    }
                }

                if (i != -1 && mc.thePlayer.inventory.currentItem != i) {
                    mc.thePlayer.inventory.currentItem = i;
                    this.wasBreaking = true;
                } else if (i == -1) {
                    if (this.wasBreaking) {
                        mc.thePlayer.inventory.currentItem = this.oldSlot;
                        this.wasBreaking = false;
                    }

                    this.oldSlot = mc.thePlayer.inventory.currentItem;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (Game.Player() != null && Game.World() != null) {
            if (this.wasBreaking) {
                mc.thePlayer.inventory.currentItem = this.oldSlot;
                this.wasBreaking = false;
            }

            this.oldSlot = mc.thePlayer.inventory.currentItem;
        }
    }
}

