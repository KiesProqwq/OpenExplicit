///*
// * Decompiled with CFR 0.152.
// * 
// * Could not load the following classes:
// *  io.netty.util.internal.ThreadLocalRandom
// *  net.minecraft.client.entity.EntityPlayerSP
// *  net.minecraft.client.settings.KeyBinding
// *  net.minecraft.init.Blocks
// *  net.minecraft.item.ItemAxe
// *  net.minecraft.item.ItemBow
// *  net.minecraft.item.ItemFood
// *  net.minecraft.item.ItemPotion
// *  net.minecraft.item.ItemSword
// *  net.minecraft.util.BlockPos
// *  net.minecraftforge.client.event.MouseEvent
// *  net.minecraftforge.common.MinecraftForge
// *  net.minecraftforge.fml.common.eventhandler.Event
// *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
// *  net.minecraftforge.fml.common.gameevent.TickEvent$RenderTickEvent
// *  org.lwjgl.input.Mouse
// */
package me.explicit.module.combat;
//
//import io.netty.util.internal.ThreadLocalRandom;
//import java.lang.reflect.Field;
//import java.nio.ByteBuffer;
//import java.util.Random;
//import me.explicit.Explicit;
//import me.explicit.module.Category;
//import me.explicit.module.Module;
//import me.explicit.settings.Setting;
//import me.explicit.utils.Game;
//import me.explicit.utils.TimerUtils;
//import net.minecraft.client.entity.EntityPlayerSP;
//import net.minecraft.client.settings.KeyBinding;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemAxe;
//import net.minecraft.item.ItemBow;
//import net.minecraft.item.ItemFood;
//import net.minecraft.item.ItemPotion;
//import net.minecraft.item.ItemSword;
//import net.minecraft.util.BlockPos;
//import net.minecraftforge.client.event.MouseEvent;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.fml.common.eventhandler.Event;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.TickEvent;
//import org.lwjgl.input.Mouse;
//
//public class AutoClicker
//extends Module {
//    public double minLeft;
//    public double maxLeft;
//    public double minBlock;
//    public double maxBlock;
//    public double jitterLeft;
//    public double jitterRight;
//    public double breakDelay;
//    public boolean sword;
//    public boolean axe;
//    public boolean blocks;
//    public boolean rightClick;
//    public boolean blockHit;
//    public boolean noShift;
//    private long time1Left;
//    private long timeLeft;
//    private long time2Left;
//    private long time3Left;
//    private double time4Left;
//    private boolean shouldLeft;
//    private long time1Right;
//    private long timeRight;
//    private long time2Right;
//    private long time3Right;
//    private double time4Right;
//    private boolean shouldRight;
//    private static Field buttonstate;
//    private static Field button;
//    private static Field buttons;
//    private Random rando;
//    private TimerUtils breaktimer = new TimerUtils();
//    private TimerUtils unbreaktimer = new TimerUtils();
//    private boolean isBreaking = false;
//    private boolean wasBreaking = false;
//
//    public AutoClicker() {
//        super("AutoClicker", 0, Category.COMBAT, "Automatically clicks for you");
//        this.rando = new Random();
//    }
//
//    @Override
//    public void setup() {
//        Explicit.instance.sm.rSetting(new Setting("MinCPS", this, 8.0, 1.0, 20.0, false));
//        Explicit.instance.sm.rSetting(new Setting("MaxCPS", this, 12.0, 1.0, 20.0, false));
//        Explicit.instance.sm.rSetting(new Setting("MinBPS", this, 3.0, 1.0, 10.0, false));
//        Explicit.instance.sm.rSetting(new Setting("MaxBPS", this, 5.0, 1.0, 10.0, false));
//        Explicit.instance.sm.rSetting(new Setting("LeftJitter", this, 0.0, 0.0, 4.0, false));
//        Explicit.instance.sm.rSetting(new Setting("RightJitter", this, 0.0, 0.0, 4.0, false));
//        Explicit.instance.sm.rSetting(new Setting("BreakDelay", this, 25.0, 0.0, 500.0, true));
//        Explicit.instance.sm.rSetting(new Setting("BreakBlocks", this, false));
//        Explicit.instance.sm.rSetting(new Setting("RightClick", this, false));
//        Explicit.instance.sm.rSetting(new Setting("BlockHit", this, true));
//        Explicit.instance.sm.rSetting(new Setting("SwordOnly", this, false));
//        Explicit.instance.sm.rSetting(new Setting("AxeOnly", this, false));
//        Explicit.instance.sm.rSetting(new Setting("NoSneak", this, false));
//    }
//
//    @Override
//    public void onEnable() {
//        super.onEnable();
//    }
//
//    @Override
//    public void onTick() {
//    }
//
//    @Override
//    public void onUpdateNoToggle() {
//        this.minLeft = Explicit.instance.sm.getSettingByName(this, "MinCPS").getValDouble();
//        this.maxLeft = Explicit.instance.sm.getSettingByName(this, "MaxCPS").getValDouble();
//        this.jitterLeft = Explicit.instance.sm.getSettingByName(this, "LeftJitter").getValDouble();
//        this.jitterRight = Explicit.instance.sm.getSettingByName(this, "RightJitter").getValDouble();
//        this.sword = Explicit.instance.sm.getSettingByName(this, "SwordOnly").getValBoolean();
//        this.axe = Explicit.instance.sm.getSettingByName(this, "AxeOnly").getValBoolean();
//        this.blocks = Explicit.instance.sm.getSettingByName(this, "BreakBlocks").getValBoolean();
//        this.rightClick = Explicit.instance.sm.getSettingByName(this, "RightClick").getValBoolean();
//        this.blockHit = Explicit.instance.sm.getSettingByName(this, "BlockHit").getValBoolean();
//        this.noShift = Explicit.instance.sm.getSettingByName(this, "NoSneak").getValBoolean();
//        this.minBlock = Explicit.instance.sm.getSettingByName(this, "MinBPS").getValDouble();
//        this.maxBlock = Explicit.instance.sm.getSettingByName(this, "MaxBPS").getValDouble();
//        this.breakDelay = (int)Explicit.instance.sm.getSettingByName(this, "BreakDelay").getValDouble();
//        Explicit.instance.sm.getSettingByName(this, "BreakDelay").setVisible(this.blocks);
//        Explicit.instance.sm.getSettingByName(this, "BlockHit").setVisible(this.rightClick);
//        Explicit.instance.sm.getSettingByName(this, "RightJitter").setVisible(this.rightClick);
//        Explicit.instance.sm.getSettingByName(this, "MinBPS").setVisible(this.blockHit && this.rightClick);
//        Explicit.instance.sm.getSettingByName(this, "MaxBPS").setVisible(this.blockHit && this.rightClick);
//    }
//
//    @SubscribeEvent
//    public void tick(TickEvent.RenderTickEvent renderTickEvent) {
//        boolean bl = false;
//        if (Game.World() == null || Game.Player() == null) {
//            return;
//        }
//        if (Game.Player().func_71045_bC() != null && (Game.Player().func_71045_bC().getItem() instanceof ItemBow || Game.Player().func_71045_bC().getItem() instanceof ItemFood || Game.Player().func_71045_bC().getItem() instanceof ItemPotion)) {
//            bl = true;
//        }
//        if (AutoClicker.mc.currentScreen != null || AutoClicker.mc.gameSettings.keyBindSneak.func_151470_d() && this.noShift) {
//            return;
//        }
//        Mouse.poll();
//        if (Mouse.isButtonDown((int)0)) {
//            this.clickLeft();
//        } else {
//            this.time1Left = 0L;
//            this.timeLeft = 0L;
//        }
//        Mouse.poll();
//        if (Mouse.isButtonDown((int)1) && this.rightClick && !bl && (this.blockHit || !Mouse.isButtonDown((int)0))) {
//            this.clickRight();
//        } else {
//            this.time1Right = 0L;
//            this.timeRight = 0L;
//        }
//        this.wasBreaking = this.isBreaking;
//    }
//
//    public void clickLeft() {
//        BlockPos blockPos;
//        if (!AutoClicker.mc.field_71415_G) {
//            return;
//        }
//        if (this.sword || this.axe) {
//            if (Game.Player().func_71045_bC() == null) {
//                return;
//            }
//            if (this.sword && !this.axe && !(Game.Player().func_71045_bC().getItem() instanceof ItemSword)) {
//                return;
//            }
//            if (!this.sword && this.axe && !(Game.Player().func_71045_bC().getItem() instanceof ItemAxe)) {
//                return;
//            }
//            if (this.sword && this.axe && !(Game.Player().func_71045_bC().getItem() instanceof ItemAxe) && !(Game.Player().func_71045_bC().getItem() instanceof ItemSword)) {
//                return;
//            }
//        }
//        this.isBreaking = false;
//        if (this.blocks && AutoClicker.mc.field_71476_x != null && (blockPos = AutoClicker.mc.field_71476_x.func_178782_a()) != null && Game.World().func_180495_p(blockPos).func_177230_c() != Blocks.field_150350_a) {
//            if (this.breaktimer.hasReached(this.breakDelay + ThreadLocalRandom.current().nextDouble(-5.0, 5.0))) {
//                int n = AutoClicker.mc.gameSettings.field_74312_F.getKeyCode();
//                KeyBinding.setKeyBindState((int)n, (boolean)true);
//                KeyBinding.func_74507_a((int)n);
//            }
//            this.isBreaking = true;
//            this.wasBreaking = true;
//            return;
//        }
//        this.breaktimer.reset();
//        if (this.jitterLeft > 0.0) {
//            EntityPlayerSP entityPlayerSP;
//            double d = this.jitterLeft * 0.5;
//            if (this.rando.nextBoolean()) {
//                entityPlayerSP = Game.Player();
//                entityPlayerSP.field_70177_z += (float)((double)this.rando.nextFloat() * d);
//            } else {
//                entityPlayerSP = Game.Player();
//                entityPlayerSP.field_70177_z -= (float)((double)this.rando.nextFloat() * d);
//            }
//            if (this.rando.nextBoolean()) {
//                entityPlayerSP = Game.Player();
//                entityPlayerSP.field_70125_A += (float)((double)this.rando.nextFloat() * (d * 0.45));
//            } else {
//                entityPlayerSP = Game.Player();
//                entityPlayerSP.field_70125_A -= (float)((double)this.rando.nextFloat() * (d * 0.45));
//            }
//        }
//        if (this.timeLeft > 0L && this.time1Left > 0L) {
//            if (System.currentTimeMillis() > this.timeLeft) {
//                int n = AutoClicker.mc.gameSettings.field_74312_F.getKeyCode();
//                KeyBinding.setKeyBindState((int)n, (boolean)true);
//                KeyBinding.func_74507_a((int)n);
//                AutoClicker.pushEvent(0, true);
//                this.getELeft();
//            } else if (System.currentTimeMillis() > this.time1Left) {
//                int n = AutoClicker.mc.gameSettings.field_74312_F.getKeyCode();
//                KeyBinding.setKeyBindState((int)n, (boolean)false);
//                AutoClicker.pushEvent(0, false);
//            }
//        } else {
//            this.getELeft();
//        }
//    }
//
//    public void clickRight() {
//        if (!AutoClicker.mc.field_71415_G) {
//            return;
//        }
//        if (this.jitterRight > 0.0) {
//            EntityPlayerSP entityPlayerSP;
//            double d = this.jitterRight * 0.5;
//            if (this.rando.nextBoolean()) {
//                entityPlayerSP = Game.Player();
//                entityPlayerSP.field_70177_z += (float)((double)this.rando.nextFloat() * d);
//            } else {
//                entityPlayerSP = Game.Player();
//                entityPlayerSP.field_70177_z -= (float)((double)this.rando.nextFloat() * d);
//            }
//            if (this.rando.nextBoolean()) {
//                entityPlayerSP = Game.Player();
//                entityPlayerSP.field_70125_A += (float)((double)this.rando.nextFloat() * (d * 0.45));
//            } else {
//                entityPlayerSP = Game.Player();
//                entityPlayerSP.field_70125_A -= (float)((double)this.rando.nextFloat() * (d * 0.45));
//            }
//        }
//        if (this.timeRight > 0L && this.time1Right > 0L) {
//            if (System.currentTimeMillis() > this.timeRight && !AutoClicker.mc.gameSettings.field_74312_F.func_151470_d()) {
//                int n = AutoClicker.mc.gameSettings.field_74313_G.getKeyCode();
//                KeyBinding.setKeyBindState((int)n, (boolean)true);
//                KeyBinding.func_74507_a((int)n);
//                AutoClicker.pushEvent(1, true);
//                this.getERight();
//            } else if (System.currentTimeMillis() > this.time1Right || AutoClicker.mc.gameSettings.field_74312_F.func_151470_d()) {
//                int n = AutoClicker.mc.gameSettings.field_74313_G.getKeyCode();
//                KeyBinding.setKeyBindState((int)n, (boolean)false);
//                AutoClicker.pushEvent(1, false);
//            }
//        } else {
//            this.getERight();
//        }
//    }
//
//    public void getELeft() {
//        double d = this.minLeft + this.rando.nextDouble() * (this.maxLeft - this.minLeft);
//        long l = (int)Math.round(1000.0 / d) - (int)(Math.round(1000.0 / d) / 1000L);
//        if (System.currentTimeMillis() > this.time2Left) {
//            if (!this.shouldLeft && this.rando.nextInt(100) >= 85) {
//                this.shouldLeft = true;
//                this.time4Left = 1.1 + this.rando.nextDouble() * 0.15;
//            } else {
//                this.shouldLeft = false;
//            }
//            this.time2Left = System.currentTimeMillis() + 400L + (long)this.rando.nextInt(1500);
//        }
//        if (this.shouldLeft) {
//            l *= (long)this.time4Left;
//        }
//        if (System.currentTimeMillis() > this.time3Left) {
//            if (this.rando.nextInt(100) >= 80) {
//                l += 50L + (long)this.rando.nextInt(100);
//            }
//            this.time3Left = System.currentTimeMillis() + 450L + (long)this.rando.nextInt(100);
//        }
//        this.timeLeft = System.currentTimeMillis() + l;
//        this.time1Left = System.currentTimeMillis() + l / 2L - (long)this.rando.nextInt(8);
//    }
//
//    public void getERight() {
//        double d = this.blockHit ? this.minBlock : this.minLeft;
//        double d2 = this.blockHit ? this.maxBlock : this.maxLeft;
//        double d3 = d + this.rando.nextDouble() * (d2 - d);
//        long l = (int)Math.round(1000.0 / d3) - (int)(Math.round(1000.0 / d3) / 1000L);
//        if (System.currentTimeMillis() > this.time2Right) {
//            if (!this.shouldRight && this.rando.nextInt(100) >= 85) {
//                this.shouldRight = true;
//                this.time4Right = 1.1 + this.rando.nextDouble() * 0.15;
//            } else {
//                this.shouldRight = false;
//            }
//            this.time2Right = System.currentTimeMillis() + 400L + (long)this.rando.nextInt(1500);
//        }
//        if (this.shouldRight) {
//            l *= (long)this.time4Right;
//        }
//        if (System.currentTimeMillis() > this.time3Right) {
//            if (this.rando.nextInt(100) >= 80) {
//                l += 50L + (long)this.rando.nextInt(100);
//            }
//            this.time3Right = System.currentTimeMillis() + 450L + (long)this.rando.nextInt(100);
//        }
//        this.timeRight = System.currentTimeMillis() + l;
//        this.time1Right = System.currentTimeMillis() + l / 2L - (long)this.rando.nextInt(8);
//    }
//
//    public static void pushEvent(int n, boolean bl) {
//        MouseEvent mouseEvent = new MouseEvent();
//        button.setAccessible(true);
//        try {
//            button.set(mouseEvent, n);
//        }
//        catch (IllegalAccessException illegalAccessException) {
//            illegalAccessException.printStackTrace();
//        }
//        button.setAccessible(false);
//        buttonstate.setAccessible(true);
//        try {
//            buttonstate.set(mouseEvent, bl);
//        }
//        catch (IllegalAccessException illegalAccessException) {
//            illegalAccessException.printStackTrace();
//        }
//        buttonstate.setAccessible(false);
//        MinecraftForge.EVENT_BUS.post((Event)mouseEvent);
//        try {
//            buttons.setAccessible(true);
//            ByteBuffer byteBuffer = (ByteBuffer)buttons.get(null);
//            buttons.setAccessible(false);
//            byteBuffer.put(n, (byte)(bl ? 1 : 0));
//        }
//        catch (IllegalAccessException illegalAccessException) {
//            illegalAccessException.printStackTrace();
//        }
//    }
//
//    static {
//        try {
//            button = MouseEvent.class.getDeclaredField("button");
//        }
//        catch (NoSuchFieldException noSuchFieldException) {
//            noSuchFieldException.printStackTrace();
//        }
//        try {
//            buttonstate = MouseEvent.class.getDeclaredField("buttonstate");
//        }
//        catch (NoSuchFieldException noSuchFieldException) {
//            noSuchFieldException.printStackTrace();
//        }
//        try {
//            buttons = Mouse.class.getDeclaredField("buttons");
//        }
//        catch (NoSuchFieldException noSuchFieldException) {
//            noSuchFieldException.printStackTrace();
//        }
//    }
//}
//
