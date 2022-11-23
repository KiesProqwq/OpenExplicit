/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 */
package me.explicit.ui.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import me.explicit.ui.clickgui.ClickGUI;
import me.explicit.ui.clickgui.elements.Element;
import me.explicit.ui.clickgui.elements.ModuleButton;
import me.explicit.ui.clickgui.util.ColorUtil;
import me.explicit.ui.clickgui.util.FontUtil;
import net.minecraft.client.gui.Gui;

public class Panel {
    public String title;
    public double x;
    public double y;
    private double x2;
    private double y2;
    public double width;
    public double height;
    public boolean dragging;
    public boolean extended;
    public boolean visible;
    public ArrayList Elements;
    public ClickGUI clickgui;

    public Panel(String string, double d, double d2, double d3, double d4, boolean bl, ClickGUI clickGUI) {
        this.title = string;
        this.x = d;
        this.y = d2;
        this.width = d3;
        this.height = d4;
        this.extended = bl;
        this.dragging = false;
        this.visible = true;
        this.clickgui = clickGUI;
        this.Elements = new ArrayList();
        this.Elements.clear();
        this.setup();
    }

    public void setup() {
    }

    public void drawScreen(int n, int n2, float f) {
        if (!this.visible) {
            return;
        }
        if (this.dragging) {
            this.x = this.x2 + (double)n;
            this.y = this.y2 + (double)n2;
        }
        Color color = new Color(ColorUtil.getClickGUIColor().getRed(), ColorUtil.getClickGUIColor().getGreen(), ColorUtil.getClickGUIColor().getBlue(), 0).darker();
        int n3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200).getRGB();
        Gui.drawRect((int)((int)this.x), (int)((int)this.y), (int)((int)this.x + (int)this.width), (int)((int)this.y + (int)this.height), (int)-15592942);
        Gui.drawRect((int)((int)this.x), (int)((int)this.y), (int)((int)this.x + 80), (int)((int)this.y + (int)this.height), (int)n3);
        if (this.extended && !this.Elements.isEmpty()) {
            double d = this.y + this.height;
            int n4 = new Color(ColorUtil.getClickGUIColor().getRed(), ColorUtil.getClickGUIColor().getGreen(), ColorUtil.getClickGUIColor().getBlue(), 100).darker().darker().getRGB();
            Iterator iterator = this.Elements.iterator();
            if (iterator.hasNext()) {
                Object object;
                ModuleButton moduleButton = (ModuleButton)iterator.next();
                Element element = null;
                Gui.drawRect((int)((int)this.x), (int)((int)d), (int)((int)this.x + (int)this.width), (int)((int)d + (int)moduleButton.height + 1), (int)n4);
                moduleButton.x = this.x + 2.0;
                moduleButton.y = d + 1.0;
                moduleButton.width = this.width - 4.0;
                moduleButton.drawScreen(n, n2, f);
                if (moduleButton.extended) {
                    int i1 = 1;
                    Iterator iterator1 = moduleButton.menuelements.iterator();

                    while (iterator1.hasNext()) {
                        Element element1 = (Element) iterator1.next();

                        if (!element1.set.isVisible()) {
                            i1 = (int) ((double) i1 + element1.height);
                            element = element1;
                        }
                    }

                    d += moduleButton.height + (double) i1;
                } else {
                    d += moduleButton.height + 1.0D;
                }
                if (element != null) {
                    element.setEndEl(true);
                    Iterator iterator2 = moduleButton.menuelements.iterator();
                    if (iterator2.hasNext()) {
                        object = (Element)iterator2.next();
                        if (object != element) {
                            ((Element)object).setEndEl(false);
                        }
                        return;
                    }
                }
                return;
            }
            FontUtil.drawStringWithShadow(this.title, (float)(this.x + 5.0), (float)(this.y + this.height / 2.0 - (double)(FontUtil.getFontHeight() / 2)), -1052689);
            iterator = this.Elements.iterator();
            if (iterator.hasNext()) {
                ModuleButton moduleButton = (ModuleButton)iterator.next();
                moduleButton.onUpdate();
                return;
            }
        } else {
            FontUtil.drawStringWithShadow(this.title, (float)(this.x + 5.0), (float)(this.y + this.height / 2.0 - (double)(FontUtil.getFontHeight() / 2)), -1052689);
        }
    }

    public boolean mouseClicked(int i, int j, int k) {
        if (!this.visible) {
            return false;
        } else if (k == 0 && this.isHovered(i, j)) {
            this.x2 = this.x - (double) i;
            this.y2 = this.y - (double) j;
            this.dragging = true;
            return true;
        } else if (k == 1 && this.isHovered(i, j)) {
            this.extended = !this.extended;
            return true;
        } else {
            if (this.extended) {
                Iterator iterator = this.Elements.iterator();

                while (iterator.hasNext()) {
                    ModuleButton modulebutton = (ModuleButton) iterator.next();

                    if (modulebutton.mouseClicked(i, j, k)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public void mouseReleased(int n, int n2, int n3) {
        if (!this.visible) {
            return;
        }
        if (n3 == 0) {
            this.dragging = false;
        }
    }
    
    public boolean isHovered(int i, int j) {
        return (double) i >= this.x && (double) i <= this.x + this.width && (double) j >= this.y && (double) j <= this.y + this.height;
    }
}

