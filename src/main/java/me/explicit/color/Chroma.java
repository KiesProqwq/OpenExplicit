/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.explicit.color;

import java.awt.Color;
import me.explicit.utils.TimerUtils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Chroma {
    private TimerUtils timer = new TimerUtils();
    private int lastHue;

    @SubscribeEvent
    public void render(RenderGameOverlayEvent renderGameOverlayEvent) {
        if (renderGameOverlayEvent.type.equals((Object)RenderGameOverlayEvent.ElementType.TEXT) && this.timer.hasReached(33.3333333333)) {
            this.changeColor();
            this.timer.reset();
        }
    }

    public Color getColor(int n) {
        int n2 = this.lastHue + n * 2;
        if (n2 > 1000) {
            n2 -= 1000;
            return null;
        }
        return Color.getHSBColor((float)n2 / 1000.0f, 1.0f, 1.0f);
    }

    private void changeColor() {
        this.lastHue += 5;
        if (this.lastHue > 1000) {
            this.lastHue = 0;
        }
    }
}

