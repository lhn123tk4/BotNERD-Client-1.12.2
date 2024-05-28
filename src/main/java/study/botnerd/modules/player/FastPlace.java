package study.botnerd.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

public class FastPlace extends Module {
    private boolean isEnabled = false;
    private boolean shouldPlace = false;

    public FastPlace() {
        super("FastPlace", "Allows you to place blocks quickly.", Category.Player);
    }

    public void onUpdate() {
        if (!isEnabled) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player != null && mc.objectMouseOver != null && shouldPlace) {
            BlockPos blockPos = mc.objectMouseOver.getBlockPos();
            EnumFacing facing = mc.objectMouseOver.sideHit;

            mc.playerController.processRightClickBlock(mc.player, mc.world, blockPos, facing, mc.objectMouseOver.hitVec, EnumHand.MAIN_HAND);

            shouldPlace = false;
        }
    }

    public void startPlacing() {
        shouldPlace = true;
    }

    public void stopPlacing() {
        shouldPlace = false;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
}
