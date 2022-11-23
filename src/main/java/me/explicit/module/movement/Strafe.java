/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.explicit.module.movement;

import java.util.ArrayList;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.MoveUtils;
import me.explicit.utils.PrivateUtils;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Strafe
extends Module {
    public Strafe() {
        super("Strafe", 0, Category.MOVEMENT, "Strafe faster");
    }

    @Override
    public void setup() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Timer");
        arrayList.add("Motion");
        Explicit.instance.sm.rSetting(new Setting("Mode", this, "Timer", arrayList));
        Explicit.instance.sm.rSetting(new Setting("Speed", this, 1.08, 0.1, 3.0, false));
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent livingUpdateEvent) {
        double d = Explicit.instance.sm.getSettingByName(this, "Speed").getValDouble();
        String string = Explicit.instance.sm.getSettingByName(this, "Mode").getValString();
        if (livingUpdateEvent.entityLiving != null && livingUpdateEvent.entityLiving == Game.Player()) {
            if (string.equalsIgnoreCase("Motion")) {
            	if (Game.Player().moveStrafing != 0.0F && Game.Player().onGround && Game.Player().ticksExisted % 2 == 0) {
                    MoveUtils.setMoveSpeed(d / 3.0);
                }
            } else if (string.equalsIgnoreCase("Timer")) {
            	if (Game.Player().moveStrafing != 0.0F && Game.Player().onGround) {
                    PrivateUtils.timer().timerSpeed = (float)d;
                } else if (!Explicit.instance.mm.getModuleByName("Timer").isToggled()) {
                    PrivateUtils.timer().timerSpeed = 1.0f;
                }
            }
        }
    }
}

