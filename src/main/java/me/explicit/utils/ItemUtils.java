/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemGlassBottle
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.DamageSource
 */
package me.explicit.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class ItemUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static ItemStack bestSword() {
        ItemStack itemstack = null;
        float f = 0.0F;

        for (int i = 9; i < 45; ++i) {
            if (ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack itemstack1 = ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                if (itemstack1.getItem() instanceof ItemSword) {
                    float f1 = getItemDamage(itemstack1);

                    if (f1 > f) {
                        f = f1;
                        itemstack = itemstack1;
                    }
                }
            }
        }

        return itemstack;
    }

    public static boolean isThrowable(ItemStack itemstack) {
        Item item = itemstack.getItem();

        return item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl || item instanceof ItemPotion && ItemPotion.isSplash(itemstack.getItemDamage());
    }

    public static ItemStack compareDamage(ItemStack itemstack, ItemStack itemstack1) {
        try {
            return itemstack != null && itemstack1 != null ? (!(itemstack.getItem() instanceof ItemSword) && itemstack1.getItem() instanceof ItemSword ? null : (getItemDamage(itemstack) > getItemDamage(itemstack1) ? itemstack : (getItemDamage(itemstack1) > getItemDamage(itemstack) ? itemstack1 : itemstack))) : null;
        } catch (NullPointerException nullpointerexception) {
            nullpointerexception.printStackTrace();
            return itemstack;
        }
    }

    public static boolean isBad(ItemStack itemStack) {
        return itemStack.getItem().getUnlocalizedName().contains("tnt") || itemStack.getItem().getUnlocalizedName().contains("stick") || itemStack.getItem().getUnlocalizedName().equalsIgnoreCase("egg") || itemStack.getItem().getUnlocalizedName().contains("string") || itemStack.getItem().getUnlocalizedName().contains("flint") || itemStack.getItem().getUnlocalizedName().contains("feather") || itemStack.getItem().getUnlocalizedName().contains("bucket") || itemStack.getItem().getUnlocalizedName().equalsIgnoreCase("chest") && !itemStack.getDisplayName().toLowerCase().contains("collect") || itemStack.getItem().getUnlocalizedName().contains("snow") || itemStack.getItem().getUnlocalizedName().contains("enchant") || itemStack.getItem().getUnlocalizedName().contains("exp") || itemStack.getItem().getUnlocalizedName().contains("shears") || itemStack.getItem().getUnlocalizedName().contains("arrow") || itemStack.getItem().getUnlocalizedName().contains("anvil") || itemStack.getItem().getUnlocalizedName().contains("torch") || itemStack.getItem().getUnlocalizedName().contains("skull") || itemStack.getItem().getUnlocalizedName().contains("seeds") || itemStack.getItem().getUnlocalizedName().contains("leather") || itemStack.getItem().getUnlocalizedName().contains("boat") || itemStack.getItem().getUnlocalizedName().contains("fishing") || itemStack.getItem().getUnlocalizedName().contains("wheat") || itemStack.getItem().getUnlocalizedName().contains("flower") || itemStack.getItem().getUnlocalizedName().contains("record") || itemStack.getItem().getUnlocalizedName().contains("note") || itemStack.getItem().getUnlocalizedName().contains("sugar") || itemStack.getItem().getUnlocalizedName().contains("wire") || itemStack.getItem().getUnlocalizedName().contains("trip") || itemStack.getItem().getUnlocalizedName().contains("slime") || itemStack.getItem().getUnlocalizedName().contains("web") || itemStack.getItem() instanceof ItemGlassBottle || itemStack.getItem() instanceof ItemArmor && !getBest().contains(itemStack) || itemStack.getItem() instanceof ItemSword && itemStack != bestSword() || itemStack.getItem().getUnlocalizedName().contains("piston") || itemStack.getItem().getUnlocalizedName().contains("potion") && isBadPotion(itemStack);
    }

    public static List<ItemStack> getBest() {
        ArrayList<ItemStack> arraylist = new ArrayList<ItemStack>();

        for (int i = 0; i < 4; ++i) {
            ItemStack itemstack = null;
            ItemStack[] aitemstack = ItemUtils.mc.thePlayer.inventory.armorInventory;
            int j = ItemUtils.mc.thePlayer.inventory.armorInventory.length;

            ItemStack itemstack1;

            for (int k = 0; k < j; ++k) {
                itemstack1 = aitemstack[k];
                if (itemstack1 != null && itemstack1.getItem() instanceof ItemArmor) {
                    ItemArmor itemarmor = (ItemArmor) itemstack1.getItem();

                    if (itemarmor.armorType == i) {
                        itemstack = itemstack1;
                    }
                }
            }

            double d0 = itemstack == null ? -1.0D : getArmorStrength(itemstack);

            itemstack1 = findBestArmor(i);
            if (itemstack1 != null && getArmorStrength(itemstack1) <= d0) {
                itemstack1 = itemstack;
            }

            if (itemstack1 != null) {
                arraylist.add(itemstack1);
            }
        }

        return arraylist;
    }

    public static ItemStack findBestArmor(int i) {
        ItemStack itemstack = null;
        double d0 = 0.0D;

        for (int j = 0; j < 36; ++j) {
            ItemStack itemstack1 = ItemUtils.mc.thePlayer.inventory.mainInventory[j];

            if (itemstack1 != null) {
                double d1 = getArmorStrength(itemstack1);

                if (d1 != -1.0D) {
                    ItemArmor itemarmor = (ItemArmor) itemstack1.getItem();

                    if (itemarmor.armorType == i && d1 >= d0) {
                        d0 = d1;
                        itemstack = itemstack1;
                    }
                }
            }
        }

        return itemstack;
    }

    public static double getArmorStrength(ItemStack itemstack) {
        if (!(itemstack.getItem() instanceof ItemArmor)) {
            return -1.0D;
        } else {
            float f = (float) ((ItemArmor) itemstack.getItem()).damageReduceAmount;
            Map<?, ?> map = EnchantmentHelper.getEnchantments(itemstack);

            if (map.containsKey(Integer.valueOf(Enchantment.protection.effectId))) {
                int i = ((Integer) map.get(Integer.valueOf(Enchantment.protection.effectId))).intValue();

                f += (float) Enchantment.protection.calcModifierDamage(i, DamageSource.generic);
            }

            return (double) f;
        }
    }

    public static boolean isBadPotion(ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() instanceof ItemPotion) {
            ItemPotion itempotion = (ItemPotion) itemstack.getItem();

            if (ItemPotion.isSplash(itemstack.getItemDamage())) {
                Iterator<?> iterator = itempotion.getEffects(itemstack).iterator();

                while (iterator.hasNext()) {
                    Object object = iterator.next();
                    PotionEffect potioneffect = (PotionEffect) object;

                    if (potioneffect.getPotionID() == Potion.poison.getId() || potioneffect.getPotionID() == Potion.harm.getId() || potioneffect.getPotionID() == Potion.moveSlowdown.getId() || potioneffect.getPotionID() == Potion.weakness.getId()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static float getItemDamage(ItemStack itemstack) {
        if (itemstack == null) {
            return 0.0F;
        } else if (!(itemstack.getItem() instanceof ItemSword)) {
            return 0.0F;
        } else {
            float f = ((ItemSword) itemstack.getItem()).getDamageVsEntity();

            f += (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemstack) * 1.25F;
            f += (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemstack) * 0.01F;
            return f;
        }
    }
}

