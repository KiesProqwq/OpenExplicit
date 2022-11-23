/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.explicit.module.player;

import io.netty.util.internal.ThreadLocalRandom;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;

public class AutoHotbar
extends Module {
    private TimerUtils timer = new TimerUtils();
    private double speed;

    public AutoHotbar() {
        super("AutoHotbar", 0, Category.PLAYER, "Automatically moves items to your hotbar");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Projectiles", this, false));
        Explicit.instance.sm.rSetting(new Setting("SplashPotions", this, true));
        Explicit.instance.sm.rSetting(new Setting("OtherPotions", this, false));
        Explicit.instance.sm.rSetting(new Setting("Soup", this, true));
        
        Explicit.instance.sm.rSetting(new Setting("MinSpeed", this, 0.2, 0.0, 1.0, false));
        Explicit.instance.sm.rSetting(new Setting("MaxSpeed", this, 0.4, 0.0, 1.001, false));
    }

    @Override
    public void onEnable() {
        this.updateSpeed();
    }

    private void updateSpeed() {
        double d;
        double d2 = Explicit.instance.sm.getSettingByName(this, "MinSpeed").getValDouble();
        if (d2 == (d = Explicit.instance.sm.getSettingByName(this, "MaxSpeed").getValDouble())) {
            d = d2 + 1.0;
        }
        this.speed = ThreadLocalRandom.current().nextDouble(Math.min(d2, d), Math.max(d2, d));
    }

    @Override
    public void onUpdate() {
        if (mc.currentScreen instanceof GuiInventory && this.timer.hasReached(this.speed * 1000.0)) {
            if (!this.isHotbarFull() && this.getItem() != -1) {
                this.shiftClick(this.getItem());
                this.updateSpeed();
            }
            this.timer.reset();
            this.updateSpeed();
        }
    }

    private int getItem() {
        for (int i = 9; i < 36; ++i) {
            if (this.isValidItem(Game.Player().inventory.mainInventory[i])) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean isHotbarFull() {
        for (int i = 0; i < 9; ++i) {
            if (Game.Player().inventory.mainInventory[i] == null) {
                return false;
            }
        }
        return true;
    }

    private void shiftClick(int i) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 0, 1, mc.thePlayer);
    }
//error
    private boolean isValidItem(ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        } else {
//            boolean flag = Explicit.instance.sm.getSettingByName(this, "SplashPotions").getValBoolean();
//            boolean flag1 = Explicit.instance.sm.getSettingByName(this, AutoHotbar.lIIlIlIl[11]).getValBoolean();
//            boolean flag2 = Explicit.instance.sm.getSettingByName(this, AutoHotbar.lIIlIlIl[12]).getValBoolean();
//            boolean flag3 = Explicit.instance.sm.getSettingByName(this, AutoHotbar.lIIlIlIl[13]).getValBoolean();
//
//            return Item.getIdFromItem(itemstack.getItem()) == 373 && flag && itemstack.getItem().getItemStackDisplayName(itemstack).toLowerCase().contains(AutoHotbar.lIIlIlIl[14]) ? true : (Item.getIdFromItem(itemstack.getItem()) == 373 && flag1 && !itemstack.getItem().getItemStackDisplayName(itemstack).toLowerCase().contains(AutoHotbar.lIIlIlIl[15]) ? true : ((Item.getIdFromItem(itemstack.getItem()) == 332 || Item.getIdFromItem(itemstack.getItem()) == 344) && flag2 ? true : Item.getIdFromItem(itemstack.getItem()) == 436 && flag3));
        }
		return false;
    }
}

