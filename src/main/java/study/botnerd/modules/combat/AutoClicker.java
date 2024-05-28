package study.botnerd.modules.combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;
import study.botnerd.Main;
import study.botnerd.gui.clickgui.Setting;

public class AutoClicker extends Module {

    private long lastClickTime = 0L;
    private long delay;
    private boolean held;

    public AutoClicker() {
        super("AutoClicker", "Automatically clicks for you", Category.Combat);

        Main.instance.settingsManager.rSetting(new Setting("Min CPS", this, 8.0, 1.0, 20.0, false));
        Main.instance.settingsManager.rSetting(new Setting("Max CPS", this, 14.0, 1.0, 20.0, false));
        Main.instance.settingsManager.rSetting(new Setting("Require Item", this, true));
        Main.instance.settingsManager.rSetting(new Setting("Held", this, true));

        initializeDelay();
    }

    private void initializeDelay() {
        float minCPS = getMinCPS();
        float maxCPS = getMaxCPS();
        delay = 1000L / (long) (Math.random() * (maxCPS - minCPS) + minCPS);
    }

    public void onTick() {
        if (isItemWhitelisted() && canClick()) {
            if (!held) {
                KeyBinding.setKeyBindState(-100, true);
                KeyBinding.onTick(-100);
                held = true;
                lastClickTime = System.currentTimeMillis();
            } else {
                if (System.currentTimeMillis() - lastClickTime >= delay) {
                    KeyBinding.setKeyBindState(-100, false);
                    held = false;
                    delay = 1000L / (long) (Math.random() * (getMaxCPS() - getMinCPS()) + getMinCPS());
                }
            }
        }
    }

    private boolean isItemWhitelisted() {
        // Logic to check if the current item is in the whitelist
        return true; // Assume always true for now
    }

    private boolean canClick() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen != null) {
            return false;
        }
        if (mc.player == null || mc.player.isDead) {
            return false;
        }
        if (mc.gameSettings.keyBindAttack.isKeyDown()) {
            return false;
        }
        return true;
    }

    private float getMinCPS() {
        return (float) Main.instance.settingsManager.getSettingByName(this, "Min CPS").getValDouble();
    }

    private float getMaxCPS() {
        return (float) Main.instance.settingsManager.getSettingByName(this, "Max CPS").getValDouble();
    }

    public void increaseMinCPS() {
        float minCPS = getMinCPS() + 0.1F;
        Main.instance.settingsManager.getSettingByName(this, "Min CPS").setValDouble(minCPS);
    }

    public void decreaseMinCPS() {
        float minCPS = getMinCPS();
        if (minCPS > 0.1F) {
            minCPS -= 0.1F;
            Main.instance.settingsManager.getSettingByName(this, "Min CPS").setValDouble(minCPS);
        }
    }

    public void increaseMaxCPS() {
        float maxCPS = getMaxCPS() + 0.1F;
        Main.instance.settingsManager.getSettingByName(this, "Max CPS").setValDouble(maxCPS);
    }

    public void decreaseMaxCPS() {
        float maxCPS = getMaxCPS();
        if (maxCPS > 0.1F) {
            maxCPS -= 0.1F;
            Main.instance.settingsManager.getSettingByName(this, "Max CPS").setValDouble(maxCPS);
        }
    }
}
