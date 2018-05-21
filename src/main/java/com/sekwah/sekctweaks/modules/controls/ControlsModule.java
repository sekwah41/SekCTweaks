package com.sekwah.sekctweaks.modules.controls;

import com.sekwah.sekctweaks.modules.Module;
import net.minecraftforge.common.MinecraftForge;

public class ControlsModule implements Module {

    private GuiEventListener listener;

    @Override
    public String getName() {
        return "Improved Controls Menu";
    }

    @Override
    public boolean load() {
        listener = new GuiEventListener();
        MinecraftForge.EVENT_BUS.register(listener);
        return true;
    }

    @Override
    public boolean unload() {
        MinecraftForge.EVENT_BUS.unregister(listener);
        return true;
    }
}
