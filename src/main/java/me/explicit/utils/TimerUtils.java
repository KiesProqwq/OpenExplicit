/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.utils;

public class TimerUtils {
    private long lastMS;

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(double d) {
        return (double)(this.getCurrentMS() - this.lastMS) >= d;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean delay(float f) {
        return (float)(this.getTime() - this.lastMS) >= f;
    }

    public long getTime() {
        return this.getCurrentMS() - this.lastMS;
    }
}

