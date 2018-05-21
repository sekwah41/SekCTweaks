package com.sekwah.sekctweaks.modules;

public interface Module {

    String getName();

    /**
     * Does Minecraft need to be reloaded to properly redo this module
     */
    boolean needsRestart = false;

    /**
     * Calls a load to the module
     * @return if the module was loaded
     */
    boolean load();

    /**
     * Calls the module to unload
     * @return if the module was unloaded
     */
    boolean unload();

}
