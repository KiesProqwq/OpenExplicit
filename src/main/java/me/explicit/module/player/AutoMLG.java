/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.item.ItemBucket
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package me.explicit.module.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import io.netty.util.internal.ThreadLocalRandom;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.PrivateUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.BlockPos;

public class AutoMLG
extends Module {
    private int oldSlot;
    private boolean lastFell;

    public AutoMLG() {
        super("AutoMLG", 0, Category.PLAYER, "Automatically places water to prevent you from dying");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("MinFall", this, 4.0, 3.0, 10.0, false));
        Explicit.instance.sm.rSetting(new Setting("AutoAim", this, false));
        Explicit.instance.sm.rSetting(new Setting("AutoSwitch", this, true));
        Explicit.instance.sm.rSetting(new Setting("AutoPickUp", this, false));
    }

    @Override
    public void onUpdate() {
        boolean flag = Explicit.instance.sm.getSettingByName(this, "AutoAim").getValBoolean();
        boolean flag1 = Explicit.instance.sm.getSettingByName(this, "AutoSwitch").getValBoolean();
        boolean flag2 = Explicit.instance.sm.getSettingByName(this, "AutoPickUp").getValBoolean();
        double d0 = Explicit.instance.sm.getSettingByName(this, "MinFall").getValDouble();
        
        if ((double) Game.Player().fallDistance > d0 && this.isBlockUnderneath() && !AutoMLG.mc.thePlayer.onGround && this.getWaterBucketSlot() != -1) {
            if (flag1) {
                AutoMLG.mc.thePlayer.inventory.currentItem = this.getWaterBucketSlot();
            }

            if (this.isHoldingWaterBucket()) {
                if (flag) {
                    Game.Player().rotationPitch = 90.0F;
                }

                PrivateUtils.setRightClickDelayTimer(0);
                KeyBinding.setKeyBindState(AutoMLG.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                this.lastFell = true;
                if (flag2) {
                    Thread thread = new Thread(new Runnable() {
                        final boolean val$autoaim = flag;
                        final AutoMLG this$0 = AutoMLG.this;

                        public void run() {
                            try {
                                Thread.sleep(100L);
                            } catch (InterruptedException interruptedexception) {
                                interruptedexception.printStackTrace();
                            }

                            if (this.this$0.isHoldingEmptyBucket()) {
                                if (this.val$autoaim) {
                                    Game.Player().rotationPitch = 90.0F;
                                }

                                if (!AutoMLG.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                                    KeyBinding.setKeyBindState(AutoMLG.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                                }

                                try {
                                    Thread.sleep(ThreadLocalRandom.current().nextLong(200L, 500L));
                                } catch (InterruptedException interruptedexception1) {
                                    interruptedexception1.printStackTrace();
                                }

                                KeyBinding.setKeyBindState(AutoMLG.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                            }

                        }
                    });

                    thread.start();
                }
            } else {
                this.StopMLG();
            }
        } else {
            this.StopMLG();
        }

    }

    private void StopMLG() {
        if (!this.lastFell) {
            this.oldSlot = AutoMLG.mc.thePlayer.inventory.currentItem;
        } else {
            AutoMLG.mc.thePlayer.inventory.currentItem = this.oldSlot;
            KeyBinding.setKeyBindState(AutoMLG.mc.gameSettings.keyBindUseItem.getKeyCode(), this.isKeyDown(AutoMLG.mc.gameSettings.keyBindUseItem.getKeyCode()));
            PrivateUtils.setRightClickDelayTimer(4);
        }

        this.lastFell = false;
    }

    private boolean isBlockUnderneath() {
        boolean flag = false;

        for (int i = 0; (double) i < Module.mc.thePlayer.posY + 2.0D; ++i) {
            BlockPos blockpos = new BlockPos(Module.mc.thePlayer.posX, (double) i, Module.mc.thePlayer.posZ);

            if (!(Module.mc.theWorld.getBlockState(blockpos).getBlock() instanceof BlockAir)) {
                flag = true;
            }
        }

        return flag;
    }

    private boolean isHoldingWaterBucket() {
        return Game.Player().getCurrentEquippedItem() != null && Game.Player().getCurrentEquippedItem().getItem().getUnlocalizedName().toLowerCase().contains("water");
    }

    private boolean isHoldingEmptyBucket() {
        return Game.Player().getCurrentEquippedItem() != null && Game.Player().getCurrentEquippedItem().getItem() instanceof ItemBucket && !Game.Player().getCurrentEquippedItem().getItem().getUnlocalizedName().toLowerCase().contains("water") && !Game.Player().getCurrentEquippedItem().getItem().getUnlocalizedName().toLowerCase().contains("milk") && !Game.Player().getCurrentEquippedItem().getItem().getUnlocalizedName().toLowerCase().contains("lava");
    }

    private int getWaterBucketSlot() {
        for (int i = 0; i < 9; ++i) {
            if (Game.Player().inventory.getStackInSlot(i) != null && (Game.Player().inventory.getStackInSlot(i).getItem() instanceof ItemBucket && !Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("lava") && Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("water") || Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("milk"))) {
                return i;
            }
        }

        return -1;
    }

    private int getEmptyBucketSlot() {
        for (int i = 0; i < 9; ++i) {
            if (Game.Player().inventory.getStackInSlot(i) != null && Game.Player().inventory.getStackInSlot(i).getItem() instanceof ItemBucket && !Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("lava") && !Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("water") && !Game.Player().inventory.getStackInSlot(i).getItem().getUnlocalizedName().toLowerCase().contains("milk")) {
                return i;
            }
        }
        return -1;
    }

    private boolean isKeyDown(int i) {
        return i < 0 ? Mouse.isButtonDown(i + 100) : Keyboard.isKeyDown(i);
    }
}

