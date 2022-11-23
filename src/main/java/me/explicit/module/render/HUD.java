/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.module.render;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;

public class HUD
extends Module {
    public HUD() {
        super("HUD", 0, Category.RENDER, "Allows you to see UI");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Rainbow", this, true));
        Explicit.instance.sm.rSetting(new Setting("Red", this, 170.0, 0.0, 255.0, true));
        Explicit.instance.sm.rSetting(new Setting("Blue", this, 0.0, 0.0, 255.0, true));
        Explicit.instance.sm.rSetting(new Setting("Green", this, 0.0, 0.0, 255.0, true));
    }
}

