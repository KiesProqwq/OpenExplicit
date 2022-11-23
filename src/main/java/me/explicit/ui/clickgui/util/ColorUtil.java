/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.ui.clickgui.util;

import java.awt.Color;
import me.explicit.Explicit;
import me.explicit.module.render.ClickGUI;

public class ColorUtil {
    public static Color getClickGUIColor() {
        if (ClickGUI.mode.equalsIgnoreCase("Rainbow")) {
            return new Color(Explicit.instance.cm.cc.getColor(0).getRed(), Explicit.instance.cm.cc.getColor(0).getGreen(), Explicit.instance.cm.cc.getColor(0).getBlue(), 255);
        }
        if (ClickGUI.mode.equalsIgnoreCase("Blue")) {
            return Color.getHSBColor(0.62f, 1.0f, 1.0f);
        }
        if (ClickGUI.mode.equalsIgnoreCase("Green")) {
            return new Color(0, 255, 0);
        }
        if (ClickGUI.mode.equalsIgnoreCase("Red")) {
            return new Color(255, 0, 0);
        }
        if (ClickGUI.mode.equalsIgnoreCase("Purple")) {
            return new Color(128, 0, 128);
        }
        if (ClickGUI.mode.equalsIgnoreCase("Dark")) {
            return new Color(20, 20, 20, 255);
        }
        return new Color(1, 1, 1, 255);
    }
}

