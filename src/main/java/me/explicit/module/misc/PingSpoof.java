/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.module.misc;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;

public class PingSpoof
extends Module {
    public static boolean toggled = false;
    public static int ping = 0;

    public PingSpoof() {
        super("PingSpoofer", "Spoofs your ping", Category.MISC);
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("ExtraPing", this, 150.0, 1.0, 6000.0, true));
    }

    @Override
    public void onUpdateNoToggle() {
        toggled = this.isToggled();
    }

    @Override
    public void onUpdate() {
        ping = (int)Explicit.instance.sm.getSettingByName(this, "ExtraPing").getValDouble();
    }
}

