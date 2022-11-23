/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Ordering
 *  net.minecraft.client.gui.GuiPlayerTabOverlay
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.explicit.module.combat;

import com.google.common.collect.Ordering;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class AntiBot
extends Module {
    private TimerUtils notificationTimer;
    private TimerUtils resetTimer = new TimerUtils();
    private int botsFound;
    private static List bots = new ArrayList();
    String mode;

    public AntiBot() {
        super("AntiBot", 0, Category.COMBAT, "Prevents you from attacking bots");
        this.notificationTimer = new TimerUtils();
    }

    @Override
    public void setup() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Hypixel");
        arrayList.add("Mineplex");
        Explicit.instance.sm.rSetting(new Setting("Mode", this, "Hypixel", arrayList));
    }

    @Override
    public void onUpdate() {
        Iterator iterator;
        if (this.resetTimer.hasReached(20000.0)) {
            bots.clear();
            this.resetTimer.reset();
        }
        this.mode = Explicit.instance.sm.getSettingByName(this, "Mode").getValString();
        if (this.botsFound > 0 && this.notificationTimer.hasReached(1000.0)) {
            this.notificationTimer.reset();
            this.botsFound = 0;
        }
        if (AntiBot.mc.thePlayer.ticksExisted <= 0) {
            this.notificationTimer.reset();
            this.botsFound = 0;
            bots.clear();
        }
        if (this.mode.equalsIgnoreCase("Mineplex")) {
            for (Object e : mc.theWorld.playerEntities) {
                if (e instanceof EntityPlayer) {
                    EntityPlayer entityPlayer = (EntityPlayer)e;
                    if (entityPlayer.ticksExisted >= 2 || entityPlayer.getHealth() >= 20.0f || entityPlayer.getHealth() <= 0.0f) continue;
                    if (entityPlayer == Module.mc.thePlayer) {
                        return;
                    }
                    this.add(entityPlayer, false);
                    ++this.botsFound;
                }
                return;
            }
        } else if (this.mode.equalsIgnoreCase("Hypixel") && (iterator = AntiBot.mc.theWorld.getLoadedEntityList().iterator()).hasNext()) {
            Object e = iterator.next();
            if (e instanceof EntityPlayer && e != AntiBot.mc.thePlayer && !bots.contains(e)) {
                EntityPlayer entityPlayer = (EntityPlayer)e;
                String string = entityPlayer.getDisplayName().getFormattedText();
                String string2 = entityPlayer.getCustomNameTag();
                String string3 = entityPlayer.getName();
                boolean bl = false;
                if (this.getTabPlayerList() != null && !this.getTabPlayerList().contains(entityPlayer)) {
                    this.add(entityPlayer, false);
                    bl = true;
                }
                if (string.contains("[NPC]") && entityPlayer != AntiBot.mc.thePlayer) {
                    this.add(entityPlayer, false);
                    bl = true;
                }
                if (string2.equalsIgnoreCase(string3) && entityPlayer.getMaxHealth() == 20.0f && (this.getTabPlayerList() == null || !this.getTabPlayerList().contains(entityPlayer)) && string.contains("\u00ef\u00bf\u00bdc") && string.contains("\u00ef\u00bf\u00bdr")) {
                    this.add(entityPlayer, true);
                    bl = true;
                }
            }
            return;
        }
    }

    public void add(EntityPlayer entityPlayer, boolean bl) {
        if (!bots.contains(entityPlayer)) {
            ++this.botsFound;
            bots.add(entityPlayer);
        }
    }

    public List getTabPlayerList() {
        NetHandlerPlayClient netHandlerPlayClient = mc.thePlayer.sendQueue;
        ArrayList<EntityPlayer> arrayList = new ArrayList<EntityPlayer>();
        arrayList.clear();
        Ordering ordering = this.field_175252_a();
        if (ordering == null) {
            return null;
        }
        List list = ordering.sortedCopy((Iterable)netHandlerPlayClient.getPlayerInfoMap());
        Iterator iterator = list.iterator();
        if (iterator.hasNext()) {
            Object e = iterator.next();
            NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)e;
            if (networkPlayerInfo == null) {
                return null;
            }
            arrayList.add(AntiBot.mc.theWorld.getPlayerEntityByName(networkPlayerInfo.getGameProfile().getName()));
            return null;
        }
        return arrayList;
    }

    public Ordering field_175252_a() {
        try {
            Class<GuiPlayerTabOverlay> clazz = GuiPlayerTabOverlay.class;
            Field field = clazz.getDeclaredField("field_175252_a");
            field.setAccessible(true);
            return (Ordering)field.get(GuiPlayerTabOverlay.class);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        bots.clear();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        bots.clear();
    }

    public static List getBots() {
        return bots;
    }
}

