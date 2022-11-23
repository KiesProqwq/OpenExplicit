/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package me.explicit.config;

import me.explicit.config.ConfigGUI;
import me.explicit.module.Category;
import me.explicit.module.Module;
import net.minecraft.client.gui.GuiScreen;

public class ConfigModule
extends Module {
    public ConfigModule() {
        super("ConfigManager", "", Category.CONFIGS);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen((GuiScreen)new ConfigGUI(ConfigModule.mc.currentScreen));
        this.setToggled(false);
    }
}

