/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.module.movement;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;

public class Step
extends Module {
    private float oldStep;

    public Step() {
        super("Step", 0, Category.MOVEMENT, "Allows you to step up blocks");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("StepHeight", this, 3.0, 1.0, 10.0, false));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.oldStep = Game.Player().stepHeight;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Game.Player().stepHeight = this.oldStep;
    }

    @Override
    public void onTick() {
        Game.Player().stepHeight = Explicit.instance.sm.getSettingByName(this, "StepHeight").getValInt();
    }
}

