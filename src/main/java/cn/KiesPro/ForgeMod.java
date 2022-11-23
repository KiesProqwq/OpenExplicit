package cn.KiesPro;

import me.explicit.Explicit;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid="BeterFPS", name="BeterFPS", version="1.72", acceptedMinecraftVersions="[1.8.9]")
public class ForgeMod {
	
	/*
	 * 想开发我就看看这个话
	 * 
	 * 代码规范:
	 * 1.能用if for就不用其他逻辑控制语句
	 * 2.在赋值中每个值之间空格最终“;”不空格
	 * 3.本代码使用了MinecraftForge<@SubscribeEvent>(因为我是懒狗)
	 * 
	 * BUGs:
	 * 1.ConfigManager
	 * 2.HUD
	 * 3.所有用Explicit.java的功能
	 * 
	 * Tips:
	 * 1.用这个JvmArgs会加快加载世界速度 -DisableExplicitGC
	 * 
	 * 你会遇到许多的shit代码看不懂 改不明白
	 * 你会遇到的Reach.java,HitBox.java
	 * 因为有一部分是抄千鹤的我也不懂他的一些注释和操作英文注释的东西都不是我的thank u
	 * 
	 * 感谢一些人
	 * -千鹤的Classloader
	 * -[Sk1erLLC/Patcher](https://github.com/Sk1erLLC/Patcher)
	 * -[Zpeanut/Hydrogen](https://github.com/Zpeanut/Hydrogen)
	 * -HeroGUI(找不到他的GitHub了)
	 * -Astomero(无官方github)
	 */
	
	@SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void Mod(FMLPreInitializationEvent event) {
        Explicit.instance = new Explicit();
        Explicit.instance.onInit();
    }
}
