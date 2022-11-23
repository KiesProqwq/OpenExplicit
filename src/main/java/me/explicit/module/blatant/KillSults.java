/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.explicit.module.blatant;

import io.netty.util.internal.ThreadLocalRandom;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.explicit.Explicit;
import me.explicit.config.ConfigManager;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.ChatUtils;
import me.explicit.utils.CombatUtils;
import me.explicit.utils.Game;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KillSults
extends Module {
    private List killsults = new ArrayList();
    private String mode = "";
    private EntityLivingBase lastHit = null;

    public KillSults() {
        super("Killsults", 0, Category.BLATANT, "Automatically says something from the killsults.txt file when you kill someone. The file is in /config/explicit");
    }

    @Override
    public void setup() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Vanilla");
        arrayList.add("Hypixel");
        Explicit.instance.sm.rSetting(new Setting("Mode", this, "Vanilla", arrayList));
        Explicit.instance.sm.rSetting(new Setting("Reload", this, false));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.setKillsults();
    }

    @Override
    public void onUpdateNoToggle() {
        this.mode = Explicit.instance.sm.getSettingByName(this, "Mode").getValString();
        if (Explicit.instance.sm.getSettingByName(this, "Reload").getValBoolean()) {
            this.setKillsults();
            Explicit.instance.sm.getSettingByName(this, "Reload").setValBoolean(false);
        }
    }

    @SubscribeEvent
    public void chat(ClientChatReceivedEvent clientChatReceivedEvent) {
        String string;
        if (this.mode.equalsIgnoreCase("Hypixel") && this.lastHit != null && (string = clientChatReceivedEvent.message.getUnformattedText()).startsWith(this.lastHit.getName()) && (string.endsWith(Game.Player().getName()) || string.endsWith(Game.Player().getName() + "."))) {
            this.insult();
        }
    }

    @SubscribeEvent
    public void kill(LivingDeathEvent livingDeathEvent) {
        if (livingDeathEvent.source.getEntity() != null && livingDeathEvent.source.getEntity().getName() == Game.Player().getName() && CombatUtils.canTarget((Entity)livingDeathEvent.entityLiving, true) && this.mode.equalsIgnoreCase("Vanilla")) {
            this.insult();
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onAttack(AttackEntityEvent attackentityevent) {
        if (attackentityevent.entityLiving != null && attackentityevent.entityLiving.getName() == Game.Player().getName() && attackentityevent.target != null && CombatUtils.canTarget(attackentityevent.target, true)) {
            this.lastHit = (EntityLivingBase) attackentityevent.target;
        }
    }

    public void insult() {
        if (this.killsults.isEmpty()) {
            return;
        }
        String string = (String)this.killsults.get(ThreadLocalRandom.current().nextInt(this.killsults.size()));
        Game.Player().sendChatMessage(string.replaceAll("%name%", this.lastHit.getName()));
    }

    public void setKillsults() {
        ConfigManager.getConfigFile("Killsults", false);
        File file = ConfigManager.KILLSULTS;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            this.killsults.clear();
            String string = bufferedReader.readLine();
            if (string != null) {
                this.killsults.add(string);
                return;
            }
            ChatUtils.sendMessage("Reloaded Killsults");
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}

