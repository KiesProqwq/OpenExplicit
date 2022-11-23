/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 */
package me.explicit.module;

import me.explicit.config.ConfigManager;
import me.explicit.module.Category;
import me.explicit.utils.Game;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class Module {
    protected static Minecraft mc = Game.Minecraft();
    private String name;
    private String displayName;
    private String description;
    private int key;
    private Category category;
    private boolean toggled;

    public Module(String string, int n, Category category, String string2) {
        this.name = string;
        this.key = n;
        this.category = category;
        this.toggled = false;
        this.description = string2;
        this.setup();
    }

    public Module(String string, String string2, Category category) {
        this.name = string;
        this.key = 0;
        this.category = category;
        this.toggled = false;
        this.description = string2;
        this.setup();
    }

    public void onUpdate() {
    }

    public void onUpdateNoToggle() {
    }

    public void onTick() {
    }

    public void onMove() {
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    public void onToggle() {
    }

    public void setToggled(boolean bl) {
        this.toggled = bl;
        this.onToggle();
//        ConfigManager.SaveConfigFile("Default");
        MinecraftForge.EVENT_BUS.register((Object)this);
        if (this.toggled) {
            this.onEnable();
        } else {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            this.onDisable();
        }
    }

    public void setToggledNoSave(boolean bl) {
        this.toggled = bl;
        this.onToggle();
        MinecraftForge.EVENT_BUS.register((Object)this);
        if (this.toggled) {
            this.onEnable();
        } else {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            this.onDisable();
        }
    }

    public void toggle() {
        this.toggled = !this.toggled;
        this.onToggle();
//        ConfigManager.SaveConfigFile("Default");
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void toggleNoSave() {
        this.toggled = !this.toggled;
        this.onToggle();
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(String string) {
        this.name = string;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int n) {
//        ConfigManager.SaveConfigFile("Default");
        this.key = n;
    }

    public void setKeyNoSave(int n) {
        this.key = n;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public String getDisplayName() {
        return this.displayName == null ? this.name : this.displayName;
    }

    public void setDisplayName(String string) {
        this.displayName = string;
    }

    public void setup() {
    }

    public void onRender2D() {
    }

    public void onRender3D() {
    }
}

