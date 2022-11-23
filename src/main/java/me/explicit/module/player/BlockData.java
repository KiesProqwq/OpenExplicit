/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 */
package me.explicit.module.player;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

class BlockData {
    public BlockPos position;
    public EnumFacing face;

    public BlockData(BlockPos blockPos, EnumFacing enumFacing) {
        this.position = blockPos;
        this.face = enumFacing;
    }
}

