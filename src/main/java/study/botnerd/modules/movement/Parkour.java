package study.botnerd.modules.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;
import study.botnerd.utils.BlockUtil;

public class Parkour extends Module{
	public Parkour() {
		super("Parkour", "Jump when reaching a block's edge", Category.Movement);
	}
	
	@Override
	public String getDescription() {
		return "Jump when reaching a block's edge";
	}
	
    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent e){
		if(BlockUtil.isBlockEdge(mc.player) 
				&& !mc.player.isSneaking()) 
			mc.player.jump();
	}
}
