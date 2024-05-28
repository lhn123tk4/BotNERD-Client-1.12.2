package study.botnerd.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;
import study.botnerd.Main;
import study.botnerd.gui.clickgui.Setting;

import java.util.List;

public class AimBot extends Module {

    private boolean AimBotEnabled = false;

    public AimBot() {
        super("AimBot", "Automatically aims at entities", Category.Combat);

        // Add settings
        Main.instance.settingsManager.rSetting(new Setting("Range", this, 4.4, 0.0, 10.0, false));
        Main.instance.settingsManager.rSetting(new Setting("FOV", this, 180.0, 0.0, 180.0, false));
    }

    public AimBot(String name, String description, Category category) {
        super(name, description, category);
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.player == null || mc.world == null) {
            return;
        }

        if (!AimBotEnabled) {
            return;
        }

        Entity targetEntity = getTargetEntity(mc);

        if (targetEntity != null) {
            aimAtTarget(mc, targetEntity);
        }
    }

    private Entity getTargetEntity(Minecraft mc) {
        double range = Main.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
        double fov = Main.instance.settingsManager.getSettingByName(this, "FOV").getValDouble();

        List<Entity> entities = mc.world.loadedEntityList;
        for (Entity entity : entities) {
            if (entity instanceof EntityLivingBase && entity != mc.player && mc.player.getDistance(entity) <= range) {
                if (isInFOV(mc.player, entity, fov)) {
                    return entity;
                }
            }
        }
        return null;
    }

    private boolean isInFOV(EntityPlayer player, Entity target, double fov) {
        float yawDifference = Math.abs(getYawDifference(player, target));
        float pitchDifference = Math.abs(getPitchDifference(player, target));
        return yawDifference <= fov / 2 && pitchDifference <= fov / 2;
    }

    private void aimAtTarget(Minecraft mc, Entity target) {
        double deltaX = target.posX - mc.player.posX;
        double deltaY = (target.posY + target.getEyeHeight()) - (mc.player.posY + mc.player.getEyeHeight());
        double deltaZ = target.posZ - mc.player.posZ;
        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        float yaw = (float) Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90.0F;
        float pitch = (float) Math.toDegrees(-Math.atan2(deltaY, distance));

        mc.player.rotationYaw = yaw;
        mc.player.rotationPitch = pitch;
    }

    private float getYawDifference(EntityPlayer player, Entity target) {
        double deltaX = target.posX - player.posX;
        double deltaZ = target.posZ - player.posZ;
        return (float) Math.toDegrees(Math.atan2(deltaZ, deltaX)) - player.rotationYaw;
    }

    private float getPitchDifference(EntityPlayer player, Entity target) {
        double deltaX = target.posX - player.posX;
        double deltaY = (target.posY + target.getEyeHeight()) - (player.posY + player.getEyeHeight());
        double deltaZ = target.posZ - player.posZ;
        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        return (float) Math.toDegrees(-Math.atan2(deltaY, distance)) - player.rotationPitch;
    }
}
