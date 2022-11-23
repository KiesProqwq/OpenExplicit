/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 */
package me.explicit.module.combat;

import java.util.List;

import io.netty.util.internal.ThreadLocalRandom;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.CombatUtils;
import me.explicit.utils.Game;
import me.explicit.utils.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

public class RodAimbot
extends Module {
    private Entity target;

    public RodAimbot() {
        super("RodAimAssist", 0, Category.COMBAT, "Helps with aiming the rod");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("HSpeed", this, 30.0, 3.0, 250.0, true));
        Explicit.instance.sm.rSetting(new Setting("VSpeed", this, 30.0, 3.0, 250.0, true));
        Explicit.instance.sm.rSetting(new Setting("Range", this, 8.0, 1.0, 17.5, false));
        Explicit.instance.sm.rSetting(new Setting("FOV", this, 100.0, 1.0, 360.0, true));
    }

    @Override
    public void onUpdate() {
        if (Game.Player() != null && Game.World() != null) {
            if (this.target == null || this.target != null && !this.isValid(this.target)) {
                this.target = this.getTarget();
            }

            if (this.target != null) {
                this.Aim();
            }
        }
    }

    private void Aim() {
        if (this.target == null) {
            return;
        }
        int n = Explicit.instance.sm.getSettingByName(this, "HSpeed").getValInt();
        int n2 = Explicit.instance.sm.getSettingByName(this, "VSpeed").getValInt();
        Entity entity = this.getTarget();
        if (entity != null && (this.getY(entity) > 1.0 || this.getY(entity) < -1.0)) {
            boolean bl = this.getY(entity) > 0.0;
            EntityPlayerSP entityPlayerSP = Game.Player();
            entityPlayerSP.rotationYaw = entityPlayerSP.rotationYaw + (float)(bl ? -(Math.abs(this.getY(entity)) / (double)n) : Math.abs(this.getY(entity)) / (double)n);
            float[] fArray = RotationUtils.getRotations(entity);
            if (this.target == null) {
                return;
            }
            float f = fArray[1] - Game.Player().getDistanceToEntity(this.target) + Game.Player().getDistanceToEntity(this.target) / 4.0f - Game.Player().rotationPitch;
            entityPlayerSP.rotationPitch = (float)((double)entityPlayerSP.rotationPitch + (double)f / ((double)n2 + ThreadLocalRandom.current().nextDouble()));
        }
    }

    /*
     * Exception decompiling
     */
    public Entity getTarget() {
        Entity entity = null;
        int i = (int) Explicit.instance.sm.getSettingByName(this, "FOV").getValDouble();

        if (Game.World().loadedEntityList != null && Game.Player().getCurrentEquippedItem() != null && Game.Player().getCurrentEquippedItem().getItem().getUnlocalizedName().toLowerCase().contains("rod")) {
            List list = Game.World().loadedEntityList;

            for (int j = 0; j < list.size(); ++j) {
                Entity entity1 = (Entity) list.get(j);

                if (this.isValid(entity1) && this.can(entity1, (float) i)) {
                    entity = entity1;
                    i = (int) this.getY(entity1);
                }
            }

            return entity;
        } else {
            return null;
        }
    }

    private boolean isValid(Entity entity) {
        return RotationUtils.canEntityBeSeen(entity) && entity.isEntityAlive() && entity != Game.Player() && (double) Game.Player().getDistanceToEntity(entity) <= Explicit.instance.sm.getSettingByName(this, "Range").getValDouble() && CombatUtils.canTarget(entity, true);
    }

    public boolean can(Entity entity, float f) {
        f = (float) ((double) f * 0.5D);
        double d0 = ((double) (Game.Player().rotationYaw - this.yaw(entity)) % 360.0D + 540.0D) % 360.0D - 180.0D;

        return d0 > 0.0D && d0 < (double) f || (double) (-f) < d0 && d0 < 0.0D;
    }

    public float yaw(Entity entity) {
        double d0 = entity.posX - Game.Player().posX;
        double d1 = entity.posY - Game.Player().posY;
        double d2 = entity.posZ - Game.Player().posZ;
        double d3 = Math.atan2(d0, d2) * 57.2957795D;

        d3 = -d3;
        double d4 = Math.asin(d1 / Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 57.2957795D;

        d4 = -d4;
        return (float) d3;
    }

    public double getY(Entity entity) {
        return ((double) (Game.Player().rotationYaw - this.yaw(entity)) % 360.0D + 540.0D) % 360.0D - 180.0D;
    }
}

