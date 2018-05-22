package com.sekwah.sekctweaks;

import com.sekwah.sekctweaks.modules.ModuleManager;
import com.sekwah.sekctweaks.modules.controls.ControlsModule;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SekCTweaks.MODID, name = SekCTweaks.NAME, version = SekCTweaks.VERSION, clientSideOnly = true)
public class SekCTweaks {

    public static final String MODID = "sekctweaks";
    public static final String NAME = "SekCTweaks";
    public static final String VERSION = "1.0";
    public final ModuleManager moduleManager;

    public static Logger LOGGER;

    @Mod.Instance
    public static SekCTweaks instance;

    public SekCTweaks() {
        this.moduleManager = new ModuleManager(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER = event.getModLog();

        this.moduleManager.addModule(new ControlsModule());

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.moduleManager.enableModules();
    }

}
