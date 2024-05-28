package study.botnerd.modules.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import study.botnerd.Main;
import study.botnerd.gui.clickgui.Setting;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

public class FastLadder extends Module{
	public FastLadder() {
		super("FastLadder", "Allows you to climb up ladders faster", Category.Player);
		Main.instance.settingsManager.rSetting(new Setting("Speed", this, 0.3, 0.0, 0.8, false));
	}
    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent e){
    	if(mc.world == null) return;
		if(!mc.player.isOnLadder() || mc.player.moveForward == 0 && mc.player.moveStrafing == 0) return;
		mc.player.motionY = Main.instance.settingsManager.getSettingByName(this, "Speed").getValDouble();
    }
}
