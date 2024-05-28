package study.botnerd.modules.movement;

import study.botnerd.modules.Category;
import study.botnerd.modules.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Fly extends Module {
	
    public Fly(){
        super("Fly", "Allows you to fly in survival mode", Category.Movement);
    }
    
    @Override
    public void onDisable(){
        super.onDisable();
    }
    
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e){
    	
    }
}
