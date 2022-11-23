/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.JOptionPane;

public class VersionCheck
extends Thread {
    @Override
    public void run() {
        try {
            URL uRL = new URL("https://bit.ly/explicitversion");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRL.openStream()));
            boolean bl = false;
            String string = bufferedReader.readLine();
            if (string != null) {
                int n = 0;
                String[] stringArray = string.split(":");
                String[] stringArray2 = stringArray;
                int n2 = stringArray2.length;
                if (n < n2) {
                    String string2 = stringArray2[n];
                    if (string2.equalsIgnoreCase("B9")) {
                        bl = true;
                    }
                    ++n;
                    return;
                }
                return;
            }
            if (!bl) {
                JOptionPane.showMessageDialog(null, "Your are not on the latest version of Explicit. Download here: https://explicitclient.weebly.com/", "Version Check", 2);
            }
            bufferedReader.close();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}

