package study.botnerd.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

public class Velocity extends Module {

    private int jumped = 0;

    public Velocity() {
        super("Velocity", "Anti Knockback", Category.Combat);
    }

    public void onUpdate() {
        if (Minecraft.getMinecraft().currentScreen != null) return;
    }

    public void onLivingUpdate() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.hurtTime == 9) {
            if (++jumped % 2 == 0 && mc.player.onGround && mc.player.isSprinting() && mc.currentScreen == null) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1, mc.player.posZ, true));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                mc.player.swingArm(EnumHand.MAIN_HAND);
                jumped = 0; 
            }
        } else {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), GameSettings.isKeyDown(mc.gameSettings.keyBindJump));
        }
    }
}
