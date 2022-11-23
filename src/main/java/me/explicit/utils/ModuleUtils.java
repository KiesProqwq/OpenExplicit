/*
 * Decompiled with CFR 0.152.
 */
package me.explicit.utils;

import java.util.Iterator;
import me.explicit.Explicit;
import me.explicit.module.Category;
import me.explicit.module.Module;

public class ModuleUtils {
	
    public static void setAllModulesToggled(boolean flag) {
        Iterator iterator = Explicit.instance.mm.getModules().iterator();

        while (iterator.hasNext()) {
            Module module = (Module) iterator.next();
            if (!module.getName().equalsIgnoreCase("HUD") && !module.getCategory().equals(Category.VALUES)) {
                module.setToggled(flag);
            }
        }
    }
    
}

