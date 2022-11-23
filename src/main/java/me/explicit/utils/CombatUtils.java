/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package me.explicit.utils;

import me.explicit.Explicit;
import me.explicit.module.combat.AntiBot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class CombatUtils {
	
    public static boolean canTarget(Entity entity, boolean flag) {
        if (entity != null && entity != Game.Player()) {
            EntityPlayer entityplayer = null;
            EntityLivingBase entitylivingbase = null;

            if (entity instanceof EntityPlayer) {
                entityplayer = (EntityPlayer) entity;
            }

            if (entity instanceof EntityLivingBase) {
                entitylivingbase = (EntityLivingBase) entity;
            }

            boolean flag1 = Explicit.instance.mm.getModuleByName("Players").isToggled();
            boolean flag2 = Explicit.instance.mm.getModuleByName("Animals").isToggled();
            boolean flag3 = Explicit.instance.mm.getModuleByName("Mobs").isToggled();
            boolean flag4 = Explicit.instance.mm.getModuleByName("Invisibles").isToggled();
            boolean flag5 = Explicit.instance.mm.getModuleByName("Others").isToggled();
            boolean flag6 = Explicit.instance.mm.getModuleByName("IgnoreNaked").isToggled();
            boolean flag7 = Explicit.instance.mm.getModuleByName("Teams").isToggled() && flag;
            boolean flag8 = false;

            if (flag7 && isTeam(Game.Player(), entity)) {
                flag8 = true;
            }

            boolean flag9 = true;

            if (entity.isInvisible() && !flag4) {
                flag9 = false;
            }

            boolean flag10 = false;

            if (Explicit.instance.mm.getModuleByName("Friends").isToggled() && Explicit.instance.friendManager.getFriends().contains(entity.getName())) {
                flag10 = true;
            }

            return !AntiBot.getBots().contains(entity) && !(entity instanceof EntityArmorStand) && !flag10 && flag9 && (entity instanceof EntityPlayer && flag1 && !flag8 && (!flag || !isNaked(entitylivingbase) || !flag6) || entity instanceof EntityAnimal && flag2 || entity instanceof EntityMob && flag3 || entity instanceof EntityLivingBase && flag5 && !(entity instanceof EntityMob) && !(entity instanceof EntityAnimal) && !(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && entitylivingbase.isEntityAlive());
        } else {
            return false;
        }
    }
	
    public static boolean isTeam(EntityPlayer entityplayer, Entity entity) {
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).getTeam() != null && entityplayer.getTeam() != null) {
            Character character = Character.valueOf(entity.getDisplayName().getFormattedText().charAt(3));
            Character character1 = Character.valueOf(entityplayer.getDisplayName().getFormattedText().charAt(3));
            Character character2 = Character.valueOf(entity.getDisplayName().getFormattedText().charAt(2));
            Character character3 = Character.valueOf(entityplayer.getDisplayName().getFormattedText().charAt(2));
            boolean flag = false;

            if (character.equals(character1) && character2.equals(character3)) {
                flag = true;
            } else {
                Character character4 = Character.valueOf(entity.getDisplayName().getFormattedText().charAt(1));
                Character character5 = Character.valueOf(entityplayer.getDisplayName().getFormattedText().charAt(1));
                Character character6 = Character.valueOf(entity.getDisplayName().getFormattedText().charAt(0));
                Character character7 = Character.valueOf(entityplayer.getDisplayName().getFormattedText().charAt(0));

                if (character4.equals(character5) && Character.isDigit(0) && character6.equals(character7)) {
                    flag = true;
                }
            }

            return flag;
        } else {
            return true;
        }
    }

    private static boolean isNaked(EntityLivingBase entitylivingbase) {
        return entitylivingbase.getTotalArmorValue() == 0;
    }
}

