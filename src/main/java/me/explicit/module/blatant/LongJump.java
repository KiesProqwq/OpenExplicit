/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.module.blatant;

import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.Game;
import me.explicit.utils.MoveUtils;

public class LongJump
extends Module {
    public long enabled;

    public LongJump() {
        super("LongJump", 0, Category.BLATANT, "Automatically jump very far. Best used with a hotkey");
    }

    public void onMove() {
        if (Game.Player().onGround && System.currentTimeMillis() - this.enabled > 500L) {
            this.setToggled(false);
        } else {
            if (!Game.Player().onGround) {
                MoveUtils.setMoveSpeed(2.0D);
            }

        }
    }

    public void onEnable() {
        super.onEnable();
        if (Game.Player().onGround) {
            mc.thePlayer.jump();
        } else {
            this.setToggled(false);
        }
        this.enabled = System.currentTimeMillis();
    }
}

