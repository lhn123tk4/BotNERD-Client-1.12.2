package study.botnerd.modules.player;

import study.botnerd.modules.Category;
import study.botnerd.modules.Module;
import java.lang.reflect.Field;

public class AutoWalk extends Module {
    private Field pressedField;

    public AutoWalk() {
        super("AutoWalk", "test", Category.Player);

        try {
            pressedField = mc.gameSettings.keyBindForward.getClass().getDeclaredField("pressed");
            pressedField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void onUpdate() {
        if (this.isToggled()) {
            try {
                pressedField.set(mc.gameSettings.keyBindForward, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void onDisable() {
        try {
            pressedField.set(mc.gameSettings.keyBindForward, false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        super.onDisable();
    }
}
