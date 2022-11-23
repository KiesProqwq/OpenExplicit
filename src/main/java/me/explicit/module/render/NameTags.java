/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 *  net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package me.explicit.module.render;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.CombatUtils;
import me.explicit.utils.Game;
import me.explicit.utils.PrivateUtils;
import me.explicit.utils.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class NameTags
extends Module {
    private float scale;
    private String mode;
    boolean armor;
    boolean dura;
    boolean players = true;
    boolean invis;
    boolean mobs = false;
    boolean animals = false;
    private ArrayList entities;
    float _x;
    float _y;
    float _z;

    public NameTags() {
        super("NameTags", 0, Category.RENDER, "Display large nametags");
    }

    @Override
    public void setup() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Hearts");
        arrayList.add("Percentage");
        Explicit.instance.sm.rSetting(new Setting("HealthMode", this, "Percentage", arrayList));
        Explicit.instance.sm.rSetting(new Setting("Scale", this, 5.0, 0.1, 10.0, false));
        Explicit.instance.sm.rSetting(new Setting("Range", this, 0.0, 0.0, 512.0, true));
        Explicit.instance.sm.rSetting(new Setting("Armor", this, true));
        Explicit.instance.sm.rSetting(new Setting("Durability", this, false));
        Explicit.instance.sm.rSetting(new Setting("Distance", this, true));
    }

    @Override
    public void onUpdate() {
        this.scale = (float)Explicit.instance.sm.getSettingByName(this, "Scale").getValDouble();
        this.mode = Explicit.instance.sm.getSettingByName(this, "HealthMode").getValString();
        this.armor = Explicit.instance.sm.getSettingByName(this, "Armor").getValBoolean();
        this.dura = Explicit.instance.sm.getSettingByName(this, "Durability").getValBoolean();
    }

    @SubscribeEvent
    public void nameTag(RenderLivingEvent.Specials.Pre pre) {
    	if (pre.entity.getDisplayName().getFormattedText() != null && pre.entity.getDisplayName().getFormattedText() != "" && pre.entity instanceof EntityPlayer && CombatUtils.canTarget(pre.entity, false) && ((double) Game.Player().getDistanceToEntity(pre.entity) <= Explicit.instance.sm.getSettingByName(this, "Range").getValDouble() || Explicit.instance.sm.getSettingByName(this, "").getValDouble() == 0.0D)) {
            pre.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void render3d(RenderWorldLastEvent renderWorldLastEvent) {
        ArrayList<EntityPlayer> arrayList = new ArrayList<EntityPlayer>();
        if (arrayList.size() > 100) {
            arrayList.clear();
        }
        for (EntityLivingBase entityLivingBase : mc.theWorld.playerEntities) {
            if ((double)Game.Player().getDistanceToEntity((Entity)entityLivingBase) > Explicit.instance.sm.getSettingByName(this, "Range").getValDouble() && Explicit.instance.sm.getSettingByName(this, "Range").getValDouble() != 0.0) {
                if (!arrayList.contains(entityLivingBase)) continue;
                arrayList.remove(entityLivingBase);
                return;
            }
            if (entityLivingBase.getName().contains("[NPC]")) {
                if (!arrayList.contains(entityLivingBase)) continue;
                arrayList.remove(entityLivingBase);
                return;
            }
            if (entityLivingBase.isEntityAlive()) {
                if (entityLivingBase.isInvisible()) {
                    if (!arrayList.contains(entityLivingBase)) {
                        return;
                    }
                    arrayList.remove(entityLivingBase);
                } else if (entityLivingBase == mc.thePlayer) {
                    if (!arrayList.contains(entityLivingBase)) {
                        return;
                    }
                    arrayList.remove(entityLivingBase);
                } else {
                    if (arrayList.size() > 100) break;
                    if (arrayList.contains(entityLivingBase)) {
                        return;
                    }
                    arrayList.add((EntityPlayer)entityLivingBase);
                }
            } else {
                if (!arrayList.contains(entityLivingBase)) {
                    return;
                }
                arrayList.remove(entityLivingBase);
            }
            return;
        }
        this._x = 0.0f;
        this._y = 0.0f;
        this._z = 0.0f;
        Iterator iterator = arrayList.iterator();
        if (iterator.hasNext()) {
            EntityLivingBase entityLivingBase;
            entityLivingBase = (EntityPlayer)iterator.next();
            if (CombatUtils.canTarget((Entity)entityLivingBase, false)) {
                entityLivingBase.setAlwaysRenderNameTag(false);
                this._x = (float)(entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * (double)PrivateUtils.timer().renderPartialTicks - Game.Minecraft().getRenderManager().viewerPosX);
                this._y = (float)(entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * (double)PrivateUtils.timer().renderPartialTicks - Game.Minecraft().getRenderManager().viewerPosY);
                this._z = (float)(entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * (double)PrivateUtils.timer().renderPartialTicks - Game.Minecraft().getRenderManager().viewerPosZ);
                this.renderNametag((EntityPlayer)entityLivingBase, this._x, this._y, this._z);
            }
            return;
        }
    }

    private String getHealth(EntityPlayer entityPlayer) {
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        return this.mode.equalsIgnoreCase("Percentage") ? decimalFormat.format(entityPlayer.getHealth() * 5.0f + entityPlayer.getAbsorptionAmount() * 5.0f) : decimalFormat.format(entityPlayer.getHealth() / 2.0f + entityPlayer.getAbsorptionAmount() / 2.0f);
    }

    private void drawNames(EntityPlayer entityPlayer) {
        float f;
        float f2 = 2.2f;
        float f3 = (float)this.getWidth(this.getPlayerName(entityPlayer)) / 2.0f + 2.2f;
        f3 = f = (float)((double)f3 + ((double)(this.getWidth(" " + this.getHealth(entityPlayer)) / 2) + 2.5));
        float f4 = -f3 - 2.2f;
        float f5 = this.getWidth(this.getPlayerName(entityPlayer)) + 4;
        if (this.mode.equalsIgnoreCase("Percentage")) {
            RenderUtils.drawBorderedRect(f4, -3.0f, f3, 10.0f, 1.0f, new Color(20, 20, 20, 180).getRGB(), new Color(10, 10, 10, 200).getRGB());
        } else {
            RenderUtils.drawBorderedRect(f4 + 5.0f, -3.0f, f3, 10.0f, 1.0f, new Color(20, 20, 20, 180).getRGB(), new Color(10, 10, 10, 200).getRGB());
        }
        GlStateManager.disableDepth();
        f5 = this.mode.equalsIgnoreCase("Percentage") ? (f5 += (float)(this.getWidth(this.getHealth(entityPlayer)) + this.getWidth(" %") - 1)) : (f5 += (float)(this.getWidth(this.getHealth(entityPlayer)) + this.getWidth(" ") - 1));
        this.drawString(this.getPlayerName(entityPlayer), f - f5, 0.0f, 0xFFFFFF);
        if (entityPlayer.getHealth() == 10.0f) {

        }
        int n = entityPlayer.getHealth() > 10.0f ? RenderUtils.blend(new Color(-16711936), new Color(-256), 1.0f / entityPlayer.getHealth() / 2.0f * (entityPlayer.getHealth() - 10.0f)).getRGB() : RenderUtils.blend(new Color(-256), new Color(-65536), 0.1f * entityPlayer.getHealth()).getRGB();
        if (this.mode.equalsIgnoreCase("Percentage")) {
            this.drawString(String.valueOf(this.getHealth(entityPlayer)) + "%", f - (float)this.getWidth(String.valueOf(this.getHealth(entityPlayer)) + " %"), 0.0f, n);
        } else {
            this.drawString(this.getHealth(entityPlayer), f - (float)this.getWidth(String.valueOf(this.getHealth(entityPlayer)) + " "), 0.0f, n);
        }
        GlStateManager.enableDepth();
    }

    private void drawString(String string, float f, float f2, int n) {
        mc.fontRendererObj.drawStringWithShadow(string, f, f2, n);
    }

    private int getWidth(String string) {
        return Module.mc.fontRendererObj.getStringWidth(string);
    }

    private void startDrawing(float f, float f2, float f3, EntityPlayer entityPlayer) {
        float f4 = mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f;
        double d = (double)(this.getSize(entityPlayer) / 10.0f * this.scale) * 1.5;
        GL11.glPushMatrix();
        RenderUtils.startDrawing();
        GL11.glTranslatef((float)f, (float)f2, (float)f3);
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-mc.getRenderManager().playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)mc.getRenderManager().playerViewX, (float)f4, (float)0.0f, (float)0.0f);
        GL11.glScaled((double)(-0.01666666753590107 * d), (double)(-0.01666666753590107 * d), (double)(0.01666666753590107 * d));
    }

    private void stopDrawing() {
        RenderUtils.stopDrawing();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private void renderNametag(EntityPlayer entityPlayer, float f, float f2, float f3) {
        this.startDrawing(f, f2 += (float)(1.55 + (entityPlayer.isSneaking() ? 0.5 : 0.7)), f3, entityPlayer);
        this.drawNames(entityPlayer);
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        if (this.armor) {
            this.renderArmor(entityPlayer);
        }
        this.stopDrawing();
    }

    private void renderArmor(EntityPlayer entityPlayer) {
        ItemStack[] itemStackArray = entityPlayer.inventory.armorInventory;
        int n = itemStackArray.length;
        int n2 = 0;
        ItemStack[] itemStackArray2 = itemStackArray;
        int n3 = 0;
        int n4 = itemStackArray.length;
        if (n3 < n4) {
            ItemStack itemStack;
            ItemStack itemStack2 = itemStack = itemStackArray2[n3];
            if (itemStack != null) {
                n2 -= 8;
            }
            ++n3;
            return;
        }
        if (entityPlayer.getHeldItem() != null) {
            n2 -= 8;
            ItemStack itemStack = entityPlayer.getHeldItem().copy();
            if (itemStack.hasEffect() && (itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemArmor)) {
                itemStack.stackSize = 1;
            }
            this.renderItemStack(itemStack, n2, -20);
            n2 += 16;
        }
        itemStackArray = entityPlayer.inventory.armorInventory;
        n4 = 3;
        if (n4 >= 0) {
            ItemStack itemStack = itemStackArray[n4];
            if (itemStack != null) {
                this.renderItemStack(itemStack, n2, -20);
                n2 += 16;
            }
            --n4;
            return;
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private String getPlayerName(EntityPlayer entityPlayer) {
        boolean bl = Explicit.instance.sm.getSettingByName(this, "Distance").getValBoolean();
        return (bl ? new DecimalFormat("#.##").format(Game.Player().getDistanceToEntity((Entity)entityPlayer)) + "m " : "") + entityPlayer.getDisplayName().getFormattedText();
    }

    private float getSize(EntityPlayer entityPlayer) {
        return Module.mc.thePlayer.getDistanceToEntity((Entity)entityPlayer) / 4.0f <= 2.0f ? 2.0f : Module.mc.thePlayer.getDistanceToEntity((Entity)entityPlayer) / 4.0f;
    }

    private void renderItemStack(ItemStack itemstack, int i, int j) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -150.0F;
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemstack, i, j);
        mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, itemstack, i, j);
        mc.getRenderItem().zLevel = 0.0F;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();

        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.disableDepth();
        this.renderEnchantText(itemstack, i, j);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.popMatrix();
    }

    private void renderEnchantText(ItemStack itemStack, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - 24;
        if (itemStack.getEnchantmentTagList() != null && itemStack.getEnchantmentTagList().tagCount() >= 6) {
            Module.mc.fontRendererObj.drawStringWithShadow("god", (float)(n * 2), (float)n7, 0xFF0000);
            return;
        }
        if (itemStack.getItem() instanceof ItemArmor) {
            n6 = EnchantmentHelper.getEnchantmentLevel((int)Enchantment.protection.effectId, (ItemStack)itemStack);
            n5 = EnchantmentHelper.getEnchantmentLevel((int)Enchantment.projectileProtection.effectId, (ItemStack)itemStack);
            n4 = EnchantmentHelper.getEnchantmentLevel((int)Enchantment.blastProtection.effectId, (ItemStack)itemStack);
            n3 = EnchantmentHelper.getEnchantmentLevel((int)Enchantment.fireProtection.effectId, (ItemStack)itemStack);
            int n8 = EnchantmentHelper.getEnchantmentLevel((int)Enchantment.thorns.effectId, (ItemStack)itemStack);
            int n9 = EnchantmentHelper.getEnchantmentLevel((int)Enchantment.unbreaking.effectId, (ItemStack)itemStack);
            int n10 = itemStack.getMaxDamage() - itemStack.getItemDamage();
            if (this.dura) {
                Module.mc.fontRendererObj.drawStringWithShadow("" + n10, (float)(n * 2), (float)n2, 0xFFFFFF);
            }
            if (n6 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("prot" + n6, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n5 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("proj" + n5, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n4 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("bp" + n4, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n3 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("frp" + n3, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n8 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("th" + n8, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n9 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("unb" + n9, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
        }
        if (itemStack.getItem() instanceof ItemBow) {
            n6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
            n5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
            n4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack);
            n3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
            if (n6 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("pow" + n6, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n5 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("pun" + n5, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n4 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("flame" + n4, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n3 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("unb" + n3, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
        }
        if (itemStack.getItem() instanceof ItemSword) {
            n6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
            n5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemStack);
            n4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack);
            n3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
            if (n6 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("sh" + n6, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n5 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("kb" + n5, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n4 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("fire" + n4, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n3 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("unb" + n3, (float)(n * 2), (float)n7, -1);
            }
        }
        if (itemStack.getItem() instanceof ItemTool) {
            n6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
            n5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
            n4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemStack);
            n3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack);
            if (n5 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("eff" + n5, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n4 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("fo" + n4, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n3 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("silk" + n3, (float)(n * 2), (float)n7, -1);
                n7 += 8;
            }
            if (n6 > 0) {
                Module.mc.fontRendererObj.drawStringWithShadow("ub" + n6, (float)(n * 2), (float)n7, -1);
            }
        }
        if (itemStack.getItem() == Items.golden_apple && itemStack.hasEffect()) {
            mc.fontRendererObj.drawStringWithShadow("god", (float)(n * 2), (float)n7, -1);
        }
    }
}

