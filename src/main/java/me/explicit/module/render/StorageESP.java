/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.tileentity.TileEntityDispenser
 *  net.minecraft.tileentity.TileEntityDropper
 *  net.minecraft.tileentity.TileEntityEnderChest
 *  net.minecraft.tileentity.TileEntityFurnace
 *  net.minecraft.tileentity.TileEntityHopper
 */
package me.explicit.module.render;

import java.awt.Color;
import java.util.Iterator;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.RenderUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;

public class StorageESP
extends Module {
    private boolean rainbow;
    private int red;
    private int green;
    private int blue;

    public StorageESP() {
        super("StorageESP", 0, Category.RENDER, "Allows you to see storages");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Rainbow", this, true));
        Explicit.instance.sm.rSetting(new Setting("LineSize", this, 2.0, 0.1, 5.0, false));
        Explicit.instance.sm.rSetting(new Setting("Red", this, 150.0, 0.0, 255.0, true));
        Explicit.instance.sm.rSetting(new Setting("Green", this, 0.0, 0.0, 255.0, true));
        Explicit.instance.sm.rSetting(new Setting("Blue", this, 0.0, 0.0, 255.0, true));
        Explicit.instance.sm.rSetting(new Setting("CheckRange", this, false));
        Explicit.instance.sm.rSetting(new Setting("Range", this, 20.0, 1.0, 512.0, true));
        Explicit.instance.sm.rSetting(new Setting("Chest", this, true));
        Explicit.instance.sm.rSetting(new Setting("EnderChest", this, true));
        Explicit.instance.sm.rSetting(new Setting("Dropper", this, false));
        Explicit.instance.sm.rSetting(new Setting("Dispenser", this, false));
        Explicit.instance.sm.rSetting(new Setting("Furnace", this, false));
        Explicit.instance.sm.rSetting(new Setting("Hopper", this, false));
    }

    @Override
    public void onUpdateNoToggle() {
        this.rainbow = Explicit.instance.sm.getSettingByName(this, "Rainbow").getValBoolean();
        Explicit.instance.sm.getSettingByName(this, "Red").setVisible(!this.rainbow);
        Explicit.instance.sm.getSettingByName(this, "Green").setVisible(!this.rainbow);
        Explicit.instance.sm.getSettingByName(this, "Blue").setVisible(!this.rainbow);
    }

    @Override
    public void onRender3D() {
        int n = (int)Explicit.instance.sm.getSettingByName(this, "Range").getValDouble();
        boolean bl = Explicit.instance.sm.getSettingByName(this, "CheckRange").getValBoolean();
        Iterator iterator = mc.theWorld.loadedTileEntityList.iterator();
        
        if (iterator.hasNext()) {
            Object e = iterator.next();
            
            if (bl) {
                if (((TileEntity)e).getPos().getX() - mc.thePlayer.getPosition().getX() > -(n + 1) && ((TileEntity) e).getPos().getX() - mc.thePlayer.getPosition().getX() < n + 1 && ((TileEntity) e).getPos().getY() - mc.thePlayer.getPosition().getY() > -(n + 1) && ((TileEntity) e).getPos().getY() - mc.thePlayer.getPosition().getY() < n + 1 && ((TileEntity) e).getPos().getZ() - mc.thePlayer.getPosition().getZ() > -(n + 1) && ((TileEntity) e).getPos().getZ() - mc.thePlayer.getPosition().getZ() < n + 1) {
                    this.checkCorrectBlock((TileEntity)e);
                }
            } else {
                this.checkCorrectBlock((TileEntity)e);
            }
            return;
        }
    }

    private void checkCorrectBlock(TileEntity tileEntity) {
        boolean bl = Explicit.instance.sm.getSettingByName(this, "Chest").getValBoolean();
        boolean bl2 = Explicit.instance.sm.getSettingByName(this, "EnderChest").getValBoolean();
        boolean bl3 = Explicit.instance.sm.getSettingByName(this, "Dropper").getValBoolean();
        boolean bl4 = Explicit.instance.sm.getSettingByName(this, "Dispenser").getValBoolean();
        boolean bl5 = Explicit.instance.sm.getSettingByName(this, "Furnace").getValBoolean();
        boolean bl6 = Explicit.instance.sm.getSettingByName(this, "Hopper").getValBoolean();
        float f = (float)Explicit.instance.sm.getSettingByName(this, "LineSize").getValDouble();
        if (tileEntity instanceof TileEntityChest && bl) {
            RenderUtils.blockESPBox(((TileEntityChest)tileEntity).getPos(), (float)this.getColor().getRed() / 255.0f, (float)this.getColor().getGreen() / 255.0f, (float)this.getColor().getBlue() / 255.0f, f);
        }
        if (tileEntity instanceof TileEntityEnderChest && bl2) {
            RenderUtils.blockESPBox(((TileEntityEnderChest)tileEntity).getPos(), (float)this.getColor().getRed() / 255.0f, (float)this.getColor().getGreen() / 255.0f, (float)this.getColor().getBlue() / 255.0f, f);
        }
        if (tileEntity instanceof TileEntityDropper && bl3) {
            RenderUtils.blockESPBox(((TileEntityDropper)tileEntity).getPos(), (float)this.getColor().getRed() / 255.0f, (float)this.getColor().getGreen() / 255.0f, (float)this.getColor().getBlue() / 255.0f, f);
        }
        if (tileEntity instanceof TileEntityDispenser && bl4) {
            RenderUtils.blockESPBox(((TileEntityDispenser)tileEntity).getPos(), (float)this.getColor().getRed() / 255.0f, (float)this.getColor().getGreen() / 255.0f, (float)this.getColor().getBlue() / 255.0f, f);
        }
        if (tileEntity instanceof TileEntityFurnace && bl5) {
            RenderUtils.blockESPBox(((TileEntityFurnace)tileEntity).getPos(), (float)this.getColor().getRed() / 255.0f, (float)this.getColor().getGreen() / 255.0f, (float)this.getColor().getBlue() / 255.0f, f);
        }
        if (tileEntity instanceof TileEntityHopper && bl6) {
            RenderUtils.blockESPBox(((TileEntityHopper)tileEntity).getPos(), (float)this.getColor().getRed() / 255.0f, (float)this.getColor().getGreen() / 255.0f, (float)this.getColor().getBlue() / 255.0f, f);
        }
    }

    private Color getColor() {
        return new Color(this.red, this.green, this.blue, 255);
    }
}

