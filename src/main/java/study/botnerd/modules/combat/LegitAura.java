package study.botnerd.modules.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import study.botnerd.Main;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;
import study.botnerd.gui.clickgui.Setting;

import java.util.Comparator;
import java.util.List;

public class LegitAura extends Module {

    private final Setting rangeSetting = new Setting("Range", this, 8.0, 1.0, 20.0, false);

    public LegitAura() {
        super("LegitAura", "Auto attack nearby entities", Category.Combat);
        Main.instance.settingsManager.rSetting(rangeSetting);
    }

    public void onUpdate() {
        if (!Main.instance.settingsManager.getSettingByName(this, "Range").getValBoolean() || mc.player == null) return;

        EntityLivingBase target = findTarget();

        if (target != null) {
            attackTarget(target);
        }
    }

    private EntityLivingBase findTarget() {
        List<EntityLivingBase> targets = mc.world.getEntitiesWithinAABB(EntityLivingBase.class,
                new AxisAlignedBB(
                        mc.player.posX - Main.instance.settingsManager.getSettingByName(this, "Range").getValDouble(),
                        mc.player.posY - Main.instance.settingsManager.getSettingByName(this, "Range").getValDouble(),
                        mc.player.posZ - Main.instance.settingsManager.getSettingByName(this, "Range").getValDouble(),
                        mc.player.posX + Main.instance.settingsManager.getSettingByName(this, "Range").getValDouble(),
                        mc.player.posY + Main.instance.settingsManager.getSettingByName(this, "Range").getValDouble(),
                        mc.player.posZ + Main.instance.settingsManager.getSettingByName(this, "Range").getValDouble()));

        targets.removeIf(entity -> entity.isDead || entity == mc.player || entity instanceof EntityPlayerSP);

        EntityPlayerSP player = mc.player;

        targets.sort(Comparator.comparingDouble(target -> player.getDistanceSq(target)));

        return targets.isEmpty() ? null : targets.get(0);
    }

    private void attackTarget(EntityLivingBase target) {
        // Kiểm tra xem người chơi có thể tấn công được không
        if (mc.player.getCooledAttackStrength(0) >= 1) {
            // Tấn công nếu attack cooldown đã hoàn thành
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}
