/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.MathHelper
 */
package me.explicit.module.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Mouse;

import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.skid.RavenUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AimAssist
extends Module {

    public static Setting Players,Inv,Mobs,Animals;

    public static Setting speed, compliment,showaimobject;
    public static Setting fov;
    public static Setting distance;
    public static Setting clickAim;
    public static Setting weaponOnly;
    public static Setting breakBlocks;
    public static Setting blatantMode;
    public static Setting ignoreFriends;
    public static ArrayList<Entity> friends = new ArrayList<>();

    public AimAssist() {
        super("AimAssist", 0, Category.COMBAT, "Helps with aiming");
    }

    @Override
    public void setup() {
//        Explicit.instance.sm.rSetting(new Setting("HSpeed", this, 50.0, 10.0, 250.0, true));
        Explicit.instance.sm.rSetting(speed = new Setting("Speed 1",this, 45.0D, 5.0D, 100.0D, true));
        Explicit.instance.sm.rSetting(compliment = new Setting("Speed 2", this,15.0D, 2D, 97.0D, true));
        Explicit.instance.sm.rSetting(fov = new Setting("FOV", this,90.0D, 15.0D, 360.0D, true));
        Explicit.instance.sm.rSetting(distance = new Setting("Distance", this,4.5D, 1.0D, 10.0D, false));
        Explicit.instance.sm.rSetting(clickAim = new Setting("ClickAim", this,true));
        Explicit.instance.sm.rSetting(breakBlocks = new Setting("BreakBlocks", this,true));
        Explicit.instance.sm.rSetting(ignoreFriends = new Setting("Ignore Friends", this,true));
        Explicit.instance.sm.rSetting(weaponOnly = new Setting("Weapon only", this,false));
        Explicit.instance.sm.rSetting(blatantMode = new Setting("Blatant mode", this,false));
        Explicit.instance.sm.rSetting(Players = new Setting("Players", this, true));
        Explicit.instance.sm.rSetting(Inv = new Setting("Inv", this, true));
        Explicit.instance.sm.rSetting(Mobs = new Setting("Mobs", this, true));
        Explicit.instance.sm.rSetting(Animals = new Setting("Animals", this, true));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
		
        if(!RavenUtils.currentScreenMinecraft()){
            return;
        }
        if(!RavenUtils.isPlayerInGame()) return;

        if (breakBlocks.isEnabled() && mc.objectMouseOver != null) {
            BlockPos p = mc.objectMouseOver.getBlockPos();
            if (p != null) {
                Block bl = mc.theWorld.getBlockState(p).getBlock();
                if (bl != Blocks.air && !(bl instanceof BlockLiquid) && bl instanceof  Block) {
                    return;
                }
            }
        }


        if (!weaponOnly.isEnabled() || RavenUtils.isPlayerHoldingWeapon()) {

            if (!clickAim.isEnabled() || Mouse.isButtonDown(0)) {
                Entity en = this.getEnemy();
                if (en != null) {

                    if (blatantMode.isEnabled()) {
                        RavenUtils.aim(en, 0.0F, false);
                    } else {
                        double n = RavenUtils.fovFromEntity(en);
                        if (n > 1.0D || n < -1.0D) {
                            double complimentSpeed = n*(ThreadLocalRandom.current().nextDouble(compliment.getValDouble() - 1.47328, compliment.getValDouble() + 2.48293)/100);
                            float val = (float)(-(complimentSpeed + n / (101.0D - (float)ThreadLocalRandom.current().nextDouble(speed.getValDouble() - 4.723847, speed.getValDouble()))));
                            mc.thePlayer.rotationYaw += val;
                        }
                    }
                }
            }
        }
    }

    public static boolean isAFriend(Entity entity) {
        if(entity == mc.thePlayer) return true;

        for (Entity wut : friends){
            if (wut.equals(entity))
                return true;
        }
        try {
            EntityPlayer bruhentity = (EntityPlayer) entity;
            if(mc.thePlayer.isOnSameTeam((EntityLivingBase) entity) || mc.thePlayer.getDisplayName().getUnformattedText().startsWith(bruhentity.getDisplayName().getUnformattedText().substring(0, 2))) return true;
        } catch (Exception fhwhfhwe) {

        }



        return false;
    }

    public Entity getEnemy() {
        int fov2 = (int) (fov.getValDouble());
        Iterator<Entity> var2 = mc.theWorld.loadedEntityList.iterator();

        Entity en;
        do {
                do {
                    do {
                        do {
                            do {
                                do {
                                    if (!var2.hasNext()) {
                                        return null;
                                    }

                                    en = (Entity) var2.next();
                                } while (ignoreFriends.isEnabled() && isAFriend(en));
                            } while(en == mc.thePlayer);
                        } while(en.isDead != false);
                    } while(!Inv.isEnabled() && en.isInvisible());
                } while((double)mc.thePlayer.getDistanceToEntity(en) > distance.getValDouble());
        	} while(!blatantMode.isEnabled() && !RavenUtils.fov(en, (float)fov2));

        if (en instanceof EntityPlayer){
            if (Players.isEnabled()) {
                return en;
            }
        }
        if (en instanceof EntityAnimal){
            if (Animals.isEnabled()) {
                return en;
            }
        }
        if (en instanceof EntityMob){
            if (Mobs.isEnabled()) {
                return en;
            }
        }

        return null;

    }

    public static void addFriend(Entity entityPlayer) {
        friends.add(entityPlayer);
    }

    public static boolean addFriend(String name) {
        boolean found = false;
        for (Entity entity:mc.theWorld.getLoadedEntityList()) {
            if (entity.getName().equalsIgnoreCase(name) || entity.getCustomNameTag().equalsIgnoreCase(name)) {
                if(!isAFriend(entity)) {
                    addFriend(entity);
                    found = true;
                }
            }
        }

        return found;
    }

    public static boolean removeFriend(String name) {
        boolean removed = false;
        boolean found = false;
        for (NetworkPlayerInfo networkPlayerInfo : new ArrayList<NetworkPlayerInfo>(mc.getNetHandler().getPlayerInfoMap())) {
            Entity entity = mc.theWorld.getPlayerEntityByName(networkPlayerInfo.getDisplayName().getUnformattedText());
            if (entity.getName().equalsIgnoreCase(name) || entity.getCustomNameTag().equalsIgnoreCase(name)) {
                removed = removeFriend(entity);
                found = true;
            }
        }

        return found && removed;
    }

    public static boolean removeFriend(Entity entityPlayer) {
        try{
            friends.remove(entityPlayer);
        } catch (Exception eeeeee){
            eeeeee.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Entity> getFriends() {
        return friends;
    }
}

