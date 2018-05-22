package com.sekwah.sekctweaks.modules.controls;

import com.sekwah.sekctweaks.gui.GuiControlsTweaked;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiControls;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiEventListener {

    @SubscribeEvent
    public void guiOpen(GuiOpenEvent event) {
        Gui gui  = event.getGui();
        if(gui != null && gui.getClass().toString().equals(GuiControls.class.toString())) {
            Minecraft mc = Minecraft.getMinecraft();
            event.setGui(new GuiControlsTweaked(mc.currentScreen, mc.gameSettings));
        }
    }

}
