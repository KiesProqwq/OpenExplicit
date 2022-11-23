/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.util.BlockPos
 */
package me.explicit.module.render;

import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

class BlockFound {
    public BlockPos pos;
    public Block block;
    public Color color;

    public BlockFound(BlockPos blockPos, Block block, Color color) {
        this.pos = blockPos;
        this.block = block;
        this.color = color;
    }
}

