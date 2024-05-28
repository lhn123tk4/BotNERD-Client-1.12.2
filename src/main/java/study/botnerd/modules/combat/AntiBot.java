package study.botnerd.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

import java.util.ArrayList;
import java.util.List;

public class AntiBot extends Module {

    private List<EntityPlayer> players = new ArrayList<>();

    public AntiBot() {
        super("AntiBot", "Anti Bots", Category.Combat);
    }

    public void onUpdate() {
        if (mc.world == null || mc.player == null) return;

        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityPlayer && !isRealPlayer((EntityPlayer) entity)) {
                players.remove(entity);
            }
        }

        for (EntityPlayer player : players) {
            mc.playerController.attackEntity(mc.player, player);
        }
    }

    private boolean isRealPlayer(EntityPlayer player) {
        // Kiểm tra xem người chơi có tên không
        if (player.getName().isEmpty()) {
            return false;
        }

        if (player.motionX != 0.0 || player.motionY != 0.0 || player.motionZ != 0.0) {
            return true;
        }

        if (!player.getHeldItemMainhand().isEmpty()) {
            return true;
        }

        if (player.onGround) {
            return true;
        }

        return false;
    }
}
