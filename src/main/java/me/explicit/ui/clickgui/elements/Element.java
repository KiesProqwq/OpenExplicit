/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 */
package me.explicit.ui.clickgui.elements;

import java.awt.Color;
import java.util.Iterator;
import me.explicit.settings.Setting;
import me.explicit.ui.clickgui.ClickGUI;
import me.explicit.ui.clickgui.elements.ModuleButton;
import me.explicit.ui.clickgui.util.FontUtil;
import net.minecraft.client.gui.Gui;

public class Element {
	
    public ClickGUI clickgui;
    public ModuleButton parent;
    public Setting set;
    
    public double offset;
    public double x;
    public double y;
    public double width;
    public double height;
    
    private boolean isEnd;
    public String setstrg;
    public boolean comboextended;

    public void setup() {
        this.clickgui = this.parent.parent.clickgui;
    }

    public void update() {
        this.x = this.parent.x - 2.0;
        this.y = this.parent.y + this.offset + this.parent.height + 1.0;
        this.width = this.parent.width + 4.0;
        this.height = 15.0;
        String string = this.set.getName();
        if (this.set.isCheck()) {
            this.setstrg = string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
            double d = this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg);
            if (d < this.x + 13.0) {
                this.width += this.x + 13.0 - d + 1.0;
            }
        } else if (this.set.isCombo()) {
            this.height = this.comboextended ? (double)(this.set.getOptions().size() * (FontUtil.getFontHeight() + 2) + 15) : 15.0;
            this.setstrg = string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
            float f = FontUtil.getStringWidth(this.setstrg);
            Iterator iterator = this.set.getOptions().iterator();
            if (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                float f2 = FontUtil.getStringWidth(string2);
                if (f2 > f) {
                    f = f2;
                }
                return;
            }
            double d = this.x + this.width - (double)f;
            if (d < this.x) {
                this.width += this.x - d + 1.0;
            }
        } else if (this.set.isSlider()) {
            this.setstrg = string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
            String string3 = "" + (double)Math.round(this.set.getValDouble() * 100.0) / 100.0;
            String string4 = "" + (double)Math.round(this.set.getMax() * 100.0) / 100.0;
            double d = this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg) - (double)FontUtil.getStringWidth(string4) - 4.0;
            if (d < this.x - 13.0) {
                this.width += this.x - d + 13.0;
            }
        }
        if (this.isEnd) {
        	Gui.drawRect((int) this.x, (int) this.y + 14, (int) this.x + (int) this.width, (int) this.y + 16, (new Color(0, 0, 0, 175)).getRGB());
        }
    }

    public void setEndEl(boolean bl) {
        this.isEnd = bl;
    }

    public void drawScreen(int n, int n2, float f) {
    }

    public boolean mouseClicked(int n, int n2, int n3) {
        return this.isHovered(n, n2);
    }

    public void mouseReleased(int n, int n2, int n3) {
    }

    public boolean isHovered(int n, int n2) {
        return (double)n >= this.x && (double)n <= this.x + this.width && (double)n2 >= this.y && (double)n2 <= this.y + this.height;
    }
}

