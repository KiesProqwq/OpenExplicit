/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.module.player;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.PrivateUtils;

public class FastBreak
extends Module {
    public FastBreak() {
        super("FastBreak", "Breaks blocks faster", Category.PLAYER);
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Damage", this, 0.2, 0.0, 1.0, false));
    }

    @Override
    public void onTick() {
    	if (mc.currentScreen == null && mc.thePlayer != null && mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null && mc.gameSettings.keyBindAttack.isKeyDown()) {
            PrivateUtils.setBlockHitDelay(0);
            float f = PrivateUtils.getBlockDamage();
            if (f == -1.0f) {
                return;
            }
            if ((double)f > Explicit.instance.sm.getSettingByName(this, "Damage").getValDouble()) {
                PrivateUtils.setBlockDamage(1.0f);
            }
        }
    }
}

