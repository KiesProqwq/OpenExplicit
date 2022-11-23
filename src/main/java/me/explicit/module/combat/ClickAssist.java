/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Mouse
 */
package me.explicit.module.combat;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class ClickAssist
extends Module {
    public double minLeft;
    public double maxLeft;
    private long lastClick;
    private long hold;
    private double speed;
    private double holdLength;
    private int averageCPS = 0;
    private ArrayList cps = new ArrayList();
    private boolean wasClick = false;

    public ClickAssist() {
        super("ClickAssist", "When clicking it will add some extra clicks to give you a higher cps", Category.COMBAT);
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("MinCPS", this, 8.0, 4.0, 20.0, false));
        Explicit.instance.sm.rSetting(new Setting("MaxCPS", this, 12.0, 5.0, 20.0, false));
    }

    @SubscribeEvent
    public void tick(TickEvent.RenderTickEvent renderTickEvent) {
        if (ClickAssist.mc.thePlayer == null) {
            return;
        }
        Mouse.poll();
        this.updateVals();
        if (this.cps.size() > Math.max(4, this.averageCPS) && ThreadLocalRandom.current().nextDouble(this.minLeft - 0.2, this.maxLeft) > (double)this.cps.size()) {
            if ((double)(System.currentTimeMillis() - this.lastClick) > this.speed * 1000.0) {
                this.lastClick = System.currentTimeMillis();
                if (this.hold < this.lastClick) {
                    this.hold = this.lastClick;
                }
                int n = ClickAssist.mc.gameSettings.keyBindAttack.getKeyCode();
                KeyBinding.setKeyBindState((int)n, (boolean)true);
                KeyBinding.onTick(n);
                this.updateVals();
            } else if ((double)(System.currentTimeMillis() - this.hold) > this.holdLength * 1000.0) {
                KeyBinding.setKeyBindState((int)ClickAssist.mc.gameSettings.keyBindAttack.getKeyCode(), (boolean)false);
                this.updateVals();
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.wasClick = false;
        this.averageCPS = 0;
    }

    private void updateVals() {
        this.minLeft = Explicit.instance.sm.getSettingByName(this, "MinCPS").getValDouble();
        this.maxLeft = Explicit.instance.sm.getSettingByName(this, "MaxCPS").getValDouble();
        int n = 0;
        if (n < this.cps.size()) {
            if (System.currentTimeMillis() - (Long)this.cps.get(n) > 1000L) {
                this.cps.remove(n);
            }
            ++n;
            return;
        }
        if (!this.wasClick && Mouse.isButtonDown((int)0)) {
            this.cps.add(System.currentTimeMillis());
            this.averageCPS = (int)((double)this.cps.size() / 1.3);
            this.wasClick = true;
        } else if (!Mouse.isButtonDown((int)0)) {
            this.wasClick = false;
        }
        if (this.minLeft >= this.maxLeft) {
            this.maxLeft = this.minLeft + 1.0;
        }
        this.speed = 1.0 / ThreadLocalRandom.current().nextDouble(this.minLeft - 0.2, this.maxLeft);
        this.holdLength = this.speed / ThreadLocalRandom.current().nextDouble(this.minLeft, this.maxLeft);
    }
}

