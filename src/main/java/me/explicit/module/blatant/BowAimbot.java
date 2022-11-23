/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.Vec3
 */
package me.explicit.module.blatant;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.CombatUtils;
import me.explicit.utils.Game;
import me.explicit.utils.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class BowAimbot
extends Module {
    private Entity target;

    public BowAimbot() {
        super("BowAimbot", 0, Category.BLATANT, "Automatically predicts where the player is going to be and aims you bow there");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("PredictSize", this, 10.0, 0.1, 20.0, false));
    }

    public void onTick() {
        if (!BowAimbot.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            this.target = null;
        } else {
            ItemStack itemstack = Game.Player().inventory.getCurrentItem();

            if (itemstack != null && itemstack.getItem() instanceof ItemBow) {
                if (this.target == null || !CombatUtils.canTarget(this.target, true) || !RotationUtils.canEntityBeSeen(this.target) || !this.target.isEntityAlive()) {
                    this.target = this.getBestEntity();
                }

                if (this.target != null) {
                    try {
                        float[] afloat = faceBow(this.target, false, true, (float) Explicit.instance.sm.getSettingByName(this, "PredictSize").getValDouble());

                        if (afloat.length < 2 || afloat[0] == Float.POSITIVE_INFINITY || afloat[0] == Float.NEGATIVE_INFINITY || afloat[1] == Float.POSITIVE_INFINITY || afloat[1] == Float.NEGATIVE_INFINITY) {
                            return;
                        }

                        float f;

                        for (f = afloat[0]; f > 360.0F; f -= 360.0F) {

                        }

                        mc.thePlayer.rotationYaw = f;
                        float f1 = afloat[1];

                        if (!Double.isNaN((double) f1)) {
                            mc.thePlayer.rotationPitch = afloat[1];
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        mc.thePlayer.rotationYaw = 0.0F;
                        mc.thePlayer.rotationPitch = 0.0F;
                    }

                }
            } else {
                this.target = null;
            }
        }
    }

    public Entity getBestEntity() {
        Entity entity = null;
        float f = Float.POSITIVE_INFINITY;

        for (Entity entity1 : Game.World().loadedEntityList) {

            if (entity1 != null && (!(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemFishingRod) || Game.Player().getDistanceToEntity(entity1) <= 30.0F) && CombatUtils.canTarget(entity1, true) && BowAimbot.mc.thePlayer.canEntityBeSeen(entity1) && BowAimbot.mc.thePlayer.canEntityBeSeen(entity1) && Game.Player().getDistanceToEntity(entity1) <= 75.0F) {
                new Vec3(0.5D, 1.0D, 0.5D);
                float[] afloat = RotationUtils.getRotations(entity1);
                float f1 = BowAimbot.mc.thePlayer.rotationYaw - afloat[0];
                float f2 = BowAimbot.mc.thePlayer.rotationPitch - afloat[1];
                float f3 = (float) Math.sqrt((double) (f1 * f1 + f2 * f2));

                if (f3 < f) {
                    entity = entity1;
                    f = f3;
                }
            }
        }

        return entity;
    }

    public static float[] faceBow(Entity entity, boolean flag, boolean flag1, float f) {
        EntityPlayerSP entityplayersp = BowAimbot.mc.thePlayer;
        double d0 = entity.posX + (flag1 ? (entity.posX - entity.prevPosX) * (double) f : 0.0D) - (entityplayersp.posX + (flag1 ? entityplayersp.posX - entityplayersp.prevPosX : 0.0D));
        double d1 = entity.getEntityBoundingBox().minY + (flag1 ? (entity.getEntityBoundingBox().minY - entity.prevPosY) * (double) f : 0.0D) + (double) entity.getEyeHeight() - 0.15D - (entityplayersp.getEntityBoundingBox().minY + (flag1 ? entityplayersp.posY - entityplayersp.prevPosY : 0.0D)) - (double) entityplayersp.getEyeHeight();
        double d2 = entity.posZ + (flag1 ? (entity.posZ - entity.prevPosZ) * (double) f : 0.0D) - (entityplayersp.posZ + (flag1 ? entityplayersp.posZ - entityplayersp.prevPosZ : 0.0D));
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        float f1 = (float) entityplayersp.getItemInUseDuration() / 20.0F;

        f1 = (f1 * f1 + f1 * 2.0F) / 3.0F;
        if (f1 > 1.0F) {
            f1 = 1.0F;
        }

        float f2 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float f3 = (float) (-Math.toDegrees(Math.atan(((double) (f1 * f1) - Math.sqrt((double) (f1 * f1 * f1 * f1) - 0.006000000052154064D * (0.006000000052154064D * d3 * d3 + 2.0D * d1 * (double) (f1 * f1)))) / (0.006000000052154064D * d3))));

        return new float[] { f2, f3};
    }
}

