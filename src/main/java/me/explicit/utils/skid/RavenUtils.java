package me.explicit.utils.skid;

import net.java.games.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;

import me.explicit.settings.Setting;


public class RavenUtils {
	public static Minecraft mc = Minecraft.getMinecraft();
	private static final Random rand = new Random();
	private static Field timerField = null;
	private static Field mouseButton = null;
	private static Field mouseButtonState = null;
	private static Field mouseButtons = null;

	public static List<Entity> getEntityList() {
		return mc.theWorld.getLoadedEntityList();
	}

	public static boolean playerOverAir() {
		double x = mc.thePlayer.posX;
		double y = mc.thePlayer.posY - 1.0D;
		double z = mc.thePlayer.posZ;
		BlockPos p = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
		return mc.theWorld.isAirBlock(p);
	}

	public static int getBlockAmountInCurrentStack(int currentItem) {
		if (mc.thePlayer.inventory.getStackInSlot(currentItem) == null) {
			return 0;
		} else {
			ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(currentItem);
			if(itemStack.getItem() instanceof ItemBlock) {
				return itemStack.stackSize;
			} else {
				return 0;
			}
		}
	}

	public static void correctSliders(Setting c, Setting d) {
		if (c.getValDouble() > d.getValDouble()) {
			double p = c.getValDouble();
			c.setValDouble(d.getValDouble());
			d.setValDouble(p);
		}
	}

	public static void hotkeyToSlot(int slot) {
		if(!RavenUtils.isPlayerInGame())
			return;

		mc.thePlayer.inventory.currentItem = slot;
	}

	public static int getCurrentPlayerSlot() {
		return mc.thePlayer.inventory.currentItem;
	}


	public static net.minecraft.util.Timer getTimer() {
		try {
			return (net.minecraft.util.Timer) timerField.get(mc);
		} catch (IndexOutOfBoundsException | IllegalAccessException var1) {
			return null;
		}
	}

	public static void aim(Entity en, float ps, boolean pc) {
		if (en != null) {
			float[] t = gr(en);
			if (t != null) {
				float y = t[0];
				float p = t[1] + 4.0F + ps;
				if (pc) {
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(y, p, mc.thePlayer.onGround));
				} else {
					mc.thePlayer.rotationYaw = y;
					mc.thePlayer.rotationPitch = p;
				}
			}

		}
	}

	public static float[] gr(Entity q) {
		if (q == null) {
			return null;
		} else {
			double diffX = q.posX - mc.thePlayer.posX;
			double diffY;
			if (q instanceof EntityLivingBase) {
				EntityLivingBase en = (EntityLivingBase)q;
				diffY = en.posY + (double)en.getEyeHeight() * 0.9D - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
			} else {
				diffY = (q.getEntityBoundingBox().minY + q.getEntityBoundingBox().maxY) / 2.0D - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
			}

			double diffZ = q.posZ - mc.thePlayer.posZ;
			double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
			float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
			float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
			return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)};
		}
	}
	public static boolean isHyp() {
		if(!RavenUtils.isPlayerInGame()) return false;
		try {
			return !mc.isSingleplayer() && mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
		} catch (Exception welpBruh) {
			welpBruh.printStackTrace();
			return false;
		}
	}
	public static double ranModuleVal(Setting a, Setting b, Random r) {
		return a.getValDouble() == b.getValDouble() ? a.getValDouble() : a.getValDouble() + r.nextDouble() * (b.getValDouble() - a.getValDouble());
	}

	public static Random rand() {
		return rand;
	}

	public static void su() {
		try {
			timerField = Minecraft.class.getDeclaredField("field_71428_T");
		} catch (Exception var4) {
			try {
				timerField = Minecraft.class.getDeclaredField("timer");
			} catch (Exception ignored) {}
		}

		if (timerField != null) {
			timerField.setAccessible(true);
		}

		try {
			mouseButton = MouseEvent.class.getDeclaredField("button");
			mouseButtonState = MouseEvent.class.getDeclaredField("buttonstate");
			mouseButtons = Mouse.class.getDeclaredField("buttons");
		} catch (Exception var2) {
		}

	}

	public enum ClickEvents {
		RENDER,
		TICK
	}

	public static void setMouseButtonState(int mouseButton, boolean held) {
		if (RavenUtils.mouseButton != null && mouseButtonState != null && mouseButtons != null) {
			MouseEvent m = new MouseEvent();

			try {
				RavenUtils.mouseButton.setAccessible(true);
				RavenUtils.mouseButton.set(m, mouseButton);
				mouseButtonState.setAccessible(true);
				mouseButtonState.set(m, held);
				MinecraftForge.EVENT_BUS.post(m);
				mouseButtons.setAccessible(true);
				ByteBuffer bf = (ByteBuffer) mouseButtons.get(null);
				mouseButtons.setAccessible(false);
				bf.put(mouseButton, (byte)(held ? 1 : 0));
			} catch (IllegalAccessException var4) {
			}

		}
	}

	private static final Random RANDOM = new Random();

	public static boolean isPlayerHoldingWeapon() {
		if (mc.thePlayer.getCurrentEquippedItem() == null) {
			return false;
		} else {
			Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
			return item instanceof ItemSword || item instanceof ItemAxe;
		}
	}

	public static float getDirection() {
		float var1 = mc.thePlayer.rotationYaw;
		if (mc.thePlayer.moveForward < 0.0F) {
			var1 += 180.0F;
		}
		float forward = 1.0F;
		if (mc.thePlayer.moveForward < 0.0F) {
			forward = -0.5F;
		} else if (mc.thePlayer.moveForward > 0.0F) {
			forward = 0.5F;
		}
		if (mc.thePlayer.moveStrafing > 0.0F) {
			var1 -= 90.0F * forward;
		}
		if (mc.thePlayer.moveStrafing < 0.0F) {
			var1 += 90.0F * forward;
		}
		var1 *= 0.017453292F;
		return var1;
	}
	public static boolean nullCheck() {
		return (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null);
	}

	public static void copy(String content) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(content), null);
	}

	public static boolean isPlayerInGame() {
		return mc.thePlayer != null && mc.theWorld != null;
	}

	public static boolean currentScreenMinecraft() {
		return mc.currentScreen == null;
	}

	public static double fovFromEntity(Entity en) {
		return ((double)(mc.thePlayer.rotationYaw - fovToEntity(en)) % 360.0D + 540.0D) % 360.0D - 180.0D;
	}

	public static float fovToEntity(Entity ent) {
		double x = ent.posX - mc.thePlayer.posX;
		double z = ent.posZ - mc.thePlayer.posZ;
		double yaw = Math.atan2(x, z) * 57.2957795D;
		return (float)(yaw * -1.0D);
	}

	public static boolean fov(Entity entity, float fov) {
		fov = (float)((double)fov * 0.5D);
		double v = ((double)(mc.thePlayer.rotationYaw - fovToEntity(entity)) % 360.0D + 540.0D) % 360.0D - 180.0D;
		return v > 0.0D && v < (double)fov || (double)(-fov) < v && v < 0.0D;
	}
}
