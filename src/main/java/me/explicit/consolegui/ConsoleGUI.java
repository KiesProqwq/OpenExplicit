/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 *  net.minecraft.client.Minecraft
 */
package me.explicit.consolegui;

import io.netty.util.internal.ThreadLocalRandom;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import me.explicit.Explicit;
import me.explicit.module.Module;
import me.explicit.settings.Setting;
import me.explicit.utils.TimerUtils;
import net.minecraft.client.Minecraft;

public class ConsoleGUI
extends Thread {
    private String fileName;
    private String fileLocation;
    private TimerUtils fileChecker = new TimerUtils();
    private TimerUtils processChecker = new TimerUtils();
    private boolean hasOpened = false;

    @Override
    public void run() {
        Explicit.consolegui = true;
        if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
            Explicit.consolegui = false;
            System.out.println("------BAD OS------");
        } else {
            File file = Minecraft.getMinecraft().mcDataDir;
            File file1 = new File(file, "gui");

            if (!file1.exists()) {
                System.out.println("------NO FOLDER------");
                Explicit.consolegui = false;
            } else {
                File[] afile = file1.listFiles();

                for (int i = 0; i < afile.length; ++i) {
                    if (afile[i].isFile() && afile[i].getName().endsWith(".exe")) {
                        this.fileName = afile[i].getName();
                        this.fileLocation = afile[i].getAbsolutePath();
                    }
                }
        
        if (this.fileName != null && this.fileLocation != null) {
            try {
                File file2 = new File(file1, "Send");
                File file3;

                if (file2.exists()) {
                    String[] astring = file2.list();

                    for (int j = 0; j < astring.length; ++j) {
                        String s = astring[j];

                        file3 = new File(file2.getPath(), s);
                        file3.delete();
                    }

                    file2.delete();
                }

                File file4 = new File(file1, "Receive");

                if (file4.exists()) {
                    String[] astring1 = file4.list();

                    for (int k = 0; k < astring1.length; ++k) {
                        String s1 = astring1[k];
                        File file5 = new File(file4.getPath(), s1);

                        file5.delete();
                    }

                    file4.delete();
                }

                File file6 = new File(file1, "modules.txt");

                if (!file6.exists()) {
                    file6.createNewFile();
                }

                File file7 = new File(file1, "settings.txt");

                if (!file7.exists()) {
                    file7.createNewFile();
                }

                file3 = new File(file1, "run.bat");
                if (!file3.exists()) {
                    file3.createNewFile();
                }

                PrintWriter printwriter = new PrintWriter(file6);
                Iterator iterator = Explicit.instance.mm.getModules().iterator();

                while (iterator.hasNext()) {
                    Module module = (Module) iterator.next();

                    printwriter.println(module.getName().toLowerCase());
                }

                printwriter.close();
                PrintWriter printwriter1 = new PrintWriter(file7);

                for (Iterator iterator1 = Explicit.instance.sm.getSettings().iterator(); iterator1.hasNext(); printwriter1.println()) {
                    Setting setting = (Setting) iterator1.next();

                    printwriter1.print(setting.getParentMod().getName().toLowerCase() + ":" + setting.getName().toLowerCase() + ":");
                    if (setting.isCheck()) {
                        printwriter1.print("boolean");
                    } else if (setting.isCombo()) {
                        printwriter1.print("string:");
                        Iterator iterator2 = setting.getOptions().iterator();

                        while (iterator2.hasNext()) {
                            String s2 = (String) iterator2.next();

                            printwriter1.print(s2);
                            if (s2 != setting.getOptions().get(setting.getOptions().size() - 1)) {
                                printwriter1.print(":");
                            }
                        }
                    } else if (setting.isSlider()) {
                        printwriter1.print(setting.getMin() + ":" + setting.getMax());
                    }
                }

                printwriter1.close();
                PrintWriter printwriter2 = new PrintWriter(file3);

                printwriter2.print(this.fileName);
                printwriter2.close();
                Runtime.getRuntime().exec("explorer.exe /select," + file3.getAbsolutePath());
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
                System.out.println("------ERROR------");
                Explicit.consolegui = false;
            }

            if (Explicit.consolegui) {
                this.fileChecker.reset();
                this.processChecker.reset();
                //error
//                this.runChecks();
            } else {
                if (Explicit.instance.mm.getModuleByName("ClickGUI").getKey() == 0) {
                    Explicit.instance.mm.getModuleByName("ClickGUI").setKey(54);
                }
            }
            System.out.println("------BAD------");
            Explicit.consolegui = false;
        }
    }
}
}

    private void runChecks() {

    }

    private void sendMessage(List list) {
        File file = Minecraft.getMinecraft().mcDataDir;
        File file1 = new File(file, "gui");
        File file2 = new File(file1, "receive");

        if (!file2.isDirectory()) {
            file2.mkdir();
        }

        File file3 = new File(file2, String.valueOf((new StringBuilder()).append("Receive").append(this.randomString(5)).append("txt")));

        try {
            if (file3.createNewFile()) {
                PrintWriter printwriter = new PrintWriter(file3);
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    String s = (String) iterator.next();

                    printwriter.println(s);
                }

                printwriter.close();
            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }
    

    @SuppressWarnings("resource")
	private String toString(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String string = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        return string;
    }

    public String randomString(int n) {
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String string2 = "";
        int n2 = 0;
        if (n2 < n) {
            string2 = string2 + string.charAt(ThreadLocalRandom.current().nextInt(string.toCharArray().length));
            ++n2;
            return null;
        }
        return string2;
    }
}

