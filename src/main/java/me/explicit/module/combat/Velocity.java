/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.explicit.module.combat;

import java.util.concurrent.ThreadLocalRandom;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity
extends Module {
    public Velocity() {
        super("Velocity", 0, Category.COMBAT, "Reduces your knockback");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Horizontal", this, 90.0, 0.0, 100.0, true, true));
        Explicit.instance.sm.rSetting(new Setting("Vertical", this, 100.0, 0.0, 100.0, true, true));
        Explicit.instance.sm.rSetting(new Setting("Chance", this, 60.0, 0.0, 100.0, true, true));
        Explicit.instance.sm.rSetting(new Setting("SprintOnly", this, true));
        Explicit.instance.sm.rSetting(new Setting("TargetingOnly", this, true));
        Explicit.instance.sm.rSetting(new Setting("WeaponOnly", this, false));
        Explicit.instance.sm.rSetting(new Setting("NoLiquid", this, false));
    }

    @SubscribeEvent
    public void onEv(LivingEvent.LivingUpdateEvent livingUpdateEvent) {
        float f = (float)Explicit.instance.sm.getSettingByName(this, "Horizontal").getValDouble();
        float f1 = (float)Explicit.instance.sm.getSettingByName(this, "Vertical").getValDouble();
        if (this.canVelocity() && Game.Player().hurtTime == Game.Player().maxHurtTime && Game.Player().maxHurtTime > 0) {
            EntityPlayerSP entityplayersp = Game.Player();

            entityplayersp.motionX *= (double) f / 100.0D;
            EntityPlayerSP entityplayersp1 = Game.Player();

            entityplayersp1.motionY *= (double) f1 / 100.0D;
            EntityPlayerSP entityplayersp2 = Game.Player();

            entityplayersp2.motionZ *= (double) f / 100.0D;
        }
    }
    
    public boolean canVelocity() {
        int i = 100 - (int) Explicit.instance.sm.getSettingByName(this, "Chance").getValDouble();
        boolean flag = Explicit.instance.sm.getSettingByName(this, "SprintOnly").getValBoolean();
        boolean flag1 = Explicit.instance.sm.getSettingByName(this, "TargetingOnly").getValBoolean();
        boolean flag2 = Explicit.instance.sm.getSettingByName(this, "WeaponOnly").getValBoolean();
        boolean flag3 = Explicit.instance.sm.getSettingByName(this, "NoLiquid").getValBoolean();

        if (Game.World() != null && Game.Player() != null && (!flag || Game.Player().isSprinting()) && i < ThreadLocalRandom.current().nextInt(0, 101)) {
            if (flag1 && Velocity.mc.objectMouseOver != null && Velocity.mc.objectMouseOver.entityHit == null) {
                return false;
            } else {
                if (flag2) {
                    if (Game.Player().getCurrentEquippedItem() == null) {
                        return false;
                    }

                    if (!(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemSword) && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                        return false;
                    }
                }

                return !Game.Player().isInWater() && !Game.Player().isInLava() || !flag3;
            }
        } else {
            return false;
        }
    }
}

