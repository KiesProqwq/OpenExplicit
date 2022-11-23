/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemSword
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
 *  org.lwjgl.input.Mouse
 */
package me.explicit.module.blatant;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Random;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.CombatUtils;
import me.explicit.utils.Game;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class TriggerBot
extends Module {
    public double minLeft;
    public double maxLeft;
    public double jitterLeft;
    public boolean sword;
    public boolean axe;
    private long time1Left;
    private long timeLeft;
    private long time2Left;
    private long time3Left;
    private double time4Left;
    private boolean shouldLeft;
    private static Field buttonstate;
    private static Field button;
    private static Field buttons;
    private Random rando = new Random();

    public TriggerBot() {
        super("TriggerBot", "Automatically hits the entity you are looking at", Category.BLATANT);
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("MinCPS", this, 8.0, 1.0, 20.0, false));
        Explicit.instance.sm.rSetting(new Setting("MaxCPS", this, 12.0, 1.0, 20.0, false));
        Explicit.instance.sm.rSetting(new Setting("Jitter", this, 0.0, 0.0, 4.0, false));
        Explicit.instance.sm.rSetting(new Setting("SwordOnly", this, false));
        Explicit.instance.sm.rSetting(new Setting("AxeOnly", this, false));
    }

    @Override
    public void onUpdateNoToggle() {
        this.minLeft = Explicit.instance.sm.getSettingByName(this, "MinCPS").getValDouble();
        this.maxLeft = Explicit.instance.sm.getSettingByName(this, "MaxCPS").getValDouble();
        this.jitterLeft = Explicit.instance.sm.getSettingByName(this, "Jitter").getValDouble();
        this.sword = Explicit.instance.sm.getSettingByName(this, "SwordOnly").getValBoolean();
        this.axe = Explicit.instance.sm.getSettingByName(this, "AxeOnly").getValBoolean();
    }

    @SubscribeEvent
    public void tick(TickEvent.RenderTickEvent renderTickEvent) {
        if (Game.World() == null || Game.Player() == null) {
            this.time1Left = 0L;
            this.timeLeft = 0L;
            return;
        }
        boolean bl = false;
        if (Game.Player().getCurrentEquippedItem() != null && (Game.Player().getCurrentEquippedItem().getItem() instanceof ItemBow || Game.Player().getCurrentEquippedItem().getItem() instanceof ItemFood || Game.Player().getCurrentEquippedItem().getItem() instanceof ItemPotion)) {
            bl = true;
        }
        if (bl || TriggerBot.mc.currentScreen == null || mc.objectMouseOver != null || mc.objectMouseOver.entityHit != null || !CombatUtils.canTarget(mc.objectMouseOver.entityHit, true)) {
            this.time1Left = 0L;
            this.timeLeft = 0L;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), Mouse.isButtonDown(0));
            return;
        }
        Mouse.poll();
        this.clickLeft();
    }

    public void clickLeft() {
        if (!TriggerBot.mc.inGameHasFocus) {
            return;
        }
        if (this.sword || this.axe) {
            if (Game.Player().getCurrentEquippedItem() == null) {
                return;
            }
            if (this.sword && !this.axe && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                return;
            }
            if (!this.sword && this.axe && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                return;
            }
            if (this.sword && this.axe && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemAxe) && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                return;
            }
        }
        if (this.jitterLeft > 0.0) {
            EntityPlayerSP entityPlayerSP;
            double d = this.jitterLeft * 0.5;
            if (this.rando.nextBoolean()) {
                entityPlayerSP = Game.Player();
                entityPlayerSP.rotationYaw += (float)((double)this.rando.nextFloat() * d);
            } else {
                entityPlayerSP = Game.Player();
                entityPlayerSP.rotationYaw -= (float)((double)this.rando.nextFloat() * d);
            }
            if (this.rando.nextBoolean()) {
                entityPlayerSP = Game.Player();
                entityPlayerSP.rotationPitch += (float)((double)this.rando.nextFloat() * (d * 0.45));
            } else {
                entityPlayerSP = Game.Player();
                entityPlayerSP.rotationPitch -= (float)((double)this.rando.nextFloat() * (d * 0.45));
            }
        }
        if (this.timeLeft > 0L && this.time1Left > 0L) {
            if (System.currentTimeMillis() > this.timeLeft) {
                int n = mc.gameSettings.keyBindAttack.getKeyCode();
                KeyBinding.setKeyBindState(n, true);
                KeyBinding.onTick(n);
                TriggerBot.pushEvent(0, true);
                this.getELeft();
            } else if (System.currentTimeMillis() > this.time1Left) {
                int n = mc.gameSettings.keyBindAttack.getKeyCode();
                KeyBinding.setKeyBindState((int)n, (boolean)false);
                TriggerBot.pushEvent(0, false);
            }
        } else {
            this.getELeft();
        }
    }

    public void getELeft() {
        double d = this.minLeft + this.rando.nextDouble() * (this.maxLeft - this.minLeft);
        long l = (int)Math.round(1000.0 / d) - (int)(Math.round(1000.0 / d) / 1000L);
        if (System.currentTimeMillis() > this.time2Left) {
            if (!this.shouldLeft && this.rando.nextInt(100) >= 85) {
                this.shouldLeft = true;
                this.time4Left = 1.1 + this.rando.nextDouble() * 0.15;
            } else {
                this.shouldLeft = false;
            }
            this.time2Left = System.currentTimeMillis() + 400L + (long)this.rando.nextInt(1500);
        }
        if (this.shouldLeft) {
            l *= (long)this.time4Left;
        }
        if (System.currentTimeMillis() > this.time3Left) {
            if (this.rando.nextInt(100) >= 80) {
                l += 50L + (long)this.rando.nextInt(100);
            }
            this.time3Left = System.currentTimeMillis() + 450L + (long)this.rando.nextInt(100);
        }
        this.timeLeft = System.currentTimeMillis() + l;
        this.time1Left = System.currentTimeMillis() + l / 2L - (long)this.rando.nextInt(8);
    }

    public static void pushEvent(int n, boolean bl) {
        MouseEvent mouseEvent = new MouseEvent();
        button.setAccessible(true);
        try {
            button.set(mouseEvent, n);
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        button.setAccessible(false);
        buttonstate.setAccessible(true);
        try {
            buttonstate.set(mouseEvent, bl);
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        buttonstate.setAccessible(false);
        MinecraftForge.EVENT_BUS.post((Event)mouseEvent);
        try {
            buttons.setAccessible(true);
            ByteBuffer byteBuffer = (ByteBuffer)buttons.get(null);
            buttons.setAccessible(false);
            byteBuffer.put(n, (byte)(bl ? 1 : 0));
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
    }

    static {
        try {
            button = MouseEvent.class.getDeclaredField("button");
        }
        catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
        try {
            buttonstate = MouseEvent.class.getDeclaredField("buttonstate");
        }
        catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
        try {
            buttons = Mouse.class.getDeclaredField("buttons");
        }
        catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
    }
}

