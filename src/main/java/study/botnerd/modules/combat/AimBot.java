package study.botnerd.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

import java.util.List;

public class AimBot extends Module {

    private final Minecraft mc = Minecraft.getMinecraft();

    public AimBot() {
        super("AimBot", "Test", Category.Combat);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (this.isToggled() && mc.player != null && mc.world != null && mc.currentScreen == null) {
            Entity target = findClosestTarget();
            if (target != null) {
                aimAtTarget(target);
            }
        }
    }

    private Entity findClosestTarget() {
        List<EntityPlayer> players = mc.world.playerEntities;
        Entity closestTarget = null;
        double closestDistance = Double.MAX_VALUE;

        for (EntityPlayer player : players) {
            if (player == mc.player) continue; // Skip the player themselves
            double distance = mc.player.getDistance(player);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestTarget = player;
            }
        }

        return closestTarget;
    }

    private void aimAtTarget(Entity target) {
        double deltaX = target.posX - mc.player.posX;
        double deltaY = target.posY + target.getEyeHeight() - mc.player.posY - mc.player.getEyeHeight();
        double deltaZ = target.posZ - mc.player.posZ;

        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float yaw = (float) (MathHelper.atan2(deltaZ, deltaX) * (180 / Math.PI)) - 90;
        float pitch = (float) -(MathHelper.atan2(deltaY, distance) * (180 / Math.PI));

        mc.player.rotationYaw = yaw;
        mc.player.rotationPitch = pitch;
    }
}
