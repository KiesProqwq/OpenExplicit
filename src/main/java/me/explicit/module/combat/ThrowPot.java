package me.explicit.module.combat;

import io.netty.util.internal.ThreadLocalRandom;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class ThrowPot
extends Module {
    private int stage = 0;
    private TimerUtils timer = new TimerUtils();
    private int oldSlot;
    private int potSlot;

    public ThrowPot() {
        super("ThrowPot", "Automatically throws as many pots as needed when you enable it", Category.COMBAT);
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("MinDelay", this, 100.0, 10.0, 1000.0, true));
        Explicit.instance.sm.rSetting(new Setting("MaxDelay", this, 150.0, 10.0, 1000.0, true));
        Explicit.instance.sm.rSetting(new Setting("PotHealth", this, 8.0, 1.0, 20.0, true));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.stage = 0;
        this.timer.reset();
        if (Game.Player() == null) {
            this.setToggled(false);
            return;
        }
        this.oldSlot = mc.thePlayer.inventory.currentItem;
        this.potSlot = this.getPotionSlot();
        if (this.potSlot == -1) {
            this.setToggled(false);
            return;
        }
    }

    @Override
    public void onTick() {
        if (this.stage == 0) {
            this.stage = 1;
        } else if (this.stage == 1) {
            float f = (float)Explicit.instance.sm.getSettingByName(this, "PotHealth").getValDouble();
            if (ThrowPot.mc.thePlayer.getHealth() <= mc.thePlayer.getMaxHealth() - f) {
                ThrowPot.mc.thePlayer.inventory.currentItem = this.potSlot;
                this.stage = 2;
                this.timer.reset();
            } else {
                ThrowPot.mc.thePlayer.inventory.currentItem = this.oldSlot;
                this.setToggled(false);
            }
        } else if (this.stage == 2) {
            if (this.timer.hasReached(this.getDelay() / 3.0)) {
                KeyBinding.setKeyBindState((int)ThrowPot.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
                this.timer.reset();
                this.stage = 3;
            }
        } else if (this.stage == 3) {
            if (this.timer.hasReached(this.getDelay() / 3.0)) {
                KeyBinding.setKeyBindState((int)ThrowPot.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
                this.stage = 4;
            }
        } else if (this.stage == 4) {
            ThrowPot.mc.thePlayer.inventory.currentItem = this.oldSlot;
            this.setToggled(false);
            this.stage = 0;
        }
    }

    public int getPotionSlot() {
        for (int i = 0; i < 8; ++i) {
            ItemStack itemstack = ThrowPot.mc.thePlayer.inventory.getStackInSlot(i);

            if (itemstack != null && !itemstack.isStackable()) {
                Item item = itemstack.getItem();

                if (item instanceof ItemPotion) {
                	
                    if (ItemPotion.isSplash(itemstack.getMetadata())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public double getDelay() {
        int n;
        int n2 = Explicit.instance.sm.getSettingByName(this, "MinDelay").getValInt() * 4;
        if (n2 == (n = Explicit.instance.sm.getSettingByName(this, "MaxDelay").getValInt() * 4)) {
            n = n2 + 1;
        }
        return ThreadLocalRandom.current().nextDouble((double)Math.min(n, n2), (double)Math.max(n, n2));
    }
}

