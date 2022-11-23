/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 */
package me.explicit.color;

import me.explicit.color.Chroma;
import net.minecraftforge.common.MinecraftForge;

public class ColorManager {
    public Chroma cc = new Chroma();

    public ColorManager() {
        MinecraftForge.EVENT_BUS.register((Object)this.cc);
    }
}

