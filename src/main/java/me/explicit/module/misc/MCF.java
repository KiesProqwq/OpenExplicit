package me.explicit.module.misc;

import java.util.Iterator;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.utils.ChatUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Mouse;

public class MCF
extends Module {
    boolean waitingForRelease = false;

    public MCF() {
        super("MCF", 0, Category.MISC, "MiddleClickFriends");
    }

    @Override
    public void onTick() {
        if (Mouse.isButtonDown(2) && !this.waitingForRelease) {
            if (mc.objectMouseOver != null && MCF.mc.objectMouseOver.entityHit != null && MCF.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
                boolean bl = false;
                Iterator iterator = Explicit.instance.friendManager.getFriends().iterator();
                if (iterator.hasNext()) {
                    String string = (String)iterator.next();
                    if (string.equalsIgnoreCase(mc.objectMouseOver.entityHit.getName())) {
                        bl = true;
                        return;
                    }
                    return;
                }
                
                if (!bl) {
                    Explicit.instance.friendManager.addFriend(mc.objectMouseOver.entityHit.getName());
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Added '" + MCF.getTeamColor((EntityPlayer)mc.objectMouseOver.entityHit) + mc.objectMouseOver.entityHit.getName() + EnumChatFormatting.GOLD + "' as a friend");
                } else {
                    Explicit.instance.friendManager.getFriends().remove(mc.objectMouseOver.entityHit.getName());
                    ChatUtils.sendMessage(EnumChatFormatting.GOLD + "Removed '" + MCF.getTeamColor((EntityPlayer)mc.objectMouseOver.entityHit) + mc.objectMouseOver.entityHit.getName() + EnumChatFormatting.GOLD + "' from your friends list");
                }
            }
            this.waitingForRelease = true;
        } else if (!Mouse.isButtonDown((int)2)) {
            this.waitingForRelease = false;
        }
    }
    
    public static String getTeamColor(EntityPlayer entityplayer) {
        Character character = Character.valueOf(entityplayer.getDisplayName().getFormattedText().charAt(1));

        return "\u00ef\u00bf\u00bd" + character;
    }
}

