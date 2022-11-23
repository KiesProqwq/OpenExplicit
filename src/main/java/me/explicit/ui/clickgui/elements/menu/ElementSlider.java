/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.util.MathHelper
 */
package me.explicit.ui.clickgui.elements.menu;

import java.awt.Color;
import me.explicit.settings.Setting;
import me.explicit.ui.clickgui.elements.Element;
import me.explicit.ui.clickgui.elements.ModuleButton;
import me.explicit.ui.clickgui.util.ColorUtil;
import me.explicit.ui.clickgui.util.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class ElementSlider
extends Element {
	
    public boolean dragging;

    public ElementSlider(ModuleButton modulebutton, Setting setting) {
        this.parent = modulebutton;
        this.set = setting;
        this.dragging = false;
        super.setup();
    }

    public void drawScreen(int i, int j, float f) {
    	String s = "" + (double)Math.round(this.set.getValDouble() * 100.0) / 100.0;
        boolean flag = this.isSliderHovered(i, j) || this.dragging;
        Color color = ColorUtil.getClickGUIColor();
        int k = (new Color(color.getRed(), color.getGreen(), color.getBlue(), flag ? 250 : 200)).getRGB();
        int j1 = (new Color(ColorUtil.getClickGUIColor().getRed(), ColorUtil.getClickGUIColor().getGreen(), ColorUtil.getClickGUIColor().getBlue(), 100)).darker().getRGB();
        double d0 = (this.set.getValDouble() - this.set.getMin()) / (this.set.getMax() - this.set.getMin());

        Gui.drawRect((int) this.x, (int) this.y, (int) this.x + (int) this.width, (int) this.y + (int) this.height, j1);
        Gui.drawRect((int) this.x, (int) this.y + 12, (int) this.x + (int) this.width, (int) this.y + 2, -15724528);
        Gui.drawRect((int) this.x, (int) this.y + 12, (int) this.x + (int) (d0 * this.width), (int) this.y + 2, k);
        FontUtil.drawString(this.setstrg, (float) (this.x + 1.0D), (float) (this.y + 3.0D), -1);
        String s1 = "";

        if (s.endsWith(".0")) {
            for (int k1 = 0; (long) k1 < s.chars().count() - 2L; ++k1) {
                s1 = String.valueOf((new StringBuilder()).append(s1).append(s.charAt(k1)));
            }
        } else {
            s1 = s;
        }

        if (this.set.percentage) {
            s1 = s1 + "%";
        }

        FontUtil.drawString(s1, (float) (this.x + this.width - (double) FontUtil.getStringWidth(s1)), (float) (this.y + 3.0D), -1);
        if (this.dragging) {
            double d1 = this.set.getMax() - this.set.getMin();
            double d2 = this.set.getMin() + MathHelper.clamp_double(((double) i - this.x) / this.width, 0.0D, 1.0D) * d1;

            this.set.setValDouble(d2);
        }

    }

    public boolean mouseClicked(int i, int j, int k) {
        if (k == 0 && this.isSliderHovered(i, j)) {
            this.dragging = true;
            return true;
        } else {
            return super.mouseClicked(i, j, k);
        }
    }

    public void mouseReleased(int i, int j, int k) {
        this.dragging = false;
    }

    public boolean isSliderHovered(int i, int j) {
        return (double) i >= this.x && (double) i <= this.x + this.width && (double) j >= this.y + 2.0D && (double) j <= this.y + 14.0D;
    }
}

