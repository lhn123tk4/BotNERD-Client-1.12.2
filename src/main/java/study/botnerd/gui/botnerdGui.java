package study.botnerd.gui;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import study.botnerd.Main;
import study.botnerd.gui.clickgui.component.Component;
import study.botnerd.utils.ColorUtil;
import study.botnerd.utils.CustomFontRenderer;

public class botnerdGui extends GuiScreen{

    private final GuiScreen parentScreen;
    protected boolean hovered;
    private final CustomFontRenderer customFontRenderer;

    public botnerdGui()
    {
        this.parentScreen = new GuiMainMenu();
        this.customFontRenderer = new CustomFontRenderer();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        ScaledResolution sr = new ScaledResolution(mc);
        this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), Color.BLACK.getRGB(), ColorUtil.rainbow(300));
        this.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), 0x9F000000);
        this.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), 0x7F000000);
        this.customFontRenderer.drawCenteredString("BotNERD Client", this.width / 2, 17, ColorUtil.rainbow(300));
        this.customFontRenderer.drawCenteredString("Version: " + Main.VERSION, this.width / 2, 30, 10526880);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }

    @Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1)
        {
            mc.displayGuiScreen(new GuiMainMenu());
        }
        else if (button.id == 0)
        {

        }
    }
}
