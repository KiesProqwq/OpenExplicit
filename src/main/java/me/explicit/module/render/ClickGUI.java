/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package me.explicit.module.render;

import java.util.ArrayList;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;

public class ClickGUI
extends Module {
    public static String mode = "";

    public ClickGUI() {
        super("ClickGUI", 54, Category.RENDER, "The thing you're looking at right now");
    }

    @Override
    public void setup() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Rainbow");
        arrayList.add("Dark");
        arrayList.add("Blue");
        arrayList.add("Red");
        arrayList.add("Green");
        arrayList.add("Purple");
        Explicit.instance.sm.rSetting(new Setting("Theme", this, "Blue", arrayList));
    }

    @Override
    public void onUpdateNoToggle() {
        mode = Explicit.instance.sm.getSettingByName(this, "Theme").getValString();
    }

    @Override
    public void onEnable() {
        super.onEnable();
//        mc.displayGuiScreen((GuiScreen)Explicit.instance.clickGui);
        this.toggle();
    }
}

