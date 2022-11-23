/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.Tessellator
 *  org.lwjgl.opengl.GL11
 */
package me.explicit.config;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import me.explicit.command.commands.ConfigCommand;
import me.explicit.config.ConfigManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ConfigGUI
extends GuiScreen {
    private final GuiScreen previousScreen;
    private int selected;
    private int offset;

    public ConfigGUI(GuiScreen guiScreen) {
        this.previousScreen = guiScreen;
    }

    public void actionPerformed(GuiButton guiButton) throws IOException {
        if (!(mc.currentScreen instanceof ConfigGUI)) {
            return;
        }
        switch (guiButton.id) {
            case 0: {
                mc.displayGuiScreen(null);
                break;
            }
            case 1: {
                ConfigCommand configCommand = new ConfigCommand();
                if (this.selected == -1) break;
                configCommand.executeCommand(new String[]{"load", ((String)ConfigManager.GetConfigs().get(this.selected)).replace(".cfg", "")}, true);
                break;
            }
            case 2: {
                ConfigCommand configCommand = new ConfigCommand();
                if (this.selected != -1) {
                    configCommand.executeCommand(new String[]{"Delete", ((String)ConfigManager.GetConfigs().get(this.selected)).replace(".cfg", "")}, true);
                }
                this.selected = -1;
                break;
            }
            case 3: {
                ConfigCommand configCommand = new ConfigCommand();
                if (this.selected == -1) {
                    configCommand.executeCommand(new String[]{"Save", "config-" + (ConfigManager.GetConfigs().size() + 1)}, true);
                    break;
                }
                configCommand.executeCommand(new String[]{"Save", ((String)ConfigManager.GetConfigs().get(this.selected)).replace(".cfg", "")}, true);
                break;
            }
        }
    }


    public void drawScreen(int i, int j, float f) {
        if (this.mc.currentScreen instanceof ConfigGUI) {
            this.drawDefaultBackground();
            if (Mouse.hasWheel()) {
                int k = Mouse.getDWheel();

                if (k < 0) {
                    this.offset += 26;
                    if (this.offset < 0) {
                        this.offset = 0;
                    }
                } else if (k > 0) {
                    this.offset -= 26;
                    if (this.offset < 0) {
                        this.offset = 0;
                    }
                }
            }

            this.drawDefaultBackground();
            FontRenderer fontrenderer = this.mc.fontRendererObj;

            fontrenderer.drawString("Config Manager", this.width / 2 - fontrenderer.getStringWidth("Config Manager") / 2, 10, -1);
            GL11.glPushMatrix();
            this.prepareScissorBox(0.0F, 33.0F, (float) this.width, (float) (this.height - 50));
            GL11.glEnable(3089);
            int l = 38;
            List list = ConfigManager.GetConfigs();

            if (list.isEmpty()) {
                this.selected = -1;
            }

            for (int i1 = 0; i1 < list.size(); ++i1) {
                if (this.isAltInArea(l) && !((String) list.get(i1)).equalsIgnoreCase("Default.cfg")) {
                    if (i1 == this.selected) {
                        if (this.isMouseOverAlt(i, j, l - this.offset) && Mouse.isButtonDown(0)) {
                            Gui.drawRect(52, l - this.offset - 4, this.width - 52, l - this.offset + 20, (new Color(50, 50, 50)).getRGB());
                        } else if (this.isMouseOverAlt(i, j, l - this.offset)) {
                            Gui.drawRect(52, l - this.offset - 4, this.width - 52, l - this.offset + 20, (new Color(150, 150, 150)).getRGB());
                        } else {
                            Gui.drawRect(52, l - this.offset - 4, this.width - 52, l - this.offset + 20, (new Color(50, 50, 50)).getRGB());
                        }
                    } else if (this.isMouseOverAlt(i, j, l - this.offset) && Mouse.isButtonDown(0)) {
                        Gui.drawRect(52, l - this.offset - 4, this.width - 52, l - this.offset + 20, (new Color(150, 150, 150)).getRGB());
                    } else if (this.isMouseOverAlt(i, j, l - this.offset)) {
                        Gui.drawRect(52, l - this.offset - 4, this.width - 52, l - this.offset + 20, (new Color(150, 150, 150)).getRGB());
                    }

                    this.mc.fontRendererObj.drawString((String) list.get(i1), 54, l + 3, -1);
                    l += 26;
                }
            }

            GL11.glDisable(3089);
            GL11.glPopMatrix();
            super.drawScreen(i, j, f);
            if (Keyboard.isKeyDown(200)) {
                this.offset -= 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            } else if (Keyboard.isKeyDown(208)) {
                this.offset += 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }

        }
    }
    
    
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 + 4, this.height - 24, 75, 20, "Cancel"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 70, this.height - 48, 70, 20, "Load"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 70, this.height - 24, 70, 20, "Remove"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 4, this.height - 48, 75, 20, "Add/Save"));
    }
    
    private boolean isAltInArea(int i) {
        return i - this.offset <= this.height - 50;
    }

    private boolean isMouseOverAlt(int i, int j, int k) {
        return i >= 52 && j >= k - 4 && i <= this.width - 52 && j <= k + 20 && i >= 0 && j >= 33 && i <= this.width && j <= this.height - 50;
    }
    
    protected void mouseClicked(int n, int n2, int n3) throws IOException {
        if (this.mc.currentScreen instanceof ConfigGUI) {
            if (this.offset < 0) {
                this.offset = 0;
            }
        if (this.offset < 0) {
            this.offset = 0;
        }
        int n4 = 38 - this.offset;
        List list = ConfigManager.GetConfigs();
        boolean bl = false;
        int n5 = 0;
        if (n5 < list.size()) {

            if (n2 >= n4) {
                if (n5 == this.selected) {
                    this.actionPerformed((GuiButton)this.buttonList.get(1));
                    return;
                }
                this.selected = n5;
                bl = true;
            }
            n4 += 26;
            ++n5;
            return;
        }
        try {
            super.mouseClicked(n, n2, n3);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (!bl) {
            this.selected = -1;
        }
        }
    }

    public void prepareScissorBox(float f, float f1, float f2, float f3) {
        int i = (new ScaledResolution(this.mc)).getScaleFactor();

        GL11.glScissor((int) (f * (float) i), (int) (((float) (new ScaledResolution(this.mc)).getScaledHeight() - f3) * (float) i), (int) ((f2 - f) * (float) i), (int) ((f3 - f1) * (float) i));
    }
    
    public void renderBackground(int i, int j) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3008);
        this.drawDefaultBackground();
        Tessellator tessellator = Tessellator.getInstance();

        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}

