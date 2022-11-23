/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.client.event.RenderPlayerEvent$Post
 *  net.minecraftforge.client.event.RenderPlayerEvent$Pre
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package me.explicit.module.render;

import me.explicit.module.Category;
import me.explicit.module.Module;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Chams
extends Module {
    public Chams() {
        super("Chams", "Renders players through walls", Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderLiving(RenderPlayerEvent.Pre pre) {
        GL11.glEnable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)-1100000.0f);
    }

    @SubscribeEvent
    public void onRenderLiving(RenderPlayerEvent.Post post) {
        GL11.glDisable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)1100000.0f);
        mc.getRenderManager().setRenderOutlines(false);
    }
}

