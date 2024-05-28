package study.botnerd.modules.render;

import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

public class Fullbright extends Module {
	
    public Fullbright(){
        super("Fullbright", "Makes maximum lighting", Category.Render);
    }
    @Override
    public void onEnable(){
        super.onEnable();
        mc.gameSettings.gammaSetting = 100;
    }
    
    @Override
    public void onDisable(){
        super.onDisable();
        mc.gameSettings.gammaSetting = 1.0f;
    }
}
