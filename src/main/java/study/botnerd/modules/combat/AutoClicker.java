package study.botnerd.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

public class AutoClicker extends Module {

    private final long delay = 0;
    private long lastClickTime = 0;

    public AutoClicker() {
        super("AutoClicker", "Test", Category.Combat);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (this.isToggled() && Minecraft.getMinecraft().currentScreen == null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime >= delay) {
                clickMouse();
                lastClickTime = currentTime;
            }
        }
    }

    private void clickMouse() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player != null) {
            mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
            mc.player.swingArm(mc.player.getActiveHand());
        }
    }
}
