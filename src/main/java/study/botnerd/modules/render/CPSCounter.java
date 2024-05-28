package study.botnerd.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

public class CPSCounter extends Module {

    private int clicks = 0;
    private long lastClickTime = System.currentTimeMillis();

    public CPSCounter() {
        super("CPSCounter", "Displays CPS (Clicks Per Second)", Category.Render);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > 1000) {
            // Reset CPS count every second
            displayCPS(clicks);
            clicks = 0;
            lastClickTime = currentTime;
        }
    }

    @SubscribeEvent
    public void onMouseClick(InputEvent.MouseInputEvent event) {
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null) {
            return;
        }

        if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) { // Check for left mouse button press
            clicks++;
        }
    }

    private void displayCPS(int cps) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Your CPS: " + cps));
    }
}
