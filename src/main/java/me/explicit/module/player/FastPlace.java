/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemFireball
 *  net.minecraft.item.ItemSnowball
 */
package me.explicit.module.player;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.PrivateUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemSnowball;

public class FastPlace
extends Module {
    public static int placeDelay = 4;

    public FastPlace() {
        super("FastPlace", 0, Category.PLAYER, "Places blocks really fast");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Blocks", this, true));
        Explicit.instance.sm.rSetting(new Setting("Projectiles", this, false));
        Explicit.instance.sm.rSetting(new Setting("Other", this, false));
    }

    @Override
    public void onTick() {
        boolean flag = Explicit.instance.sm.getSettingByName(this, "Blocks").getValBoolean();
        boolean flag1 = Explicit.instance.sm.getSettingByName(this, "Projectiles").getValBoolean();
        boolean flag2 = Explicit.instance.sm.getSettingByName(this, "Other").getValBoolean();
        int b0 = 0;
        
        if (Game.Player().inventory.getCurrentItem() != null) {
            Item item = Game.Player().inventory.getCurrentItem().getItem();

            if ((!(item instanceof ItemBlock) || !flag) && (!(item instanceof ItemSnowball) && !(item instanceof ItemEgg) && !(item instanceof ItemFireball) || !flag1)) {
                if (flag2 && (!(item instanceof ItemBlock) || !flag) && (item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemFireball) && flag1) {
                    this.setFastPlace(b0);
                } else if ((!(item instanceof ItemBlock) || !flag) && (item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemFireball) && flag1) {
                    this.setFastPlace(4);
                }
            } else {
                this.setFastPlace(b0);
            }
        } else if (Game.Player().inventory.getCurrentItem() == null) {
            this.setFastPlace(4);
        }

    }

    @Override
    public void onDisable() {
        this.setFastPlace(4);
        placeDelay = 4;
        super.onDisable();
    }

    public void setFastPlace(int i) {
        PrivateUtils.setRightClickDelayTimer(i);
    }
}

