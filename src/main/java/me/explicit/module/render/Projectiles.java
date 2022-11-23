/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.Vec3
 *  org.lwjgl.opengl.GL11
 */
package me.explicit.module.render;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.ItemUtils;
import me.explicit.utils.PrivateUtils;
import me.explicit.utils.RenderUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Projectiles
extends Module {
    public Projectiles() {
        super("Projectiles", 0, Category.RENDER, "Allows you to see where your projectiles may land");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Box", this, true));
    }

    @Override
    public void onRender3D() {
        if (Game.World() != null && Game.Player() != null) {
            EntityPlayerSP entityplayersp = Game.Player();
            ItemStack itemstack = entityplayersp.inventory.getCurrentItem();

            if (itemstack != null) {
                if (ItemUtils.isThrowable(itemstack)) {
                    boolean flag = false;

                    if (Game.Player().getCurrentEquippedItem() != null && (Game.Player().getCurrentEquippedItem().getItem() instanceof ItemBow || Game.Player().getCurrentEquippedItem().getItem() instanceof ItemFood || Game.Player().getCurrentEquippedItem().getItem() instanceof ItemPotion)) {
                        flag = true;
                    }

                    if (!flag || Projectiles.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                        boolean flag1 = itemstack.getItem() instanceof ItemBow;
                        double d0 = entityplayersp.lastTickPosX + (entityplayersp.posX - entityplayersp.lastTickPosX) * (double) PrivateUtils.timer().renderPartialTicks - Math.cos((double) ((float) Math.toRadians((double) entityplayersp.rotationYaw))) * 0.1599999964237213D;
                        double d1 = entityplayersp.lastTickPosY + (entityplayersp.posY - entityplayersp.lastTickPosY) * (double) PrivateUtils.timer().renderPartialTicks + (double) entityplayersp.getEyeHeight();
                        double d2 = entityplayersp.lastTickPosZ + (entityplayersp.posZ - entityplayersp.lastTickPosZ) * (double) PrivateUtils.timer().renderPartialTicks - Math.sin((double) ((float) Math.toRadians((double) entityplayersp.rotationYaw))) * 0.1599999964237213D;
                        float f = flag1 ? 1.0F : 0.4F;
                        float f1 = (float) Math.toRadians((double) entityplayersp.rotationYaw);
                        float f2 = (float) Math.toRadians((double) entityplayersp.rotationPitch);
                        float f3 = (float) (-Math.sin((double) f1) * Math.cos((double) f2) * (double) f);
                        float f4 = (float) (-Math.sin((double) f2) * (double) f);
                        float f5 = (float) (Math.cos((double) f1) * Math.cos((double) f2) * (double) f);
                        double d3 = Math.sqrt((double) (f3 * f3 + f4 * f4 + f5 * f5));

                        f3 /= (float) d3;
                        f4 /= (float) d3;
                        f5 /= (float) d3;
                        if (flag1) {
                            float f6 = (float) (72000 - entityplayersp.getItemInUseCount()) / 20.0F;

                            f6 = (f6 * f6 + f6 * 2.0F) / 3.0F;
                            if (f6 > 1.0F || f6 <= 0.1F) {
                                f6 = 1.0F;
                            }

                            f6 *= 3.0F;
                            f3 *= f6;
                            f4 *= f6;
                            f5 *= f6;
                        } else {
                            f3 = (float) ((double) f3 * 1.5D);
                            f4 = (float) ((double) f4 * 1.5D);
                            f5 = (float) ((double) f5 * 1.5D);
                        }


                        GL11.glPushMatrix();
                        GL11.glDisable(3553);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        GL11.glDisable(2929);
                        GL11.glDepthMask(false);
                        GL11.glEnable(2848);
                        GL11.glLineWidth(2.0F);
                        
                        double d7 = flag1 ? 0.05D : (itemstack.getItem() instanceof ItemPotion ? 0.4D : (itemstack.getItem() instanceof ItemFishingRod ? 0.15D : 0.03D));

                        new Vec3(entityplayersp.posX, entityplayersp.posY + (double) entityplayersp.getEyeHeight(), entityplayersp.posZ);
                        GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.75F);
                        GL11.glBegin(3);

                        for (int i = 0; i < 1000; ++i) {
                            GL11.glVertex3d(d0 - Minecraft.getMinecraft().getRenderManager().viewerPosX, d1 - Minecraft.getMinecraft().getRenderManager().viewerPosY, d2 - Minecraft.getMinecraft().getRenderManager().viewerPosZ);
                            d0 += (double) f3 * 0.1D;
                            d1 += (double) f4 * 0.1D;
                            d2 += (double) f5 * 0.1D;
                            f3 *= 0.999F;
                            f4 *= 0.999F;
                            f5 *= 0.999F;
                            f4 -= (float) (d7 * 0.1D);
                            IBlockState iblockstate = Game.World().getBlockState(new BlockPos(d0, d1, d2));

                            if (!Game.World().isAirBlock(new BlockPos(d0, d1, d2)) && !iblockstate.getBlock().getUnlocalizedName().toLowerCase().contains("grass")) {
                                break;
                            }
                        }

                        GL11.glEnd();
                        double d8 = d0 - Minecraft.getMinecraft().getRenderManager().viewerPosX;
                        double d9 = d1 - Minecraft.getMinecraft().getRenderManager().viewerPosY;
                        double d10 = d2 - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

                        GL11.glPushMatrix();
                        GL11.glTranslated(d8 - 0.5D, d9 - 0.5D, d10 - 0.5D);
                        if (Explicit.instance.sm.getSettingByName(this, "Box").getValBoolean()) {
                            GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.25F);
                            RenderUtils.drawSolidBox();
                            GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.75F);
                            RenderUtils.drawOutlinedBox();
                        }

                        GL11.glPopMatrix();
                        GL11.glDisable(3042);
                        GL11.glEnable(3553);
                        GL11.glEnable(2929);
                        GL11.glDepthMask(true);
                        GL11.glDisable(2848);
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }
}

