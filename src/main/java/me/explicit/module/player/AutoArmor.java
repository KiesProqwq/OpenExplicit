/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 */
package me.explicit.module.player;

import java.util.concurrent.ThreadLocalRandom;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class AutoArmor
extends Module {
    private TimerUtils timer = new TimerUtils();
    private TimerUtils dropTimer = new TimerUtils();
    private boolean dropping;
    private double delay;

    public AutoArmor() {
        super("AutoArmor", 0, Category.PLAYER, "Automatically equips armor");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("MinDelay", this, 100.0, 0.0, 1000.0, true));
        Explicit.instance.sm.rSetting(new Setting("MaxDelay", this, 150.0, 0.0, 1000.0, true));
        Explicit.instance.sm.rSetting(new Setting("Drop", this, true));
        Explicit.instance.sm.rSetting(new Setting("OpenInv", this, false));
    }

    @Override
    public void onTick() {
        double d;
        if (!this.isToggled()) {
            return;
        }
        String string = Explicit.instance.sm.getSettingByName(this, "OpenInv").getValBoolean() ? "OpenInv" : "Basic";
        double d2 = Explicit.instance.sm.getSettingByName(this, "MinDelay").getValDouble();
        if (d2 == (d = Explicit.instance.sm.getSettingByName(this, "MaxDelay").getValDouble())) {
            d = d2 * 1.1;
        }
        this.delay = ThreadLocalRandom.current().nextDouble(Math.min(d2, d), Math.max(d2, d));
        if (string.equalsIgnoreCase("OpenInv") && !(AutoArmor.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if ((AutoArmor.mc.currentScreen == null || AutoArmor.mc.currentScreen instanceof GuiInventory || AutoArmor.mc.currentScreen instanceof GuiChat) && this.timer.hasReached(this.delay)) {
            this.getBestArmor();
        }
        if (!this.dropping) {
            if ((AutoArmor.mc.currentScreen == null || AutoArmor.mc.currentScreen instanceof GuiInventory || AutoArmor.mc.currentScreen instanceof GuiChat) && this.timer.hasReached(this.delay)) {
                this.getBestArmor();
            }
        } else if (this.dropTimer.hasReached(this.delay)) {
            mc.playerController.windowClick(Module.mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, Module.mc.thePlayer);
            this.dropTimer.reset();
            this.dropping = false;
        }
    }

    public void getBestArmor() {
        int i;
        ItemStack itemstack;

        for (i = 1; i < 5; ++i) {
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(4 + i).getHasStack()) {
                itemstack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(4 + i).getStack();
                if (isBestArmor(itemstack, i)) {
                    continue;
                }

                if (Explicit.instance.sm.getSettingByName(this, "Drop").getValBoolean()) {
                    this.drop(4 + i);
                } else {
                    this.shiftClick(4 + i);
                }
            }

            for (int j = 9; j < 45; ++j) {
                if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
                    ItemStack itemstack1 = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(j).getStack();

                    if (isBestArmor(itemstack1, i) && getProtection(itemstack1) > 0.0F) {
                        this.shiftClick(j);
                        this.timer.reset();
                        if (this.delay > 0.0D) {
                            return;
                        }
                    }
                }
            }
        }

        if (Explicit.instance.sm.getSettingByName(this, "Drop").getValBoolean()) {
            for (i = 9; i < 45; ++i) {
                if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    itemstack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if ((getProtection(itemstack) > 0.0F || this.isDuplicate(itemstack, i)) && !this.dropping && itemstack.getItem() instanceof ItemArmor) {
                        this.drop(i);
                    }
                }
            }
        }

    }

	public static boolean isBestArmor(ItemStack stack, int type) {
		float prot = getProtection(stack);
		String strType = "";
		if (type == 1) {
			strType = "helmet";
		} else if (type == 2) {
			strType = "chestplate";
		} else if (type == 3) {
			strType = "leggings";
		} else if (type == 4) {
			strType = "boots";
		}
		if (!stack.getUnlocalizedName().contains(strType)) {
			return false;
		}
		for (int i = 5; i < 45; i++) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				if (getProtection(is) > prot && is.getUnlocalizedName().contains(strType))
					return false;
			}
		}
		return true;
	}

	public boolean isDuplicate(ItemStack itemstack, int i) {
        for (int j = 0; j < 45; ++j) {
            if (Module.mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
                ItemStack itemstack1 = Module.mc.thePlayer.inventoryContainer.getSlot(j).getStack();

                if (itemstack1 != itemstack && i != j && getProtection(itemstack) == getProtection(itemstack1) && itemstack1.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemPotion) && !(itemstack1.getItem() instanceof ItemBlock)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void shiftClick(int i) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, i, 0, 1, AutoArmor.mc.thePlayer);
    }

    public void drop(int n) {
        if (Explicit.instance.sm.getSettingByName(this, "Drop").getValBoolean() && this.dropTimer.hasReached(this.delay) && !this.dropping) {
        	mc.playerController.windowClick(Module.mc.thePlayer.inventoryContainer.windowId, n, 0, 0, Module.mc.thePlayer);
            this.dropping = true;
            this.dropTimer.reset();
        }
    }

    public static float getProtection(ItemStack itemStack) {
        float f = 0.0f;
        if (itemStack.getItem() instanceof ItemArmor) {
            ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
            
            f = (float) ((double) f + (double) itemArmor.damageReduceAmount + (double) ((100 - itemArmor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack)) * 0.0075D);
            f = (float) ((double) f + (double) EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, itemStack) / 100.0D);
            f = (float) ((double) f + (double) EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, itemStack) / 100.0D);
            f = (float) ((double) f + (double) EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, itemStack) / 100.0D);
            f = (float) ((double) f + (double) EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 50.0D);
            f = (float) ((double) f + (double) EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, itemStack) / 100.0D);
        }
        return f;
    }
}

