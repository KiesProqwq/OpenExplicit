/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.explicit.module.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.module.combat.AntiBot;
import me.explicit.settings.Setting;
import me.explicit.utils.CombatUtils;
import me.explicit.utils.Game;
import me.explicit.utils.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class ESP
extends Module {
    private String color;
    private int red;
    private int green;
    private int blue;

    public ESP() {
        super("EntityESP", 0, Category.RENDER, "Shows entities through walls");
    }

    @Override
    public void setup() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Box");
        arrayList.add("2D");
        Explicit.instance.sm.rSetting(new Setting("Mode", this, "2D", arrayList));
        
        ArrayList<String> arrayList2 = new ArrayList<String>();
        arrayList2.add("Rainbow");
        arrayList2.add("Select");
        Explicit.instance.sm.rSetting(new Setting("Color", this, "Rainbow", arrayList2));
        
        Explicit.instance.sm.rSetting(new Setting("Players", this, true));
        Explicit.instance.sm.rSetting(new Setting("Invisibles", this, false));
        Explicit.instance.sm.rSetting(new Setting("Teams", this, false));
        Explicit.instance.sm.rSetting(new Setting("Animals", this, false));
        Explicit.instance.sm.rSetting(new Setting("Mobs", this, false));
        Explicit.instance.sm.rSetting(new Setting("Red", this, 150.0, 0.0, 255.0, true));
        Explicit.instance.sm.rSetting(new Setting("Green", this, 0.0, 0.0, 255.0, true));
        Explicit.instance.sm.rSetting(new Setting("Blue", this, 0.0, 0.0, 255.0, true));
    }

    @Override
    public void onRender3D() {
        this.color = Explicit.instance.sm.getSettingByName(this, "Color").getValString();
        this.red = (int)Explicit.instance.sm.getSettingByName(this, "Red").getValDouble();
        this.green = (int)Explicit.instance.sm.getSettingByName(this, "Green").getValDouble();
        this.blue = (int)Explicit.instance.sm.getSettingByName(this, "Blue").getValDouble();
        if (this.color.equalsIgnoreCase("Rainbow")) {
            Explicit.instance.sm.getSettingByName(this, "Red").setVisible(false);
            Explicit.instance.sm.getSettingByName(this, "Blue").setVisible(false);
            Explicit.instance.sm.getSettingByName(this, "Green").setVisible(false);
        } else {
            Explicit.instance.sm.getSettingByName(this, "Red").setVisible(true);
            Explicit.instance.sm.getSettingByName(this, "Green").setVisible(true);
            Explicit.instance.sm.getSettingByName(this, "Blue").setVisible(true);
        }
        Iterator iterator = Game.World().loadedEntityList.iterator();
        if (iterator.hasNext()) {
            Entity entity = (Entity)iterator.next();
            if (!AntiBot.getBots().contains(entity)) {
                boolean bl = true;
                boolean bl2 = Explicit.instance.sm.getSettingByName(this, "Teams").getValBoolean();
                boolean bl3 = Explicit.instance.sm.getSettingByName(this, "Players").getValBoolean();
                boolean bl4 = Explicit.instance.sm.getSettingByName(this, "Invisibles").getValBoolean();
                if (!CombatUtils.isTeam((EntityPlayer)Game.Player(), (Entity)entity) && bl2 && bl3 && entity instanceof EntityPlayer) {
                    bl = false;
                } else if (entity.isInvisible() && !bl4 && bl3 && entity instanceof EntityPlayer) {
                    bl = false;
                } else if (!bl3 || !(entity instanceof EntityPlayer)) {
                    bl = false;
                }
                if (entity != Game.Player() && !AntiBot.getBots().contains(entity) && (bl || entity instanceof EntityMob && Explicit.instance.sm.getSettingByName(this, "Mobs").getValBoolean() || entity instanceof EntityAnimal && Explicit.instance.sm.getSettingByName(this, "Animals").getValBoolean())) {
                    if (Explicit.instance.sm.getSettingByName(this, "Mode").getValString().equalsIgnoreCase("Box")) {
                        RenderUtils.renderEntity(entity, this.getColor().getRGB(), 2);
                    } else if (Explicit.instance.sm.getSettingByName(this, "Mode").getValString().equalsIgnoreCase("2D")) {
                        RenderUtils.renderEntity(entity, this.getColor().getRGB(), 3);
                    }
                }
            }
            return;
        }
    }

    private Color getColor() {
        if (this.color.equalsIgnoreCase("Rainbow")) {
            return Explicit.instance.cm.cc.getColor(0);
        }
        return new Color(this.red, this.green, this.blue, 255);
    }
}

