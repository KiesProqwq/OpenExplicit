/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 */
package me.explicit.module.combat;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.CombatUtils;
import me.explicit.utils.Game;
import me.explicit.utils.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class HitBoxes
extends Module {
    private Entity pointedEntity;
    private MovingObjectPosition moving;
    public static float hitBoxMultiplier = 1.0f;

    public HitBoxes() {
        super("Hitbox", 0, Category.COMBAT, "Expands entities hitboxes");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Distance", this, 30.0, 1.0, 180.0, true));
    }

    @SubscribeEvent
    public void mouse(MouseEvent mouseEvent) {
        if (this.moving != null && mouseEvent.button == 0 && (HitBoxes.mc.objectMouseOver.getBlockPos() == null || Game.World().isAirBlock(HitBoxes.mc.objectMouseOver.getBlockPos()))) {
            mc.objectMouseOver = this.moving;
        }
    }

    public void onClick() {
    }

    @SubscribeEvent
    public void tick(TickEvent.RenderTickEvent renderTickEvent) {
        if (Game.World() != null) {
            hitBoxMultiplier = (float)Explicit.instance.sm.getSettingByName(this, "Distance").getValDouble();
        }
        this.getMouseOver(1.0f);
    }

    @Override
    public void onUpdate() {
        if (Game.World() != null) {
            hitBoxMultiplier = (float)Explicit.instance.sm.getSettingByName(this, "Distance").getValDouble();
        }
        this.getMouseOver(1.0f);
    }

    private void getMouseOver(float f) {
        if (mc.getRenderViewEntity() != null && Game.World() != null) {
            double d;
            HitBoxes.mc.pointedEntity = null;
            double d2 = 3.0;
            if (Explicit.instance.mm.getModuleByName("Reach").isToggled()) {
                double d3;
                d = Explicit.instance.sm.getSettingByName(Explicit.instance.mm.getModuleByName("Reach"), "MinReach").getValDouble();
                d2 = d > (d3 = Explicit.instance.sm.getSettingByName(Explicit.instance.mm.getModuleByName("Reach"), "MaxReach").getValDouble()) || d == d3 ? d : ThreadLocalRandom.current().nextDouble(d, d3);
            }
            this.moving = mc.getRenderViewEntity().rayTrace(d2, f);
            d = d2;
            Vec3 vec3 = mc.getRenderViewEntity().getPositionEyes(f);
            if (this.moving != null) {
                d = this.moving.hitVec.distanceTo(vec3);
            }
            Vec3 vec32 = mc.getRenderViewEntity().getLook(f);
            Vec3 vec33 = vec3.addVector(vec32.xCoord * d2, vec32.yCoord * d2, vec32.zCoord * d2);
            this.pointedEntity = null;
            Vec3 vec34 = null;
            float f2 = 1.0f;
            List list = Game.World().getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec32.xCoord * d2, vec32.yCoord * d2, vec32.zCoord * d2).expand((double)f2, (double)f2, (double)f2));
            double d4 = d;
            int n = 0;
            if (n < list.size()) {
                Entity entity = (Entity)list.get(n);
                if (entity.canBeCollidedWith() && CombatUtils.canTarget(entity, true) && RotationUtils.canEntityBeSeen(entity)) {
                    double d5;
                    float f3 = 0.13f * hitBoxMultiplier;
                    AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().expand((double)f3, (double)f3, (double)f3);
                    MovingObjectPosition movingObjectPosition = axisAlignedBB.calculateIntercept(vec3, vec33);
                    if (axisAlignedBB.isVecInside(vec3)) {
                        if (0.0 < d4 || d4 == 0.0) {
                            this.pointedEntity = entity;
                            vec34 = movingObjectPosition == null ? vec3 : movingObjectPosition.hitVec;
                            d4 = 0.0;
                        }
                    } else if (movingObjectPosition != null && ((d5 = vec3.distanceTo(movingObjectPosition.hitVec)) < d4 || d4 == 0.0)) {
                        if (entity == mc.getRenderViewEntity().ridingEntity && !entity.canRiderInteract()) {
                            if (d4 == 0.0) {
                                this.pointedEntity = entity;
                                vec34 = movingObjectPosition.hitVec;
                            }
                        } else {
                            this.pointedEntity = entity;
                            vec34 = movingObjectPosition.hitVec;
                            d4 = d5;
                        }
                    }
                }
                ++n;
                return;
            }
            if (this.pointedEntity != null && (d4 < d || this.moving == null)) {
                this.moving = new MovingObjectPosition(this.pointedEntity, vec34);
                if (CombatUtils.canTarget(this.pointedEntity, true) || this.pointedEntity instanceof EntityItemFrame) {
                    HitBoxes.mc.pointedEntity = this.pointedEntity;
                }
            }
        }
    }
}

