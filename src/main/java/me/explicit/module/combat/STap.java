/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Keyboard
 */
package me.explicit.module.combat;

import org.lwjgl.input.Keyboard;

import io.netty.util.internal.ThreadLocalRandom;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.CombatUtils;
import me.explicit.utils.Game;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class STap
extends Module {
    boolean shouldTap = false;
    private TimerUtils tDelay = new TimerUtils();
    private TimerUtils tHold = new TimerUtils();
    private TimerUtils updateData = new TimerUtils();
    private double delay;
    private double hold;
    boolean hasReached = false;

    public STap() {
        super("STap", 0, Category.MOVEMENT, "Automatically taps S.");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("MinDelay", this, 100.0, 1.0, 500.0, true));
        Explicit.instance.sm.rSetting(new Setting("MaxDelay", this, 150.0, 1.0, 500.0, true));
        Explicit.instance.sm.rSetting(new Setting("MinHold", this, 10.0, 1.0, 250.0, true));
        Explicit.instance.sm.rSetting(new Setting("MaxHold", this, 11.0, 1.0, 250.0, true));
    }

    @Override
    public void onEnable() {
        this.shouldTap = false;
        this.hasReached = false;
        super.onEnable();
    }

    public void setData() {
        double d = Explicit.instance.sm.getSettingByName(this, "MinDelay").getValDouble();
        double d2 = Explicit.instance.sm.getSettingByName(this, "MaxDelay").getValDouble();
        double d3 = Explicit.instance.sm.getSettingByName(this, "MinHold").getValDouble();
        double d4 = Explicit.instance.sm.getSettingByName(this, "MaxHold").getValDouble();
        if (d == d2 || d > d2) {
            d2 = d * 1.1;
        }
        if (d3 == d4 || d3 > d4) {
            d4 = d3 * 1.1;
        }
        this.delay = ThreadLocalRandom.current().nextDouble(Math.min(d, d2), Math.max(d, d2));
        this.hold = ThreadLocalRandom.current().nextDouble(Math.min(d3, d4), Math.max(d3, d4));
    }

    @SubscribeEvent
    public void t34ff(TickEvent.RenderTickEvent renderTickEvent) {
        double d;
        double d2 = Explicit.instance.sm.getSettingByName(this, "MinDelay").getValDouble();
        if (this.updateData.hasReached(Math.max(d2, d = Explicit.instance.sm.getSettingByName(this, "MaxDelay").getValDouble()) * ((ThreadLocalRandom.current().nextDouble() + ThreadLocalRandom.current().nextDouble()) * 2.0))) {
            this.setData();
            this.hasReached = true;
            this.updateData.reset();
        }
        if (!this.hasReached) {
            return;
        }
        if (this.tHold.hasReached(this.hold) && this.shouldTap && Keyboard.isKeyDown((int)STap.mc.gameSettings.keyBindBack.getKeyCode())) {
            KeyBinding.setKeyBindState((int)STap.mc.gameSettings.keyBindBack.getKeyCode(), (boolean)true);
            this.shouldTap = false;
        } else if (!Keyboard.isKeyDown((int)STap.mc.gameSettings.keyBindBack.getKeyCode())) {
            KeyBinding.setKeyBindState((int)STap.mc.gameSettings.keyBindBack.getKeyCode(), (boolean)false);
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void asdfgbnv(AttackEntityEvent attackentityevent) {
        if (this.tDelay.hasReached(this.delay) && !this.shouldTap && Module.mc.objectMouseOver != null && Module.mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY && Module.mc.objectMouseOver.entityHit.getEntityId() == attackentityevent.target.getEntityId() && CombatUtils.canTarget(Game.World().getEntityByID(attackentityevent.target.getEntityId()), true)) {
            this.shouldTap = true;
            this.tHold.reset();
            this.tDelay.reset();
            KeyBinding.setKeyBindState(STap.mc.gameSettings.keyBindBack.getKeyCode(), false);
        }
    }
}

