package com.sekwah.sekctweaks.modules;

import com.sekwah.sekctweaks.SekCTweaks;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

    private final Logger logger;

    private final HashMap<String, Module> modules = new HashMap<>();

    public ModuleManager(SekCTweaks mod) {
        this.logger = mod.logger;
    }

    public void addModule(Module module) {
        if(module.getName() != null) {
            this.logger.info("Added module: " + module.getName());
            this.modules.put(module.getName(), module);
        }
        else {
            throw new NullPointerException("Module name cannot be null");
        }
    }

    public void enableModules() {
        this.logger.info(this.modules.size() + " modules registered.");
        for(Map.Entry<String,Module> moduleEntry : this.modules.entrySet()) {
            Module module = moduleEntry.getValue();
            this.logger.info("Enabling: " + module.getName());
            this.logger.info((module.load() ? "Enabled: " : "Failed to enable: ") + module.getName());
        }
    }

}
