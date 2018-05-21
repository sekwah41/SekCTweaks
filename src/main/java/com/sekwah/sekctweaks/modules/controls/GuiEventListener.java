package com.sekwah.sekctweaks.modules.controls;

import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiEventListener {

    @SubscribeEvent
    public void guiOpen(GuiOpenEvent event) {
        System.out.println("CHANGE");
    }

}
