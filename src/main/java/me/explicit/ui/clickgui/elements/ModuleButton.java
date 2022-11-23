/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  org.lwjgl.input.Keyboard
 */
package me.explicit.ui.clickgui.elements;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import me.explicit.Explicit;
import me.explicit.module.Module;
import me.explicit.module.render.ClickGUI;
import me.explicit.settings.Setting;
import me.explicit.ui.clickgui.Panel;
import me.explicit.ui.clickgui.elements.menu.ElementCheckBox;
import me.explicit.ui.clickgui.elements.menu.ElementComboBox;
import me.explicit.ui.clickgui.elements.menu.ElementSlider;
import me.explicit.ui.clickgui.util.ColorUtil;
import me.explicit.ui.clickgui.util.FontUtil;
import me.explicit.utils.ChatUtils;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class ModuleButton {
	
    public Module mod;
    public ArrayList menuelements;
    public Panel parent;
    
    public double x;
    public double y;
    public double width;
    public double height;
    
    public boolean extended = false;
    public boolean listening = false;
    private TimerUtils timer;

    public ModuleButton(Module module, Panel panel) {
        Iterator iterator;
        this.mod = module;
        this.height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 6;
        this.parent = panel;
        this.menuelements = new ArrayList();
        this.menuelements.clear();
        if (Explicit.instance.sm.getSettingsByMod(module) != null && (iterator = Explicit.instance.sm.getSettingsByMod(module).iterator()).hasNext()) {
            Setting setting = (Setting)iterator.next();
            if (setting.isCheck()) {
                this.menuelements.add(new ElementCheckBox(this, setting));
            } else if (setting.isSlider()) {
                this.menuelements.add(new ElementSlider(this, setting));
            } else if (setting.isCombo()) {
                this.menuelements.add(new ElementComboBox(this, setting));
            }
            throw null;
        }
        this.timer = new TimerUtils();
    }

    public void drawScreen(int n, int n2, float f) {
        Color color = ColorUtil.getClickGUIColor();
        int n3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 150).getRGB();
        int n4 = ColorUtil.getClickGUIColor().getRGB();
        
        if (this.mod.isToggled() || this.mod.getName().equalsIgnoreCase("ClickGUI")) {
        	Gui.drawRect((int) this.x - 2, (int) this.y, (int) this.x + (int) this.width + 2, (int) this.y + (int) this.height, n3);
            n4 = ColorUtil.getClickGUIColor().brighter().getRGB();
            n4 = ClickGUI.mode.equalsIgnoreCase("Dark") ? new Color(240, 240, 240, 200).getRGB() : ColorUtil.getClickGUIColor().brighter().getRGB();
        }
        
        if (this.isHovered(n, n2)) {
            Gui.drawRect((int) this.x - 2, (int) this.y, (int) this.x + (int) this.width + 2, (int) this.y + (int) this.height + 1, 1427181841);
            if (this.timer.hasReached(1000.0D)) {
                Gui.drawRect(n - 1, n2, (int) ((float) (n + 1) + FontUtil.getStringWidth(this.mod.getDescription())), (int) ((double) (n2 - FontUtil.getFontHeight()) - 2.5D), ColorUtil.getClickGUIColor().darker().darker().getRGB());
                FontUtil.drawString(this.mod.getDescription(), (float) (n + 1), (float) ((double) n2 - (double) FontUtil.getFontHeight() * 1.25D), ColorUtil.getClickGUIColor().brighter().brighter().getRGB());
            }
        } else {
            this.timer.reset();
        }
        
    }

    public void onUpdate() {
        int n = ClickGUI.mode.equalsIgnoreCase("Dark") ? new Color(240, 240, 240).getRGB() : ColorUtil.getClickGUIColor().brighter().getRGB();
        FontUtil.drawTotalCenteredStringWithShadow(this.mod.getName(), (float)(this.x + (double)(FontUtil.getStringWidth(this.mod.getName()) / 2.0f)), (float)(this.y + 1.0 + this.height / 2.0), n);
        if (!this.menuelements.isEmpty()) {
            FontUtil.drawTotalCenteredStringWithShadow(this.extended ? "-" : "+", (float)this.x + 72.0f, (float)(this.y + 1.0 + this.height / 2.0), n);
        }
    }

    public boolean mouseClicked(int i, int j, int k) {
        if (!this.isHovered(i, j)) {
            return false;
        } else {
            if (k == 0) {
                this.mod.toggle();
            } else if (k == 1) {
                if (this.menuelements != null && this.menuelements.size() > 0) {
                    boolean flag = !this.extended;

                    this.extended = flag;
                }
            } else if (k == 2) {
                this.listening = true;
            }

            return true;
        }
    }

    public boolean keyTyped(char c, int n) throws IOException {
        if (this.listening) {
            if (n != 1) {
                ChatUtils.sendMessage("Bound '" + this.mod.getName() + "' to '" + Keyboard.getKeyName((int)n) + "'");
                this.mod.setKey(n);
            } else {
                ChatUtils.sendMessage("Unbound '" + this.mod.getName() + "'");
                this.mod.setKey(0);
            }
            this.listening = false;
            return true;
        }
        return false;
    }
    
    public boolean isHovered(int i, int j) {
        return (double) i >= this.x && (double) i <= this.x + this.width && (double) j >= this.y && (double) j <= this.y + this.height;
    }
}

