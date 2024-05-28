package study.botnerd.modules.player;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.KeyEvent;

import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

public class Egale extends Module {
    private Robot robot;

    public Egale() {
        super("Egale", "Keep Shift pressed continuously", Category.Player);
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        robot.keyPress(KeyEvent.VK_SHIFT);
    }

    @Override
    public void onDisable() {
        // Thả phím Shift ra
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }
}
