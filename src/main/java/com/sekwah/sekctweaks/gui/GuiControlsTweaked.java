package com.sekwah.sekctweaks.gui;

import java.io.IOException;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiControlsTweaked extends GuiScreen
{
    private static final GameSettings.Options[] OPTIONS_ARR = new GameSettings.Options[] {GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN, GameSettings.Options.AUTO_JUMP};
    /** A reference to the screen object that created this. Used for navigating between screens. */
    private final GuiScreen parentScreen;
    protected String screenTitle = "Controls";
    /** Reference to the GameSettings object. */
    private final GameSettings options;
    /** The ID of the button that has been pressed. */
    public KeyBinding buttonId;
    public long time;
    private GuiKeyBindingListTweaked keyBindingList;
    private GuiButton buttonReset;

    public GuiControlsTweaked(GuiScreen screen, GameSettings settings)
    {
        this.parentScreen = screen;
        this.options = settings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.keyBindingList = new GuiKeyBindingListTweaked(this, this.mc);
        this.buttonList.add(new GuiButton(200, this.width / 2 - 155 + 160, this.height - 29, 150, 20, I18n.format("gui.done")));
        this.buttonReset = this.addButton(new GuiButton(201, this.width / 2 - 155, this.height - 29, 150, 20, I18n.format("controls.resetAll")));
        this.screenTitle = I18n.format("controls.title");
        int i = 0;

        for (GameSettings.Options gamesettings$options : OPTIONS_ARR)
        {
            if (gamesettings$options.isFloat())
            {
                this.buttonList.add(new GuiOptionSlider(gamesettings$options.getOrdinal(), this.width / 2 - 155 + i % 2 * 160, 18 + 24 * (i >> 1), gamesettings$options));
            }
            else
            {
                this.buttonList.add(new GuiOptionButton(gamesettings$options.getOrdinal(), this.width / 2 - 155 + i % 2 * 160, 18 + 24 * (i >> 1), gamesettings$options, this.options.getKeyBinding(gamesettings$options)));
            }

            ++i;
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.keyBindingList.handleMouseInput();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 200)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        else if (button.id == 201)
        {
            for (KeyBinding keybinding : this.mc.gameSettings.keyBindings)
            {
                keybinding.setToDefault();
            }

            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else if (button.id < 100 && button instanceof GuiOptionButton)
        {
            this.options.setOptionValue(((GuiOptionButton)button).getOption(), 1);
            button.displayString = this.options.getKeyBinding(GameSettings.Options.byOrdinal(button.id));
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (this.buttonId != null)
        {
            this.buttonId.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.getActiveModifier(), -100 + mouseButton);
            this.options.setOptionKeyBinding(this.buttonId, -100 + mouseButton);
            this.buttonId = null;
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else if (mouseButton != 0 || !this.keyBindingList.mouseClicked(mouseX, mouseY, mouseButton))
        {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    /**
     * Called when a mouse button is released.
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (state != 0 || !this.keyBindingList.mouseReleased(mouseX, mouseY, state))
        {
            super.mouseReleased(mouseX, mouseY, state);
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.buttonId != null)
        {
            if (keyCode == 1)
            {
                this.buttonId.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.NONE, 0);
                this.options.setOptionKeyBinding(this.buttonId, 0);
            }
            else if (keyCode != 0)
            {
                this.buttonId.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.getActiveModifier(), keyCode);
                this.options.setOptionKeyBinding(this.buttonId, keyCode);
            }
            else if (typedChar > 0)
            {
                this.buttonId.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.getActiveModifier(), typedChar + 256);
                this.options.setOptionKeyBinding(this.buttonId, typedChar + 256);
            }

            if (!net.minecraftforge.client.settings.KeyModifier.isKeyCodeModifier(keyCode))
            this.buttonId = null;
            this.time = Minecraft.getSystemTime();
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else
        {
            super.keyTyped(typedChar, keyCode);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.keyBindingList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 8, 16777215);
        boolean flag = false;

        for (KeyBinding keybinding : this.options.keyBindings)
        {
            if (!keybinding.isSetToDefaultValue())
            {
                flag = true;
                break;
            }
        }

        this.buttonReset.enabled = flag;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @SideOnly(Side.CLIENT)
    class List extends GuiSlot
    {
        /** A list containing the many different locale language codes. */
        private final java.util.List<String> langCodeList = Lists.<String>newArrayList();
        /** The map containing the Locale-Language pairs. */
        //private final Map<String, Language> languageMap = Maps.<String, Language>newHashMap();

        public List(Minecraft mcIn)
        {
            super(mcIn, GuiControlsTweaked.this.width, GuiControlsTweaked.this.height, 32, GuiControlsTweaked.this.height - 65 + 4, 18);

            /*for (Language language : GuiLanguage.this.languageManager.getLanguages())
            {
                this.languageMap.put(language.getLanguageCode(), language);
                this.langCodeList.add(language.getLanguageCode());
            }*/
        }

        protected int getSize()
        {
            return this.langCodeList.size();
        }

        /**
         * The element in the slot that was clicked, boolean for whether it was double clicked or not
         */
        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
        {
            /*Language language = this.languageMap.get(this.langCodeList.get(slotIndex));
            GuiControlsTweaked.this.languageManager.setCurrentLanguage(language);
            GuiControlsTweaked.this.game_settings_3.language = language.getLanguageCode();
            this.mc.refreshResources();
            GuiControlsTweaked.this.fontRenderer.setUnicodeFlag(GuiControlsTweaked.this.languageManager.isCurrentLocaleUnicode() || GuiControlsTweaked.this.game_settings_3.forceUnicodeFont);
            GuiControlsTweaked.this.fontRenderer.setBidiFlag(GuiControlsTweaked.this.languageManager.isCurrentLanguageBidirectional());
            GuiControlsTweaked.this.confirmSettingsBtn.displayString = I18n.format("gui.done");
            GuiControlsTweaked.this.forceUnicodeFontBtn.displayString = GuiControlsTweaked.this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
            GuiControlsTweaked.this.game_settings_3.saveOptions();*/
        }

        /**
         * Returns true if the element passed in is currently selected
         */
        protected boolean isSelected(int slotIndex)
        {
            return false;//((String)this.langCodeList.get(slotIndex)).equals(GuiControlsTweaked.this.languageManager.getCurrentLanguage().getLanguageCode());
        }

        /**
         * Return the height of the content being scrolled
         */
        protected int getContentHeight()
        {
            return this.getSize() * 18;
        }

        protected void drawBackground()
        {
            GuiControlsTweaked.this.drawDefaultBackground();
        }

        protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks)
        {
            GuiControlsTweaked.this.fontRenderer.setBidiFlag(true);
            //GuiControlsTweaked.this.drawCenteredString(GuiControlsTweaked.this.fontRenderer, ((Language)this.languageMap.get(this.langCodeList.get(slotIndex))).toString(), this.width / 2, yPos + 1, 16777215);
            //GuiControlsTweaked.this.fontRenderer.setBidiFlag(GuiControlsTweaked.this.languageManager.getCurrentLanguage().isBidirectional());
        }
    }
}