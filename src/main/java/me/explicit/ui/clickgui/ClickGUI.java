/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package me.explicit.ui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import me.explicit.Explicit;
import me.explicit.config.ConfigManager;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.SettingsManager;
import me.explicit.ui.clickgui.Panel;
import me.explicit.ui.clickgui.elements.Element;
import me.explicit.ui.clickgui.elements.ModuleButton;
import me.explicit.ui.clickgui.elements.menu.ElementSlider;
import me.explicit.ui.clickgui.util.ColorUtil;
import me.explicit.ui.clickgui.util.FontUtil;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ClickGUI
extends GuiScreen {
    public static ArrayList panels;
    public static ArrayList rpanels;
    private ModuleButton mb = null;
    public SettingsManager setmgr;
    boolean wasFastRenderOn;
    public static boolean enabled;
    private TimerUtils timer = new TimerUtils();

    public ClickGUI() {
    	this.setmgr = Explicit.instance.sm;
        FontUtil.setupFontUtils();
        (ClickGUI.panels = new ArrayList()).clear();
        double d0 = 80.0D;
        double d1 = 20.0D;
        double d2 = 10.0D;
        double d3 = 10.0D;
        double d4 = d1;
        Category[] acategory = Category.values();
        int i = acategory.length;

        for (int j = 0; j < i; ++j) {
            final Category category = acategory[j];
            final String s = String.valueOf((new StringBuilder()).append(Character.toUpperCase(category.name().toLowerCase().charAt(0))).append(category.name().toLowerCase().substring(1)));

            ClickGUI.panels.add(new Panel(s, d2, d0, d1, i, false, this) {
                final Category val$c = category;
                final ClickGUI this$0 = ClickGUI.this;

                public void setup() {
                    Iterator iterator = Explicit.instance.mm.modules.iterator();

                    while (iterator.hasNext()) {
                        Module module = (Module) iterator.next();

                        if (module.getCategory().equals(this.val$c)) {
                            this.Elements.add(new ModuleButton(module, this));
                        }
                    }

                }
            });
            d3 += d4 + 2.0D;
        }

        ClickGUI.rpanels = new ArrayList();
        Iterator iterator = ClickGUI.panels.iterator();

        while (iterator.hasNext()) {
            Panel panel = (Panel) iterator.next();

            ClickGUI.rpanels.add(panel);
        }

        Collections.reverse(ClickGUI.rpanels);
    }

    public void drawScreen(int i, int j, float f) {
        Iterator iterator = ClickGUI.panels.iterator();

        while (iterator.hasNext()) {
            Panel panel = (Panel) iterator.next();

            panel.drawScreen(i, j, f);
        }

        ScaledResolution scaledresolution = new ScaledResolution(this.mc);

        GL11.glPushMatrix();
        GL11.glTranslated((double) scaledresolution.getScaledWidth(), (double) scaledresolution.getScaledHeight(), 0.0D);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glPopMatrix();
        this.mb = null;
        Iterator iterator1 = ClickGUI.panels.iterator();

        Panel panel1;
        Iterator iterator2;
        ModuleButton modulebutton;

        label109:
        while (iterator1.hasNext()) {
            panel1 = (Panel) iterator1.next();
            if (panel1 != null && panel1.visible && panel1.extended && panel1.Elements != null && panel1.Elements.size() > 0) {
                iterator2 = panel1.Elements.iterator();

                while (iterator2.hasNext()) {
                    modulebutton = (ModuleButton) iterator2.next();
                    if (modulebutton.listening) {
                        this.mb = modulebutton;
                        break label109;
                    }
                }
            }
        }

        iterator1 = ClickGUI.panels.iterator();

        while (iterator1.hasNext()) {
            panel1 = (Panel) iterator1.next();
            if (panel1.extended && panel1.visible && panel1.Elements != null) {
                iterator2 = panel1.Elements.iterator();

                while (iterator2.hasNext()) {
                    modulebutton = (ModuleButton) iterator2.next();
                    if (modulebutton.extended && modulebutton.menuelements != null && !modulebutton.menuelements.isEmpty()) {
                        double d0 = -1.0D;
                        Color color = ColorUtil.getClickGUIColor().darker();
                        int k = (new Color(color.getRed(), color.getGreen(), color.getBlue(), 255)).getRGB();
                        Iterator iterator3 = modulebutton.menuelements.iterator();

                        while (iterator3.hasNext()) {
                            Element element = (Element) iterator3.next();

                            if (!element.set.isVisible()) {
                                element.offset = d0;
                                element.update();
                                element.drawScreen(i, j, f);
                                d0 += element.height;
                            }
                        }
                    }
                }
            }
        }

        if (this.mb != null) {
            drawRect(0, 0, this.width, this.height, -2012213232);
            GL11.glPushMatrix();
            GL11.glTranslatef((float) (scaledresolution.getScaledWidth() / 2), (float) (scaledresolution.getScaledHeight() / 2), 0.0F);
            FontUtil.drawBigTotalCenteredStringWithShadow("Listening...", 0.0F, -20.0F, -1);
            FontUtil.drawBigTotalCenteredStringWithShadow("Press 'ESCAPE' to unbind " + this.mb.mod.getName() + (this.mb.mod.getKey() > -1 ? " (" + Keyboard.getKeyName((int)this.mb.mod.getKey()) + ")" : ""), 0.0f, 0.0f, -1);
            GL11.glPopMatrix();
        }

        String s = "Explicit " + "B9".toLowerCase();
        char[] achar = s.toCharArray();
        double d1 = 5.0D;
        ScaledResolution scaledresolution1 = new ScaledResolution(this.mc);

        for (int l = 0; l < achar.length; ++l) {
            this.mc.fontRendererObj.drawString(String.valueOf((new StringBuilder()).append(achar[l]).append("")), scaledresolution1.getScaledWidth() - 10 - this.mc.fontRendererObj.getStringWidth(s) + (int) d1, 5, Explicit.instance.uiRenderer.getColor(l).getRGB());
            d1 += (double) this.mc.fontRendererObj.getStringWidth(String.valueOf((new StringBuilder()).append(achar[l]).append("")));
        }

        super.drawScreen(i, j, f);
    }

    public void mouseClicked(int n, int n2, int n3) {
        ConfigManager.saveGUISettings();
        if (this.mb != null) {
            return;
        }
        Iterator iterator = rpanels.iterator();
        if (iterator.hasNext()) {
            Iterator iterator2;
            Panel panel = (Panel)iterator.next();
            if (panel.extended && panel.visible && panel.Elements != null && (iterator2 = panel.Elements.iterator()).hasNext()) {
                Iterator iterator3;
                ModuleButton moduleButton = (ModuleButton)iterator2.next();
                if (moduleButton.extended && (iterator3 = moduleButton.menuelements.iterator()).hasNext()) {
                    Element element = (Element)iterator3.next();
                    if (!element.set.isVisible() && element.mouseClicked(n, n2, n3)) {
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        iterator = rpanels.iterator();
        if (iterator.hasNext()) {
            Panel panel = (Panel)iterator.next();
            if (panel.mouseClicked(n, n2, n3)) {
                return;
            }
            return;
        }
        try {
            super.mouseClicked(n, n2, n3);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void mouseReleased(int n, int n2, int n3) {
        if (this.mb != null) {
            return;
        }
        Iterator iterator = rpanels.iterator();
        if (iterator.hasNext()) {
            Iterator iterator2;
            Panel panel = (Panel)iterator.next();
            if (panel.extended && panel.visible && panel.Elements != null && (iterator2 = panel.Elements.iterator()).hasNext()) {
                Iterator iterator3;
                ModuleButton moduleButton = (ModuleButton)iterator2.next();
                if (moduleButton.extended && (iterator3 = moduleButton.menuelements.iterator()).hasNext()) {
                    Element element = (Element)iterator3.next();
                    element.mouseReleased(n, n2, n3);
                    return;
                }
                return;
            }
            return;
        }
        iterator = rpanels.iterator();
        if (iterator.hasNext()) {
            Panel panel = (Panel)iterator.next();
            panel.mouseReleased(n, n2, n3);
            return;
        }
        super.mouseReleased(n, n2, n3);
    }

    protected void keyTyped(char c, int n) {
        Iterator iterator = rpanels.iterator();
        if (iterator.hasNext()) {
            Iterator iterator2;
            Panel panel = (Panel)iterator.next();
            if (panel != null && panel.visible && panel.extended && panel.Elements != null && panel.Elements.size() > 0 && (iterator2 = panel.Elements.iterator()).hasNext()) {
                ModuleButton moduleButton = (ModuleButton)iterator2.next();
                try {
                    if (moduleButton.keyTyped(c, n)) {
                        return;
                    }
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
                return;
            }
            return;
        }
        try {
            super.keyTyped(c, n);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void initGui() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        this.buttonList.add(new GuiButton(1, 5, scaledResolution.getScaledHeight() - 25, 100, 20, "Reset Buttons"));
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            this.closeAllSettings();
            double d = 22.0;
            double d2 = 10.0;
            double d3 = 10.0;
            double d4 = d;
            Iterator iterator = panels.iterator();
            if (iterator.hasNext()) {
                Panel panel = (Panel)iterator.next();
                panel.dragging = false;
                panel.extended = false;
                panel.x = d2;
                panel.y = d3;
                d3 += d4;
                return;
            }
        }
    }

    public void onGuiClosed() {
        Iterator iterator = rpanels.iterator();
        if (iterator.hasNext()) {
            Iterator iterator2;
            Panel panel = (Panel)iterator.next();
            if (panel.extended && panel.visible && panel.Elements != null && (iterator2 = panel.Elements.iterator()).hasNext()) {
                Iterator iterator3;
                ModuleButton moduleButton = (ModuleButton)iterator2.next();
                if (moduleButton.extended && (iterator3 = moduleButton.menuelements.iterator()).hasNext()) {
                    Element element = (Element)iterator3.next();
                    if (element instanceof ElementSlider) {
                        ((ElementSlider)element).dragging = false;
                    }
                    return;
                }
                return;
            }
            return;
        }
    }

    public void closeAllSettings() {
        Iterator iterator = rpanels.iterator();
        if (iterator.hasNext()) {
            Iterator iterator2;
            Panel panel = (Panel)iterator.next();
            if (panel != null && panel.visible && panel.extended && panel.Elements != null && panel.Elements.size() > 0 && (iterator2 = panel.Elements.iterator()).hasNext()) {
                ModuleButton moduleButton = (ModuleButton)iterator2.next();
                moduleButton.extended = false;
                return;
            }
            return;
        }
    }

    static {
        enabled = false;
    }
}

