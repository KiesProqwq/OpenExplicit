/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.event.entity.living.LivingAttackEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.explicit.module.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.CombatUtils;
import me.explicit.utils.Game;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Reach
extends Module {
    float ab;
    float bb;
    boolean cb;
    boolean d;
    int timeout;
    int hits;
    boolean misplace;
    public Random e = new Random();
    private List Players;
    private List oldPos;
    private List currentPos;

    public Reach() {
        super("Reach", 0, Category.COMBAT, "Increases your reach distance");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (this.Players == null) {
            this.Players = new ArrayList();
            this.Players.clear();
        } else {
            this.Players.clear();
        }
        if (this.oldPos == null) {
            this.oldPos = new ArrayList();
            this.oldPos.clear();
        } else {
            this.oldPos.clear();
        }
        if (this.currentPos == null) {
            this.currentPos = new ArrayList();
            this.currentPos.clear();
        } else {
            this.currentPos.clear();
        }
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("MinReach", this, 3.0, 3.0, 6.0, false));
        Explicit.instance.sm.rSetting(new Setting("MaxReach", this, 3.8, 3.0, 6.0, false));
        Explicit.instance.sm.rSetting(new Setting("TimeoutHits", this, 0.0, 0.0, 20.0, true));
        Explicit.instance.sm.rSetting(new Setting("Misplace", this, false));
        Explicit.instance.sm.rSetting(new Setting("WeaponOnly", this, false));
        Explicit.instance.sm.rSetting(new Setting("SprintOnly", this, true));
        Explicit.instance.sm.rSetting(new Setting("GroundOnly", this, false));
    }

    @Override
    public void onUpdateNoToggle() {
        this.misplace = Explicit.instance.sm.getSettingByName(this, "Misplace").getValBoolean();
        if (this.misplace) {
            Explicit.instance.sm.getSettingByName(this, "TimeoutHits").setVisible(false);
            Explicit.instance.sm.getSettingByName(this, "WeaponOnly").setVisible(false);
            Explicit.instance.sm.getSettingByName(this, "SprintOnly").setVisible(false);
            Explicit.instance.sm.getSettingByName(this, "GroundOnly").setVisible(false);
        } else {
            Explicit.instance.sm.getSettingByName(this, "TimeoutHits").setVisible(true);
            Explicit.instance.sm.getSettingByName(this, "WeaponOnly").setVisible(true);
            Explicit.instance.sm.getSettingByName(this, "SprintOnly").setVisible(true);
            Explicit.instance.sm.getSettingByName(this, "GroundOnly").setVisible(true);
        }
    }

    @SubscribeEvent
    public void mous(MouseEvent mouseEvent) {
        if (Explicit.destructed) {
            return;
        }
        this.ab = (float)Explicit.instance.sm.getSettingByName(this, "MinReach").getValDouble();
        this.bb = (float)Explicit.instance.sm.getSettingByName(this, "MaxReach").getValDouble();
        this.cb = Explicit.instance.sm.getSettingByName(this, "WeaponOnly").getValBoolean();
        this.timeout = (int)Explicit.instance.sm.getSettingByName(this, "TimeoutHits").getValDouble();
        this.misplace = Explicit.instance.sm.getSettingByName(this, "Misplace").getValBoolean();
        this.d = false;
        if (this.canReach()) {
            if (Reach.mc.objectMouseOver != null) {
                BlockPos blockpos = Reach.mc.objectMouseOver.getBlockPos();

                if (blockpos != null && Game.World().getBlockState(blockpos).getBlock() != Blocks.air) {
                    return;
                }
            }

            double d0 = (double) this.ab + this.e.nextDouble() * (double) (this.bb - this.ab);
            Object[] aobject = add(d0, 0.0D, 0.0F);

            if (aobject != null) {
                Reach.mc.objectMouseOver = new MovingObjectPosition((Entity) aobject[0], (Vec3) aobject[1]);
                Reach.mc.pointedEntity = (Entity) aobject[0];
            }
        }
    }
    
    public boolean canReach() {
        boolean flag = Explicit.instance.sm.getSettingByName(this, "SpirntOnly").getValBoolean();
        boolean flag1 = Explicit.instance.sm.getSettingByName(this, "GroundOnly").getValBoolean();

        if (flag1 && !Game.Player().onGround) {
            return false;
        } else if (flag && !Game.Player().isSprinting()) {
            return false;
        } else if (this.misplace) {
            return false;
        } else {
            if (this.cb) {
                if (Game.Player().getCurrentEquippedItem() == null) {
                    return false;
                }

                if (!(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemSword) && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                    return false;
                }
            }

            return this.hits >= this.timeout;
        }
    }

    @SubscribeEvent
    public void onAttack(LivingAttackEvent livingAttackEvent) {
        if (livingAttackEvent.entityLiving != null && livingAttackEvent.source.getEntity() != null && livingAttackEvent.source.getEntity() == Reach.mc.thePlayer && livingAttackEvent.ammount > 0.0f && CombatUtils.canTarget((Entity)livingAttackEvent.entityLiving, true)) {
            if (Reach.mc.thePlayer.getDistanceToEntity((Entity)livingAttackEvent.entityLiving) >= Math.min(this.ab, this.bb)) {
                if (this.hits < this.timeout) {
                    ++this.hits;
                } else if (this.hits >= this.timeout) {
                    this.hits = 0;
                }
            } else {
                ++this.hits;
            }
        }
    }

    public static Object[] add(double d0, double d1, float f) {
        Entity entity = Reach.mc.getRenderViewEntity();
        Entity entity1 = null;

        if (entity != null && Game.World() != null) {
            Reach.mc.mcProfiler.startSection("pick");
            Vec3 vec3 = entity.getPositionEyes(0.0F);
            Vec3 vec31 = entity.getLook(0.0F);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            Vec3 vec33 = null;
            List list = Game.World().getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(1.0D, 1.0D, 1.0D));
            double d2 = d0;

            for (int i = 0; i < list.size(); ++i) {
                Entity entity2 = (Entity) list.get(i);

                if (entity2.canBeCollidedWith()) {
                    float f2 = entity2.getCollisionBorderSize();
                    AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().expand((double) f2, (double) f2, (double) f2);

                    axisalignedbb = axisalignedbb.expand(d1, d1, d1);
                    MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                    if (axisalignedbb.isVecInside(vec3)) {
                        if (0.0D < d2 || d2 == 0.0D) {
                            entity1 = entity2;
                            vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                            d2 = 0.0D;
                        }
                    } else if (movingobjectposition != null) {
                        double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                        if (d3 < d2 || d2 == 0.0D) {
                            boolean flag = false;

                            if (entity2 == entity.ridingEntity) {
                                if (d2 == 0.0D) {
                                    entity1 = entity2;
                                    vec33 = movingobjectposition.hitVec;
                                }
                            } else {
                                entity1 = entity2;
                                vec33 = movingobjectposition.hitVec;
                                d2 = d3;
                            }
                        }
                    }
                }
            }

            if (d2 < d0 && !CombatUtils.canTarget(entity1, true)) {
                entity1 = null;
            }

            Reach.mc.mcProfiler.endSection();
            if (entity1 != null && vec33 != null) {
                return new Object[] { entity1, vec33};
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

