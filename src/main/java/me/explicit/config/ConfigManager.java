/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package me.explicit.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.explicit.Explicit;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.ui.clickgui.ClickGUI;
import me.explicit.ui.clickgui.Panel;
import net.minecraft.client.Minecraft;

public class ConfigManager {
    public static File dir;
    private static final File DEFAULT;
    private static final File FRIENDS;
    public static final File KILLSULTS;
    public static final File CLICKGUI;

    public static File getConfigFile(String string, boolean bl) {
        File file = !bl ? new File(dir, String.format("%s.txt", string)) : new File(dir, String.format("%s.cfg", string));
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return file;
    }

    public static void init() {
        if (!dir.exists()) {
            dir.mkdir();
        }
        ConfigManager.LoadConfig("Default");
        ConfigManager.LoadFriends();
        ConfigManager.loadGUISettings();
    }

    public static void SaveConfigFile(String string) {
        try {
            if (Explicit.instance.sm == null || Explicit.instance.sm.getSettings() == null) {
                return;
            }
            PrintWriter printWriter = new PrintWriter(ConfigManager.getConfigFile(string, true));
            Iterator iterator = Explicit.instance.sm.getSettings().iterator();
            if (iterator.hasNext()) {
                Setting setting = (Setting)iterator.next();
                if (setting.isCheck()) {
                    printWriter.write("SET:" + setting.getParentMod().getName() + ":" + setting.getName() + ":" + setting.getValBoolean());
                } else if (setting.isCombo()) {
                    printWriter.write("SET:" + setting.getParentMod().getName() + ":" + setting.getName() + ":" + setting.getValString());
                } else if (setting.isSlider()) {
                    printWriter.write("SET:" + setting.getParentMod().getName() + ":" + setting.getName() + ":" + setting.getValDouble());
                } else {
                    printWriter.write("SET:" + setting.getParentMod().getName() + ":" + setting.getName());
                }
                printWriter.println();
                return;
            }
            int n = 0;
            if (n < Explicit.instance.mm.getModules().size()) {
                Module module = (Module)Explicit.instance.mm.getModules().get(n);
                printWriter.write("MOD:" + module.getName() + ":" + module.isToggled() + ":" + module.getKey());
                if (Explicit.instance.mm.getModules().size() != n) {
                    printWriter.println();
                }
                ++n;
                return;
            }
            printWriter.close();
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public static void SaveFriendsFile() {
        try {
            PrintWriter printWriter = new PrintWriter(FRIENDS);
            int n = 0;
            if (n < Explicit.instance.friendManager.getFriends().size()) {
                printWriter.println((String)Explicit.instance.friendManager.getFriends().get(n));
                ++n;
                return;
            }
            printWriter.close();
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
    public static void LoadFriends() {
        try {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(ConfigManager.FRIENDS));
            String s;

            if (ConfigManager.FRIENDS.exists()) {
                while ((s = bufferedreader.readLine()) != null) {
                    Explicit.instance.friendManager.addFriendNoSave(s);
                }
            }

            bufferedreader.close();
        } catch (Exception exception) {
        	
        }

    }

    public static List GetConfigs() {
        File[] fileArray;
        File[] fileArray2;
        int n;
        int n2;
        ArrayList<String> arrayList = new ArrayList<String>();
        File file = dir;
        if (file.isDirectory() && (n2 = 0) < (n = (fileArray2 = (fileArray = file.listFiles())).length)) {
            File file2 = fileArray2[n2];
            if (file2.getName().contains(".cfg") && !file2.getName().equalsIgnoreCase("Default.cfg")) {
                String string = file2.getName();
                arrayList.add(string);
            }
            ++n2;
            return null;
        }
        return arrayList;
    }

    public static void saveGUISettings() {
        try {
            PrintWriter printWriter = new PrintWriter(CLICKGUI);
            Iterator iterator = ClickGUI.panels.iterator();
            if (iterator.hasNext()) {
                Panel panel = (Panel)iterator.next();
                printWriter.println(panel.title + ":" + panel.x + ":" + panel.y + ":" + panel.extended);
                return;
            }
            printWriter.close();
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public static void loadGUISettings() {
        ArrayList arraylist = new ArrayList();

        String s;

        try {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(ConfigManager.CLICKGUI));

            while ((s = bufferedreader.readLine()) != null) {
                arraylist.add(s);
            }

            bufferedreader.close();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

        if (!arraylist.isEmpty()) {
            Iterator iterator = arraylist.iterator();

            while (iterator.hasNext()) {
                s = (String) iterator.next();
                String[] astring = s.split(":");

                if (astring.length > 3) {
                    Iterator iterator1 = ClickGUI.panels.iterator();

                    while (iterator1.hasNext()) {
                        Panel panel = (Panel) iterator1.next();

                        if (panel.title.equalsIgnoreCase(astring[0])) {
                            panel.x = Double.parseDouble(astring[1]);
                            panel.y = Double.parseDouble(astring[2]);
                            panel.extended = Boolean.parseBoolean(astring[3]);
                        }
                    }
                }
            }
        }

    }


    public static void LoadConfig(String s) {
        try {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(getConfigFile(s, true)));
            String s1;

            if (getConfigFile(s, true).exists()) {
                while ((s1 = bufferedreader.readLine()) != null) {
                    String[] astring = s1.split(":");
                    Module module = Explicit.instance.mm.getModuleByName(astring[1]);

                    if (module != null) {
                        if (astring[0].equalsIgnoreCase("set")) {
                            Setting setting = Explicit.instance.sm.getSettingByName(module, astring[2]);

                            if (setting != null) {
                                if (setting.isCheck()) {
                                    setting.setValBooleanNoSave(Boolean.parseBoolean(astring[3]));
                                } else if (setting.isCombo()) {
                                    setting.setValStringNoSave(astring[3]);
                                } else if (setting.isSlider()) {
                                    setting.setValDoubleNoSave(Double.parseDouble(astring[3]));
                                }
                            }
                        } else if (astring[0].equalsIgnoreCase("mod")) {
                            if (Boolean.parseBoolean(astring[2])) {
                                module.setToggledNoSave(true);
                            }

                            int i = Integer.parseInt(astring[3]);

                            if (i != 0) {
                                module.setKey(i);
                            }
                        }
                    }
                }
            }
            bufferedreader.close();
        } catch (Exception exception) {
            
        }

    }

    static {
        File file = Minecraft.getMinecraft().mcDataDir;
        dir = new File(file, "config/explicit");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        DEFAULT = ConfigManager.getConfigFile("Default", true);
        FRIENDS = ConfigManager.getConfigFile("Friends", false);
        CLICKGUI = ConfigManager.getConfigFile("ClickGUI", false);
        File file2 = new File(dir, "Killsults.txt");
        if (!file2.exists()) {
            try {
                file2.createNewFile();
                PrintWriter printWriter = new PrintWriter(file2);
                printWriter.println("Download the Explicit client at explicitclient - weebly - com");
                printWriter.println("get tapped ur bad %name%");
                printWriter.println("%name% imagine not using Explicit");
                printWriter.close();
            }
            catch (IOException iOException) {
            	iOException.printStackTrace();
            }
        }
        KILLSULTS = file2;
    }
}

