package study.botnerd.modules.render;

import study.botnerd.Main;
import study.botnerd.gui.clickgui.Setting;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {
    public ClickGui(){
        super("ClickGui", "Allows you to enable and disable modules", Category.Render);
        Main.instance.settingsManager.rSetting(new Setting("Background",this,true));
        this.setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable(){
        mc.displayGuiScreen(Main.instance.clickGui);
        this.setToggled(false);
    }
}
