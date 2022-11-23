/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.module.misc;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;

public class SelfDestruct
extends Module {
    public SelfDestruct() {
        super("SelfDestruct", 0, Category.MISC, "BYE BYE");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Explicit.instance.onSelfDestruct();
        this.setToggled(false);
    }

    @Override
    public void onTick() {
        Explicit.instance.onSelfDestruct();
        this.setToggled(false);
    }
}

