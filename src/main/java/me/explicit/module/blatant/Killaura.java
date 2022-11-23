/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.world.World
 */
package me.explicit.module.blatant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.netty.util.internal.ThreadLocalRandom;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.Game;
import me.explicit.utils.RotationUtils;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;

public class Killaura extends Module {
	private TimerUtils switchTimer = new TimerUtils();
	private TimerUtils attackTimer = new TimerUtils();

	public EntityLivingBase target;

	private List targets = new ArrayList();

	private boolean isBlocking;
	private boolean canBlock;
	private int currentIndex;

	public static boolean isAttackTick = false;
	public static boolean isRotationTick = false;

	private String mode;
	private String rotationsmode;

	private double range;
	private double blockrange;
	private int hitchance;
	private int switchdelay;
	private double aps;

	public boolean autoblock;
	private boolean raytrace;

	// target
	private boolean player;
	private boolean mobs;
	private boolean animals;
	private boolean invis;

	public Killaura() {
		super("Killaura", 0, Category.BLATANT, "Automatically hits entities around you");
	}

	@Override
	public void setup() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("Switch");
		arrayList.add("Single");
		arrayList.add("Multi");
		Explicit.instance.sm.rSetting(new Setting("Mode", this, "Switch", arrayList));
		ArrayList<String> arrayList2 = new ArrayList<String>();
		arrayList2.add("Blatant");
		arrayList2.add("Smooth");
		arrayList2.add("Legit");
		arrayList2.add("None");
		Explicit.instance.sm.rSetting(new Setting("Rotations", this, "Legit", arrayList2));
		Explicit.instance.sm.rSetting(new Setting("Range", this, 4.2, 1.0, 6.0, false));
		Explicit.instance.sm.rSetting(new Setting("BlockRange", this, 5.0, 1.0, 10.0, false));
		Explicit.instance.sm.rSetting(new Setting("HitChance", this, 100.0, 1.0, 100.0, true));
		Explicit.instance.sm.rSetting(new Setting("SwitchDelay", this, 200.0, 1.0, 1000.0, true));
		Explicit.instance.sm.rSetting(new Setting("APS", this, 10.0, 2.0, 20.0, false));
		Explicit.instance.sm.rSetting(new Setting("RandomizeAPS", this, true));
		Explicit.instance.sm.rSetting(new Setting("Autoblock", this, false));
		Explicit.instance.sm.rSetting(new Setting("Raytrace", this, false));
		Explicit.instance.sm.rSetting(new Setting("OnClick", this, false));
		Explicit.instance.sm.rSetting(new Setting("SwordOnly", this, false));
		Explicit.instance.sm.rSetting(new Setting("AxeOnly", this, false));

		Explicit.instance.sm.rSetting(new Setting("Players", this, false));
		Explicit.instance.sm.rSetting(new Setting("Mobs", this, false));
		Explicit.instance.sm.rSetting(new Setting("Animals", this, false));
		Explicit.instance.sm.rSetting(new Setting("Invisibles", this, false));
	}

	@Override
	public void onTick() {
		isRotationTick = false;
		this.updateSettings();
		this.setTargets();
		if (Game.Player() == null || Game.World() == null) {
			return;
		}
		if (Explicit.instance.sm.getSettingByName(this, "OnClick").getValBoolean()
				&& !Killaura.mc.gameSettings.keyBindAttack.isKeyDown()) {
			return;
		}
		this.isBlocking = Game.Player().isUsingItem() && Killaura.mc.thePlayer.getCurrentEquippedItem() != null
				&& Killaura.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
		if (this.target != null) {
			this.rotate();
			double d = Killaura.mc.thePlayer.getDistanceToEntity((Entity) this.target);
			this.canBlock = d <= this.blockrange && d <= this.range && this.autoblock
					&& mc.thePlayer.getCurrentEquippedItem() != null
					&& Killaura.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
			if (this.canBlock && !this.isBlocking) {
				this.block();
			}
			if (d <= this.range && this.attackTimer.hasReached(1000.0 / this.aps)) {
				mc.thePlayer.swingItem();
				if (ThreadLocalRandom.current().nextInt(0, 100) <= this.hitchance
						&& (Killaura.mc.objectMouseOver.entityHit != null
								&& (Killaura.mc.objectMouseOver.entityHit == this.target
										|| this.mode.equalsIgnoreCase("multi"))
								|| !this.raytrace)
						&& this.canHit()) {
					if (this.isBlocking) {
						this.unBlock();
					}
					isAttackTick = true;
					this.attack();
					if (this.canBlock) {
						this.block();
					}
				}
				this.attackTimer.reset();
			} else {
				isAttackTick = false;
			}
		} else if (this.isBlocking) {
			this.unBlock();
		}
	}

	private boolean canHit() {
		boolean flag = Explicit.instance.sm.getSettingByName(this, "SwordOnly").getValBoolean();
		boolean flag1 = Explicit.instance.sm.getSettingByName(this, "AxeOnly").getValBoolean();

		if (flag || flag1) {
			if (Game.Player().getCurrentEquippedItem() == null) {
				return false;
			}

			if (flag && !flag1 && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemSword)) {
				return false;
			}

			if (!flag && flag1 && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
				return false;
			}

			if (flag && flag1 && !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemAxe)
					&& !(Game.Player().getCurrentEquippedItem().getItem() instanceof ItemSword)) {
				return false;
			}
		}

		return true;
	}

	private void rotate() {
		if (this.canHit()) {
			float[] afloat = RotationUtils.getRotations(this.target);

			if (this.rotationsmode.equalsIgnoreCase("Blatant")) {
				Game.Player().rotationYaw = afloat[0];
				Game.Player().rotationPitch = afloat[1];
				Killaura.isRotationTick = true;
			} else {
				double d0;
				float f;
				float f1;
				double d1;

				if (this.rotationsmode.equalsIgnoreCase("Smooth")) {
					d0 = ThreadLocalRandom.current().nextDouble(7.5D, 10.0D);
					f = RotationUtils.getYawChange(Game.Player().prevRotationYaw,
							this.target.posX + ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D) * 0.05D,
							this.target.posZ + ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D) * 0.05D);
					f1 = (float) ((double) f / d0);
					Game.Player().rotationYaw = Game.Player().prevRotationYaw + f1;
					d1 = (double) (afloat[1] - Game.Player().rotationPitch);
					Game.Player().rotationPitch = (float) ((double) Game.Player().rotationPitch + d1 / d0);
					Killaura.isRotationTick = true;
				} else if (this.rotationsmode.equalsIgnoreCase("Legit")) {
					d0 = ThreadLocalRandom.current().nextDouble(15.0D, 20.0D);
					f = RotationUtils.getYawChange(Game.Player().prevRotationYaw,
							this.target.posX + ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D) * 0.05D,
							this.target.posZ + ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D) * 0.05D);
					f1 = (float) ((double) f / d0);
					Game.Player().rotationYaw = Game.Player().prevRotationYaw + f1;
					d1 = (double) (afloat[1] - Game.Player().rotationPitch);
					Game.Player().rotationPitch = (float) ((double) Game.Player().rotationPitch + d1 / d0);
					Killaura.isRotationTick = true;
				}
			}
		}

		if (Killaura.mc.thePlayer.rotationPitch > 90.0F) {
			Killaura.mc.thePlayer.rotationPitch = 90.0F;
		} else if (Killaura.mc.thePlayer.rotationPitch < -90.0F) {
			Killaura.mc.thePlayer.rotationPitch = -90.0F;
		}

	}

	@Override
	public void onEnable() {
		this.targets.clear();
		super.onEnable();
	}

	@Override
	public void onDisable() {
		if (this.isBlocking) {
			this.unBlock();
		}
		isAttackTick = false;
		isRotationTick = false;
		super.onDisable();
	}

	private int getTargetInt() {
		for (int i = 0; i < this.targets.size(); ++i) {
			if (this.targets.get(i) == this.target) {
				return i;
			}
		}
		return -1;
	}

	private void setTargets() {
		this.targets = this.getTargets();
		if (!this.targets.isEmpty() && (this.target == null || this.targets.contains(this.target))) {
			if (!this.mode.equalsIgnoreCase("Switch") && this.switchTimer.hasReached((double) this.switchdelay)
					&& (!this.mode.equalsIgnoreCase("Multi") || this.targets.size() != 1)) {
				if (this.mode.equalsIgnoreCase("Single")) {
					if (this.target != null && this.isValid(this.target) && this.targets.size() == 1) {
						return;
					}

					if (this.target == null) {
						this.target = (EntityLivingBase) this.targets.get(0);
					} else if (this.targets.size() > 1) {
						int i = this.targets.size() - 1;

						if (this.currentIndex >= i) {
							this.currentIndex = 0;
						} else {
							++this.currentIndex;
						}

						if (this.targets.get(this.currentIndex) != null
								&& this.targets.get(this.currentIndex) != this.target) {
							this.target = (EntityLivingBase) this.targets.get(this.currentIndex);
							this.switchTimer.reset();
						}
					} else {
						this.target = null;
					}
				} else if (this.mode.equalsIgnoreCase("Multi")) {
					if (this.targets.isEmpty()) {
						this.target = null;
					} else if (this.target != null && !this.targets.contains(this.target) || this.target == null) {
						this.target = (EntityLivingBase) this.targets
								.get(ThreadLocalRandom.current().nextInt(this.targets.size() - 1));
					}
				}
			} else {
				if (this.target != null && this.isValid(this.target)) {
					return;
				}

				this.target = (EntityLivingBase) this.targets.get(0);
			}

		} else {
			this.target = null;
		}
	}

	private List getTargets() {

		ArrayList arraylist = new ArrayList();
		Iterator iterator = mc.theWorld.loadedEntityList.iterator();

		while (iterator.hasNext()) {
			Entity entity = (Entity) iterator.next();

			if (this.isValid(entity)) {
				arraylist.add((EntityLivingBase) entity);
			}
		}

		return arraylist;
	}

	private boolean isValid(Entity e) {
		if (e == mc.thePlayer) {
			return false;
		}
		if (!e.isEntityAlive()) {
			return false;
		}
		if (e instanceof EntityPlayer && player && mc.thePlayer.isOnSameTeam((EntityLivingBase) e)) {
			return true;
		}
		if (e instanceof EntityMob && mobs) {
			return true;
		}
		if (e instanceof EntityAnimal && animals) {
			return true;
		}
		if (e.isInvisible() && !invis) {
			return true;
		}
		return false;
	}

	private boolean attack() {
		if (this.target == null) {
			return false;
		} else {
			if (mode.equalsIgnoreCase("Multi")) {
				Iterator iterator = this.targets.iterator();

				while (iterator.hasNext()) {
					EntityLivingBase entitylivingbase = (EntityLivingBase) iterator.next();

					mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entitylivingbase, Action.ATTACK));
				}
			} else {
				mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, Action.ATTACK));
			}

			return true;
		}
	}

	private void unBlock() {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
		mc.playerController.onStoppedUsingItem(mc.thePlayer);
	}

	private void block() {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
		if (mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem())) {
			mc.getItemRenderer().resetEquippedProgress2();
		}
	}

	private void updateSettings() {
		this.mode = Explicit.instance.sm.getSettingByName(this, "Mode").getValString();
		this.rotationsmode = Explicit.instance.sm.getSettingByName(this, "Rotations").getValString();
		this.range = Explicit.instance.sm.getSettingByName(this, "Range").getValDouble();
		this.blockrange = Explicit.instance.sm.getSettingByName(this, "BlockRange").getValDouble();
		this.hitchance = Explicit.instance.sm.getSettingByName(this, "HitChance").getValInt();
		this.switchdelay = Explicit.instance.sm.getSettingByName(this, "SwitchDelay").getValInt();
		boolean bl = Explicit.instance.sm.getSettingByName(this, "RandomizeAPS").getValBoolean();
		this.aps = Explicit.instance.sm.getSettingByName(this, "APS").getValDouble()
				+ (bl ? ThreadLocalRandom.current().nextDouble(-1.0, 1.0) : 0.0);
		this.autoblock = Explicit.instance.sm.getSettingByName(this, "AutoBlock").getValBoolean();
		this.raytrace = Explicit.instance.sm.getSettingByName(this, "Raytrace").getValBoolean();

		this.player = Explicit.instance.sm.getSettingByName(this, "Player").getValBoolean();
		this.mobs = Explicit.instance.sm.getSettingByName(this, "Mobs").getValBoolean();
		this.animals = Explicit.instance.sm.getSettingByName(this, "Animals").getValBoolean();
		this.invis = Explicit.instance.sm.getSettingByName(this, "Invisibles").getValBoolean();
	}
}
