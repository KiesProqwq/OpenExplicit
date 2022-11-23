/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.module.render;

import java.util.ArrayList;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;

public class TimeChanger
extends Module {
    public TimeChanger() {
        super("TimeChanger", 0, Category.RENDER, "Changes the time of the day");
    }

    @Override
    public void setup() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Day");
        arrayList.add("Sunset");
        arrayList.add("Night");
        Explicit.instance.sm.rSetting(new Setting("Mode", this, "Day", arrayList));
    }
}

