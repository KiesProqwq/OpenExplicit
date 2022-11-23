/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.module.blatant;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.MoveUtils;

public class Fly
extends Module {
    public Fly() {
        super("Flight", 0, Category.BLATANT, "Allows you to fly like in creative mode");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Speed", this, 1.0, 0.05, 5.0, false));
    }


    public void onTick() {
        boolean flag = Fly.mc.gameSettings.keyBindJump.isKeyDown();
        boolean flag1 = Fly.mc.gameSettings.keyBindSneak.isKeyDown();
        double d0 = Explicit.instance.sm.getSettingByName(this, "Speed").getValDouble();

        if (!flag && !flag1) {
            Game.Player().motionY = 0.0D;
        } else if (flag) {
            Game.Player().motionY = d0;
        } else if (flag1) {
            Game.Player().motionY = -d0;
        }

        MoveUtils.setMoveSpeed(d0);
    }

    public void onDisable() {
        super.onDisable();
    }
}

