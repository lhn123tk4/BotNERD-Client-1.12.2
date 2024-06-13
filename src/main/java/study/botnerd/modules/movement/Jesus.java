package study.botnerd.modules.movement;

import study.botnerd.modules.Category;
import study.botnerd.modules.Module;

public class Jesus extends Module {

    public Jesus() {
        super("Jesus", "Dolphin", Category.Movement);
    }

    public void onUpdate() {
        if(this.isToggled()) {
            if(mc.player.isInWater()) {
                mc.player.motionY += 0.04;
            }
        }
    }
}
