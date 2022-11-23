/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 */
package me.explicit.ui.hud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.SettingsManager;
import me.explicit.ui.clickgui.ClickGUI;
import me.explicit.utils.Game;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class HUDRenderer
extends Gui {
    protected Minecraft mc = Game.Minecraft();

    public void draw() {
        if (!Explicit.instance.mm.getModuleByName("HUD").isToggled()) {
            return;
        }
        this.renderModules();
    }

    public void renderModules() {
        if (this.mc.currentScreen instanceof ClickGUI || Game.World() == null || Game.Player() == null) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        List list = this.getOrder();
        int n = 1;
        int n2 = 0;
        if (n2 < this.getOrder().size()) {
            String string = (String)this.getOrder().get(n2);
            if (!string.equalsIgnoreCase("HUD") && !((Module)this.getOrderModule().get(n2)).getCategory().equals((Object)Category.VALUES)) {
                int n3 = 10 * n - mc.fontRendererObj.FONT_HEIGHT + 2;
                this.renderText(string, scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(string) - 2, n3, 0);
                ++n;
            }
//            ++n2;
            return;
        }
        String string = "Explicit " + "B9".toLowerCase();
        this.renderText("Explicit " + "B9".toLowerCase(), 5, 5, 0);
    }

    private void renderText(String string, int n, int n2, int n3) {
        String string2 = "";
        int n4 = 0;
        if (n4 < string.toCharArray().length) {
            char c = string.toCharArray()[n4];
            float f = n + mc.fontRendererObj.getStringWidth(string2);
            mc.fontRendererObj.drawStringWithShadow(c + "", f, (float)n2, this.getColor((int)(-f) + n2 + n3).getRGB());
            string2 = string2 + c;
            ++n4;
            return;
        }
    }

    public Color getColor(int n) {
        SettingsManager settingsManager = Explicit.instance.sm;
        Module module = Explicit.instance.mm.getModuleByName("HUD");
        if (settingsManager.getSettingByName(module, "Rainbow").getValBoolean()) {
            return Explicit.instance.cm.cc.getColor(n);
        }
        int n2 = (int)settingsManager.getSettingByName(module, "Red").getValDouble();
        int n3 = (int)settingsManager.getSettingByName(module, "Green").getValDouble();
        int n4 = (int)settingsManager.getSettingByName(module, "Blue").getValDouble();
        return new Color(n2, n3, n4, 255);
    }

    private List getOrder() {
        List list = Explicit.instance.mm.getEnabledModules();
        ArrayList arraylist = null;
        Comparator comparator = new Comparator() {
            final HUDRenderer this$0 = HUDRenderer.this;

            public int compare(String s, String s1) {
                return Float.compare((float) this.this$0.mc.fontRendererObj.getStringWidth(s1), (float) this.this$0.mc.fontRendererObj.getStringWidth(s));
            }

            public int compare(Object object, Object object1) {
                return this.compare((String) object, (String) object1);
            }
        };

        if (arraylist == null) {
            (arraylist = new ArrayList()).clear();
        }

        for (int i = 0; i < list.size(); ++i) {
            arraylist.add(((Module) list.get(i)).getDisplayName());
        }

        Collections.sort(arraylist, comparator);
        return arraylist;
    }

    private List getOrderModule() {
        ArrayList<Module> arrayList = new ArrayList<Module>();
        arrayList.clear();
        Iterator iterator = this.getOrder().iterator();
        if (iterator.hasNext()) {
            String string = (String)iterator.next();
            if (Explicit.instance.mm.getModuleByName(string) != null) {
                arrayList.add(Explicit.instance.mm.getModuleByName(string));
            }
            return null;
        }
        return arrayList;
    }
}

