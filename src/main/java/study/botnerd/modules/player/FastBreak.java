package study.botnerd.modules.player;

import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import study.botnerd.Main;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;
import study.botnerd.utils.BlockUtil;
import study.botnerd.utils.PlayerUtil;

public class FastBreak extends Module{
	public FastBreak() {
		super("FastBreak", "Allows you to break blocks faster", Category.Player);
	}
	
    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent e){
		PlayerUtil.setBlockHitDelay(0);
	}
	
    @SubscribeEvent
    public void onLeftClickBlock(LeftClickBlock event) {
    	float progress = PlayerUtil.getCurBlockDamageMP() + BlockUtil.getHardness(event.getPos());	
    	if(progress >= 1) return;
    	Main.instance.sendPacket(new CPacketPlayerDigging(
    			CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(),
    			mc.objectMouseOver.sideHit));
    }
}
