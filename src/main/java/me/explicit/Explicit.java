/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.command.ICommand
 *  net.minecraft.network.INetHandler
 *  net.minecraftforge.client.ClientCommandHandler
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.input.Keyboard
 */
package me.explicit;

import java.io.File;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import me.explicit.color.ColorManager;
import me.explicit.command.commands.BindCommand;
import me.explicit.command.commands.ConfigCommand;
import me.explicit.command.commands.FriendCommand;
import me.explicit.command.commands.HelpCommand;
import me.explicit.command.commands.SettingsCommand;
import me.explicit.command.commands.ToggleCommand;
import me.explicit.config.ConfigManager;
import me.explicit.consolegui.ConsoleGUI;
import me.explicit.friends.FriendManager;
import me.explicit.module.Module;
import me.explicit.module.ModuleManager;
import me.explicit.net.NetHandler;
import me.explicit.settings.SettingsManager;
import me.explicit.ui.hud.HUDRenderer;
import me.explicit.utils.Game;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.ICommand;
import net.minecraft.network.INetHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class Explicit {
	//String info
    public static final String MODID = "Explicit";
    public static final String NAME = "Explicit";
    public static final String VERSION = "B9";
    //instance
    public static Explicit instance;
    //Manager
    public ModuleManager mm;
    public SettingsManager sm;
    public ColorManager cm;
    public ConfigManager configManager;
    public FriendManager friendManager;
    
//    public ClickGUI clickGui;
    public ConsoleGUI cg;
    public HUDRenderer uiRenderer;
    
    public static boolean destructed = false;
    public static boolean consolegui = false;

    public void onInit() {
    	
//        VersionCheck versionCheck = new VersionCheck();
//        versionCheck.setName("VersionCheck");
//        versionCheck.start();
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.configManager = new ConfigManager();
        this.sm = new SettingsManager();
        this.mm = new ModuleManager();
//        this.clickGui = new ClickGUI();
        this.cm = new ColorManager();
        this.uiRenderer = new HUDRenderer();
        ConfigManager.init();
        this.friendManager = new FriendManager();
        if (!destructed) {
            this.registerCommands();
        }
        this.cg = new ConsoleGUI();
        this.cg.setName("ConsoleGUI");
        this.cg.start();
    }

    public void registerCommands() {
        ClientCommandHandler.instance.registerCommand((ICommand)new FriendCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new ToggleCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new BindCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new ConfigCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new SettingsCommand());
        ClientCommandHandler.instance.registerCommand((ICommand)new HelpCommand());
    }

    public void onSelfDestruct() {
        Minecraft.getMinecraft().currentScreen = null;
        Iterator iterator = this.mm.modules.iterator();
        
        if (iterator.hasNext()) {
            Module module = (Module)iterator.next();
            MinecraftForge.EVENT_BUS.unregister((Object)module);
            module.setToggledNoSave(false);
            module.setName("");
            module = null;
            this.mm.modules.remove(module);
            return;
        }
        
        MinecraftForge.EVENT_BUS.unregister(this);
        consolegui = false;
        destructed = true;
        this.configManager = null;
        this.uiRenderer = null;
//        this.clickGui = null;
        this.mm = null;
        this.sm = null;
        File file = Minecraft.getMinecraft().mcDataDir;
        File file2 = new File(file, "GUI");
        String[] stringArray = file.list();
        int n = 0;
        if (n < stringArray.length) {
            String string = stringArray[n];
            File file3 = new File(file.getPath(), string);
            file3.delete();
            ++n;
            return;
        }
        file.delete();
        if (file2.exists()) {
            int n2 = 0;
            String[] stringArray2 = file2.list();
            if (n2 < stringArray2.length) {
                String string = stringArray2[n2];
                File file4 = new File(file2.getPath(), string);
                file4.delete();
                ++n2;
                return;
            }
            file2.delete();
        }
    }

    @SubscribeEvent
    public void ClientTick(ClientTickEvent clienttickevent) {
        if (Game.World() != null) {
            INetHandler inethandler = Game.Player().sendQueue.getNetworkManager().getNetHandler();

            if (!(inethandler instanceof NetHandler)) {
                Game.Player().sendQueue.getNetworkManager().setNetHandler(new NetHandler((NetHandlerPlayClient) inethandler));
            }
        }

        if (!Explicit.destructed) {
            Iterator iterator = this.mm.getModules().iterator();

            while (iterator.hasNext()) {
                Module module = (Module) iterator.next();

                if (Game.World() != null && Game.Player() != null) {
//                    module.onUpdateNoToggle();
                    if (module.isToggled()) {
                        module.onUpdate();
                    }
                }
            }

        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent livingupdateevent) {
        if (livingupdateevent.entityLiving != null && livingupdateevent.entityLiving == Game.Player()) {
            Iterator iterator = this.mm.getModules().iterator();

            while (iterator.hasNext()) {
                Module module = (Module) iterator.next();

                if (module.isToggled() && Game.World() != null && Game.Player() != null) {
                    module.onMove();
                }
            }
        }

    }

    @SubscribeEvent
    public void PlayerTick(PlayerTickEvent playertickevent) {
        if (!Explicit.destructed) {
            Iterator iterator = this.mm.getModules().iterator();

            while (iterator.hasNext()) {
                Module module = (Module) iterator.next();

                if (module.isToggled() && Game.World() != null && Game.Player() != null) {
//                    module.onTick();
                }
            }

        }
    }

    @SubscribeEvent
    public void key(KeyInputEvent keyinputevent) {
        if (!Explicit.destructed && Game.World() != null && Game.Player() != null) {
            try {
                if (Keyboard.isCreated() && Keyboard.getEventKeyState()) {
                    int i = Keyboard.getEventKey();

                    if (i <= 0) {
                        return;
                    }

                    Iterator iterator = this.mm.modules.iterator();

                    while (iterator.hasNext()) {
                        Module module = (Module) iterator.next();

                        if (module.getKey() == i && i > 0) {
                            module.toggle();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent rendergameoverlayevent) {
        if (rendergameoverlayevent.type.equals(ElementType.TEXT) && !Explicit.destructed) {
            this.uiRenderer.draw();
            Iterator iterator = this.mm.getModules().iterator();

            while (iterator.hasNext()) {
                Module module = (Module) iterator.next();

                if (module.isToggled() && Game.World() != null && Game.Player() != null) {
                    module.onRender2D();
                }
            }
        }
    }

    @SubscribeEvent
    public void render3d(RenderWorldLastEvent renderworldlastevent) {
        if (!Explicit.destructed) {
            Iterator iterator = this.mm.getModules().iterator();

            while (iterator.hasNext()) {
                Module module = (Module) iterator.next();

                if (module.isToggled() && Game.World() != null && Game.Player() != null) {
                    module.onRender3D();
                }
            }
        }
    }

}

