package com.sekwah.sekctweaks.modules;

import com.sekwah.sekctweaks.SekCTweaks;
import net.minecraftforge.fml.common.ProgressManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

    private final HashMap<String, Module> modules = new HashMap<>();

    public ModuleManager(SekCTweaks mod) {
    }

    public void addModule(Module module) {
        if(module.getName() != null) {
            SekCTweaks.LOGGER.info("Added module: " + module.getName());
            this.modules.put(module.getName(), module);
        }
        else {
            throw new NullPointerException("Module name cannot be null");
        }
    }

    public void enableModules() {
        ProgressManager.ProgressBar bar = ProgressManager.push("Enabling Modules", this.modules.size());
        SekCTweaks.LOGGER.info(this.modules.size() + " modules registered.");
        for(Map.Entry<String,Module> moduleEntry : this.modules.entrySet()) {
            Module module = moduleEntry.getValue();
            bar.step("Enabling: " + module.getName());
            SekCTweaks.LOGGER.info("Enabling: " + module.getName());
            SekCTweaks.LOGGER.info((module.load() ? "Enabled: " : "Failed to enable: ") + module.getName());
        }
        ProgressManager.pop(bar);
    }

}
