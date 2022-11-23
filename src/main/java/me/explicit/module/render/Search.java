/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.explicit.module.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.module.render.BlockFound;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.RenderUtils;
import me.explicit.utils.TimerUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Search
extends Module {
    public int r;
    public boolean iron;
    public boolean gold;
    public boolean diamond;
    public boolean emerald;
    public boolean lapis;
    public boolean redstone;
    public boolean coal;
    public boolean spawner;
    
    public List ores;
    public List blocksFound;
    private TimerUtils updateTimer = new TimerUtils();

    public Search() {
        super("Search", 0, Category.RENDER, "Allows you to find ores");
    }

    @Override
    public void setup() {
        Explicit.instance.sm.rSetting(new Setting("Range", this, 50.0, 2.0, 256.0, true));
        Explicit.instance.sm.rSetting(new Setting("Update", this, 4000.0, 1000.0, 9999.0, true));
        Explicit.instance.sm.rSetting(new Setting("Iron", this, true));
        Explicit.instance.sm.rSetting(new Setting("Coal", this, false));
        Explicit.instance.sm.rSetting(new Setting("Gold", this, false));
        Explicit.instance.sm.rSetting(new Setting("Diamond", this, true));
        Explicit.instance.sm.rSetting(new Setting("Redstone", this, false));
        Explicit.instance.sm.rSetting(new Setting("Emerald", this, false));
        Explicit.instance.sm.rSetting(new Setting("Lapis", this, false));
        Explicit.instance.sm.rSetting(new Setting("Spawner", this, false));
        (this.blocksFound = new ArrayList()).clear();
        (this.ores = new ArrayList()).add(Blocks.iron_ore);
        this.ores.add(Blocks.gold_ore);
        this.ores.add(Blocks.diamond_ore);
        this.ores.add(Blocks.emerald_ore);
        this.ores.add(Blocks.lapis_ore);
        this.ores.add(Blocks.redstone_ore);
        this.ores.add(Blocks.coal_ore);
        this.ores.add(Blocks.mob_spawner);
    }

    @Override
    public void onUpdateNoToggle() {
        this.r = (int)Explicit.instance.sm.getSettingByName(this, "Range").getValDouble();
        this.iron = Explicit.instance.sm.getSettingByName(this, "Iron").getValBoolean();
        this.gold = Explicit.instance.sm.getSettingByName(this, "Gold").getValBoolean();
        this.diamond = Explicit.instance.sm.getSettingByName(this, "Diamond").getValBoolean();
        this.redstone = Explicit.instance.sm.getSettingByName(this, "Redstone").getValBoolean();
        this.coal = Explicit.instance.sm.getSettingByName(this, "Coal").getValBoolean();
        this.emerald = Explicit.instance.sm.getSettingByName(this, "Emerald").getValBoolean();
        this.lapis = Explicit.instance.sm.getSettingByName(this, "Lapis").getValBoolean();
        this.spawner = Explicit.instance.sm.getSettingByName(this, "Spawner").getValBoolean();
    }

    @SubscribeEvent
    public void orl(RenderWorldLastEvent renderWorldLastEvent) {
        if (this.updateTimer.hasReached(Explicit.instance.sm.getSettingByName(this, "Update").getValDouble())) {
            this.updateBlocks();
            this.updateTimer.reset();
        }
        if (!(this.iron || this.gold || this.coal || this.diamond || this.redstone || this.emerald || this.lapis || this.spawner)) {
            return;
        }
        int n = 0;
        if (n < this.blocksFound.size()) {
            this.draw((BlockFound)this.blocksFound.get(n));
            ++n;
            return;
        }
    }

    private void updateBlocks() {
        this.blocksFound.clear();
        BlockPos blockpos = Game.Player().getPosition();
        int i = this.r;

        for (int j = blockpos.getX() - i; j <= blockpos.getX() + i; ++j) {
            for (int k = blockpos.getZ() - i; k < blockpos.getZ() + i; ++k) {
                for (int l = blockpos.getY() - i; l < blockpos.getY() + i; ++l) {
                    Block block = Game.World().getBlockState(new BlockPos(j, l, k)).getBlock();

                    if (this.ores.contains(block) && (this.iron || !block.equals(Blocks.iron_ore)) && (this.gold || !block.equals(Blocks.gold_ore)) && (this.diamond || !block.equals(Blocks.diamond_ore)) && (this.emerald || !block.equals(Blocks.emerald_ore)) && (this.lapis || !block.equals(Blocks.lapis_ore)) && (this.redstone || !block.equals(Blocks.redstone_ore)) && (this.coal || !block.equals(Blocks.coal_ore)) && (this.spawner || !block.equals(Blocks.mob_spawner))) {
                        this.blocksFound.add(new BlockFound(new BlockPos(j, l, k), block, this.color(block)));
                    }
                }
            }
        }
    }

    private void draw(BlockFound blockFound) {
        RenderUtils.blockESPBox(blockFound.pos, blockFound.color.getRed(), blockFound.color.getGreen(), blockFound.color.getBlue(), 1.0f);
    }

    private Color color(Block block) {
        short short0 = 0;
        short short1 = 0;
        short short2 = 0;

        if (block.equals(Blocks.iron_ore)) {
            short0 = 255;
            short1 = 255;
            short2 = 255;
        } else if (block.equals(Blocks.gold_ore)) {
            short0 = 255;
            short1 = 255;
        } else if (block.equals(Blocks.diamond_ore)) {
            short1 = 220;
            short2 = 255;
        } else if (block.equals(Blocks.emerald_ore)) {
            short0 = 35;
            short1 = 255;
        } else if (block.equals(Blocks.lapis_ore)) {
            short1 = 50;
            short2 = 255;
        } else if (block.equals(Blocks.redstone_ore)) {
            short0 = 255;
        } else if (block.equals(Blocks.mob_spawner)) {
            short0 = 30;
            short2 = 135;
        }
        return new Color(short0, short1, short2);
    }
}

