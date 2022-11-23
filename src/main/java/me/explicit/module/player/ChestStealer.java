/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.module.player;

import java.util.concurrent.ThreadLocalRandom;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.ItemUtils;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class ChestStealer
extends Module {
    TimerUtils timer = new TimerUtils();
    double delay;
    private boolean checkName;

    public ChestStealer() {
        super("ChestStealer", 0, Category.PLAYER, "Automatically steals items from chests");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("MinDelay", this, 100.0, 0.0, 500.0, true));
        Explicit.instance.sm.rSetting(new Setting("MaxDelay", this, 150.0, 0.0, 500.0, true));
        Explicit.instance.sm.rSetting(new Setting("IgnoreItems", this, true));
        Explicit.instance.sm.rSetting(new Setting("CheckName", this, false));
    }

    @Override
    public void onEnable() {
        this.setDelay();
        super.onEnable();
    }
    
    @Override
    public void onUpdate() {
        this.checkName = Explicit.instance.sm.getSettingByName(this, "CheckName").getValBoolean();

        if (Module.mc.currentScreen instanceof GuiChest) {
            GuiChest guichest = (GuiChest) Module.mc.currentScreen;
            ContainerChest containerchest = (ContainerChest) Game.Player().openContainer;

            if ((containerchest.getLowerChestInventory().getName().toLowerCase().contains("menu") || containerchest.getLowerChestInventory().getName().toLowerCase().contains("play") || containerchest.getLowerChestInventory().getName().toLowerCase().contains("select") || containerchest.getLowerChestInventory().getName().toLowerCase().contains("teleport")) && this.checkName) {
                return;
            }

            if (this.isChestEmpty(guichest) || this.isInventoryFull()) {
                Module.mc.thePlayer.closeScreen();
                return;
            }

            for (int k = 0; k < containerchest.getLowerChestInventory().getSizeInventory(); ++k) {
                ItemStack itemstack = containerchest.getLowerChestInventory().getStackInSlot(k);

                if (itemstack != null && this.timer.hasReached(this.delay) && (this.isValidItem(itemstack) || !Explicit.instance.sm.getSettingByName(this, "IgnoreItems").getValBoolean())) {
                    Module.mc.playerController.windowClick(guichest.inventorySlots.windowId, k, 0, 1, Module.mc.thePlayer);
                    this.setDelay();
                    this.timer.reset();
                    break;
                }
            }
        }

    }

    private boolean isValidItem(ItemStack itemstack) {
        return itemstack != null && (ItemUtils.compareDamage(itemstack, ItemUtils.bestSword()) != null && ItemUtils.compareDamage(itemstack, ItemUtils.bestSword()) == itemstack || itemstack.getItem() instanceof ItemBlock || itemstack.getItem() instanceof ItemPotion && !ItemUtils.isBadPotion(itemstack) || itemstack.getItem() instanceof ItemArmor || itemstack.getItem() instanceof ItemAppleGold || itemstack.getItem() instanceof ItemFood);
    }

    public void setDelay() {
        double d0 = Explicit.instance.sm.getSettingByName(this, "MinDelay").getValDouble();
        double d1 = Explicit.instance.sm.getSettingByName(this, "MaxDelay").getValDouble();

        if (d0 == d1) {
            d1 = d0 * 1.1D;
        }

        this.delay = ThreadLocalRandom.current().nextDouble(Math.min(d0, d1), Math.max(d0, d1));
    }

    private boolean isChestEmpty(GuiChest guichest) {
        ContainerChest containerchest = (ContainerChest) Game.Player().openContainer;

        for (int i = 0; i < containerchest.getLowerChestInventory().getSizeInventory(); ++i) {
            ItemStack itemstack = containerchest.getLowerChestInventory().getStackInSlot(i);

            if (itemstack != null && (this.isValidItem(itemstack) || !Explicit.instance.sm.getSettingByName(this, "IgnoreItems").getValBoolean())) {
                return false;
            }
        }

        return true;
    }

    private boolean isInventoryFull() {
        for (int i = 9; i <= 44; ++i) {
            ItemStack itemstack = Module.mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemstack == null) {
                return false;
            }
        }

        return true;
    }
}

