package com.sekwah.sekctweaks.gui;

import java.util.Arrays;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @see net.minecraft.client.gui.GuiLanguage for selective list
 */
@SideOnly(Side.CLIENT)
public class GuiKeyBindingListTweaked extends GuiListExtended
{
    private final GuiControlsTweaked controlsScreen;
    private final Minecraft mc;
    private final GuiListExtended.IGuiListEntry[] listEntries;
    private int maxListLabelWidth;

    public GuiKeyBindingListTweaked(GuiControlsTweaked controls, Minecraft mcIn)
    {
        super(mcIn, 450, controls.height, 63, controls.height - 32, 20);
        this.width = 420;
        this.right = controls.width - 20;
        this.left = this.right - this.width;
        this.controlsScreen = controls;
        this.mc = mcIn;
        KeyBinding[] akeybinding = ArrayUtils.clone(mcIn.gameSettings.keyBindings);
        this.listEntries = new GuiListExtended.IGuiListEntry[akeybinding.length + KeyBinding.getKeybinds().size()];
        Arrays.sort(akeybinding);
        int i = 0;
        String s = null;

        for (KeyBinding keybinding : akeybinding)
        {
            String s1 = keybinding.getKeyCategory();

            if (!s1.equals(s))
            {
                s = s1;
                this.listEntries[i++] = new GuiKeyBindingListTweaked.CategoryEntry(s1);
            }

            int j = mcIn.fontRenderer.getStringWidth(I18n.format(keybinding.getKeyDescription()));

            if (j > this.maxListLabelWidth)
            {
                this.maxListLabelWidth = j;
            }

            this.listEntries[i++] = new GuiKeyBindingListTweaked.KeyEntry(keybinding);
        }
    }

    protected int getSize()
    {
        return this.listEntries.length;
    }

    /**
     * Gets the IGuiListEntry object for the given index
     */
    public GuiListExtended.IGuiListEntry getListEntry(int index)
    {
        return this.listEntries[index];
    }

    protected int getScrollBarX()
    {
        return this.right - 25;
    }

    @Override
    public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
        super.drawScreen(mouseXIn,mouseYIn,partialTicks);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.disableDepth();
        this.overlayBackground(0, this.top, 255, 255);
        this.overlayBackground(this.bottom, this.height, 255, 255);
            int i = this.getScrollBarX();
            int j = i + 6;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableTexture2D();
        int i1 = 4;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos((double)this.left + 4, (double)(this.top), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        bufferbuilder.pos((double)this.left, (double)(this.top), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos((double)this.left, (double)this.bottom, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos((double)this.left + 4, (double)this.bottom, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        tessellator.draw();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos((double)this.right, (double)this.top, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos((double)this.right - 4, (double)this.top, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        bufferbuilder.pos((double)this.right - 4, (double)(this.bottom), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        bufferbuilder.pos((double)this.right, (double)(this.bottom), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        tessellator.draw();
        int j1 = this.getMaxScroll();

        if (j1 > 0)
        {
            int k1 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
            k1 = MathHelper.clamp(k1, 32, this.bottom - this.top - 8);
            int l1 = (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;

            if (l1 < this.top)
            {
                l1 = this.top;
            }

            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos((double)i, (double)this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos((double)j, (double)this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos((double)j, (double)this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos((double)i, (double)this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            tessellator.draw();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos((double)i, (double)(l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
            bufferbuilder.pos((double)j, (double)(l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
            bufferbuilder.pos((double)j, (double)l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferbuilder.pos((double)i, (double)l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
            tessellator.draw();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos((double)i, (double)(l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
            bufferbuilder.pos((double)(j - 1), (double)(l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
            bufferbuilder.pos((double)(j - 1), (double)l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
            bufferbuilder.pos((double)i, (double)l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
            tessellator.draw();
        }

        this.renderDecorations(mouseXIn, mouseYIn);
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
    }

    /**
     * Gets the width of the list
     */
    public int getListWidth()
    {
        return super.getListWidth() + 32;
    }

    @SideOnly(Side.CLIENT)
    public class CategoryEntry implements GuiListExtended.IGuiListEntry
    {
        private final String labelText;
        private final int labelWidth;

        public CategoryEntry(String name)
        {
            this.labelText = I18n.format(name);
            this.labelWidth = GuiKeyBindingListTweaked.this.mc.fontRenderer.getStringWidth(this.labelText);
        }

        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks)
        {
            GuiKeyBindingListTweaked.this.mc.fontRenderer.drawString(this.labelText, GuiKeyBindingListTweaked.this.mc.currentScreen.width / 2 - this.labelWidth / 2, y + slotHeight - GuiKeyBindingListTweaked.this.mc.fontRenderer.FONT_HEIGHT - 1, 16777215);
        }

        /**
         * Called when the mouse is clicked within this entry. Returning true means that something within this entry was
         * clicked and the list should not be dragged.
         */
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY)
        {
            return false;
        }

        /**
         * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
         */
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
        {
        }

        public void updatePosition(int slotIndex, int x, int y, float partialTicks)
        {
        }
    }

    @SideOnly(Side.CLIENT)
    public class KeyEntry implements GuiListExtended.IGuiListEntry
    {
        /** The keybinding specified for this KeyEntry */
        private final KeyBinding keybinding;
        /** The localized key description for this KeyEntry */
        private final String keyDesc;
        private final GuiButton btnChangeKeyBinding;
        private final GuiButton btnReset;

        private KeyEntry(KeyBinding name)
        {
            this.keybinding = name;
            this.keyDesc = I18n.format(name.getKeyDescription());
            this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 95, 20, I18n.format(name.getKeyDescription()));
            this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset"));
        }

        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks)
        {
            boolean flag = GuiKeyBindingListTweaked.this.controlsScreen.buttonId == this.keybinding;
            GuiKeyBindingListTweaked.this.mc.fontRenderer.drawString(this.keyDesc, x + 90 - GuiKeyBindingListTweaked.this.maxListLabelWidth, y + slotHeight / 2 - GuiKeyBindingListTweaked.this.mc.fontRenderer.FONT_HEIGHT / 2, 16777215);
            this.btnReset.x = x + 210;
            this.btnReset.y = y;
            this.btnReset.enabled = !this.keybinding.isSetToDefaultValue();
            this.btnReset.drawButton(GuiKeyBindingListTweaked.this.mc, mouseX, mouseY, partialTicks);
            this.btnChangeKeyBinding.x = x + 105;
            this.btnChangeKeyBinding.y = y;
            this.btnChangeKeyBinding.displayString = this.keybinding.getDisplayName();
            boolean flag1 = false;
            boolean keyCodeModifierConflict = true; // less severe form of conflict, like SHIFT conflicting with SHIFT+G

            if (this.keybinding.getKeyCode() != 0)
            {
                for (KeyBinding keybinding : GuiKeyBindingListTweaked.this.mc.gameSettings.keyBindings)
                {
                    if (keybinding != this.keybinding && keybinding.conflicts(this.keybinding))
                    {
                        flag1 = true;
                        keyCodeModifierConflict &= keybinding.hasKeyCodeModifierConflict(this.keybinding);
                    }
                }
            }

            if (flag)
            {
                this.btnChangeKeyBinding.displayString = TextFormatting.WHITE + "> " + TextFormatting.YELLOW + this.btnChangeKeyBinding.displayString + TextFormatting.WHITE + " <";
            }
            else if (flag1)
            {
                this.btnChangeKeyBinding.displayString = (keyCodeModifierConflict ? TextFormatting.GOLD : TextFormatting.RED) + this.btnChangeKeyBinding.displayString;
            }

            this.btnChangeKeyBinding.drawButton(GuiKeyBindingListTweaked.this.mc, mouseX, mouseY, partialTicks);
        }

        /**
         * Called when the mouse is clicked within this entry. Returning true means that something within this entry was
         * clicked and the list should not be dragged.
         */
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY)
        {
            if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingListTweaked.this.mc, mouseX, mouseY))
            {
                GuiKeyBindingListTweaked.this.controlsScreen.buttonId = this.keybinding;
                return true;
            }
            else if (this.btnReset.mousePressed(GuiKeyBindingListTweaked.this.mc, mouseX, mouseY))
            {
                this.keybinding.setToDefault();
                GuiKeyBindingListTweaked.this.mc.gameSettings.setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
                KeyBinding.resetKeyBindingArrayAndHash();
                return true;
            }
            else
            {
                return false;
            }
        }

        /**
         * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
         */
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
        {
            this.btnChangeKeyBinding.mouseReleased(x, y);
            this.btnReset.mouseReleased(x, y);
        }

        public void updatePosition(int slotIndex, int x, int y, float partialTicks)
        {
        }
    }
}