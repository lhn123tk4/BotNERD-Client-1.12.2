package study.botnerd.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

import java.util.List;

public class WTap extends Module {

    public float dist = 2.8F;
    public int FOV = 45, minTapDelay = 100, maxTapDelay = 200;
    public boolean reqItem, released;
    public int[] itemWhitelist = {267, 276, 272, 283, 268};
    public Object target;
    private long lastClickTime = 0L;
    private long delay = 100L;
    private String mode = "legit";

    public WTap() {
        super("WTap", "W", Category.Combat);
    }

    public void onTick() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            if (target == null) {
                target = checkTarget(dist, FOV);
            }

            if (target != null) {
                if (System.currentTimeMillis() - lastClickTime >= delay && !released) {
                    KeyBinding.setKeyBindState(Keyboard.KEY_W, false);
                    released = true;
                    delay /= 3L;
                    lastClickTime = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - lastClickTime >= delay && released) {
                    KeyBinding.setKeyBindState(Keyboard.KEY_W, true);
                    KeyBinding.onTick(Keyboard.KEY_W);
                    released = false;
                    delay = (long) (Math.random() * (maxTapDelay - minTapDelay) + minTapDelay);
                    lastClickTime = System.currentTimeMillis();
                }
            } else {
                if (released && Minecraft.getMinecraft().currentScreen == null) {
                    KeyBinding.setKeyBindState(Keyboard.KEY_W, true);
                    KeyBinding.onTick(Keyboard.KEY_W);
                }
            }
        } else {
            target = null;
        }
    }

    private Entity checkTarget(float dist, int FOV) {
        List<Entity> entities = Minecraft.getMinecraft().world.loadedEntityList;
        Vec3d playerLook = Minecraft.getMinecraft().getRenderViewEntity().getLook(1);
        double playerPitch = playerLook.y;
        double playerYaw = playerLook.x;
        Vec3d playerPos = Minecraft.getMinecraft().getRenderViewEntity().getPositionVector();

        for (Entity entity : entities) {
            if (entity.equals(Minecraft.getMinecraft().player)) {
                continue;
            }

            Vec3d targetPos = entity.getPositionVector();
            Vec3d toTarget = targetPos.subtract(playerPos);

            double angleX = Math.toDegrees(Math.atan2(toTarget.z, toTarget.x)) - 90.0;
            double angleY = Math.toDegrees(Math.atan2(Math.sqrt(toTarget.x * toTarget.x + toTarget.z * toTarget.z), toTarget.y)) - 90.0;

            double deltaX = Math.abs(playerYaw - angleX);
            double deltaY = Math.abs(playerPitch - angleY);

            double distanceSquared = toTarget.lengthSquared();
            double distance = Math.sqrt(distanceSquared);

            if (deltaX < FOV && deltaY < FOV && distance <= dist) {
                return entity;
            }
        }

        return null;
    }

    public void setMode(String mode) {
        switch (mode.toLowerCase()) {
            case "legit":
                FOV = 45;
                break;
            case "combooneall":
                FOV = 90;
                break;
            default:
                FOV = 45;
                break;
        }
        this.mode = mode.toLowerCase();
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Mode set to: " + mode));
    }

    public String getCurrentMode() {
        return mode;
    }
}
