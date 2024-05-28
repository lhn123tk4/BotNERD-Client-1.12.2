package study.botnerd.modules.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;
import java.util.Random;

public class FakeLag extends Module {

    public int FOV = 100, minDelay = 150, maxDelay = 250;
    public float dist = 3.0F;
    public boolean attacking;

    public FakeLag() {
        super("FakeLag", "Simulates lag for your client", Category.Misc);
    }

    public static void sleep(int minDelay, int maxDelay) {
        try {
            Thread.sleep(new Random().nextInt(maxDelay - minDelay + 1) + minDelay);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown()) {
            attacking = true;
        } else {
            attacking = false;
        }
    }

    public void onUpdate() {
        if (attacking) {
            return; // If attacking, no need for fake lag
        }

        for (net.minecraft.entity.Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (entity instanceof net.minecraft.entity.player.EntityPlayer && entity != Minecraft.getMinecraft().player) {
                double distanceSq = Minecraft.getMinecraft().player.getDistanceSq(entity);
                if (distanceSq <= dist * dist && isInFOV(entity)) {
                    sleep(minDelay, maxDelay);
                    break;
                }
            }
        }
    }

    private boolean isInFOV(net.minecraft.entity.Entity entity) {
        double angle = Math.atan2(entity.posZ - Minecraft.getMinecraft().player.posZ, entity.posX - Minecraft.getMinecraft().player.posX);
        angle = Math.toDegrees(angle);
        angle -= Minecraft.getMinecraft().player.rotationYaw;
        angle = (angle + 360) % 360;

        return angle < FOV / 2 || angle > 360 - FOV / 2;
    }
}
