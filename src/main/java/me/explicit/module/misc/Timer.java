/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.module.misc;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.PrivateUtils;

public class Timer
extends Module {
    public Timer() {
        super("Timer", 0, Category.MISC, "Increases the speed of your Minecraft client");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("TimerSpeed", this, 1.25, 0.1, 10.0, false));
    }

    @Override
    public void onUpdate() {
        PrivateUtils.timer().timerSpeed = (float)Explicit.instance.sm.getSettingByName(this, "TimerSpeed").getValDouble();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        PrivateUtils.timer().timerSpeed = 1.0f;
    }
}

