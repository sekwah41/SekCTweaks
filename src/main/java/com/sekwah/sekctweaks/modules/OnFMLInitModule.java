package com.sekwah.sekctweaks.modules;

public interface OnFMLInitModule extends Module {

    /**
     * If things are changed on startup it likely will need to restart
     */
    boolean needsRestart = true;

    /**
     * Calls on startup
     * @return if the module was loaded
     */
    boolean onInit();

}
