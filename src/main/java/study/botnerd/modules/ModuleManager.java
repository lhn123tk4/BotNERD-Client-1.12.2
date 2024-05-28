package study.botnerd.modules;

import java.util.ArrayList;

import study.botnerd.modules.combat.*;
import study.botnerd.modules.misc.FakeLag;
import study.botnerd.modules.movement.AntiWeb;
import study.botnerd.modules.movement.FastLadder;
import study.botnerd.modules.movement.Fly;
import study.botnerd.modules.movement.InvMove;
import study.botnerd.modules.movement.Parkour;
import study.botnerd.modules.player.Egale;
import study.botnerd.modules.player.FastPlace;
import study.botnerd.modules.render.*;
import study.botnerd.modules.movement.Sprint;
import study.botnerd.modules.player.AntiAFK;
import study.botnerd.modules.player.FastBreak;

public class ModuleManager {

    public ArrayList<Module> modules;

    public ModuleManager(){
        (modules = new ArrayList<Module>()).clear();
        this.modules.add(new ClickGui()); //sub sub components, custom font, animations
        this.modules.add(new Hud());
        this.modules.add(new Sprint());
        this.modules.add(new InvMove()); //F5
        this.modules.add(new Fly());
        this.modules.add(new Fullbright());
        this.modules.add(new FastLadder());
        this.modules.add(new AntiAFK());
        this.modules.add(new AntiWeb());
        this.modules.add(new FastBreak()); //fix
        this.modules.add(new Parkour());
        this.modules.add(new EnemyInfo());
        this.modules.add(new AutoClicker());
        this.modules.add(new AimBot());
        this.modules.add(new WTap());
        this.modules.add(new Velocity());
        this.modules.add(new AntiBot());
        this.modules.add(new FakeLag());
        this.modules.add(new CPSCounter());
        this.modules.add(new FastPlace());
        this.modules.add(new LegitAura());
        this.modules.add(new Egale());
    }
    public Module getModule(String name){
        Module mod;
        for(Module m : this.modules){
            if(m.getName().equalsIgnoreCase(name)){
                return m;
            }
        }
        return null;
    }
    public ArrayList<Module> getModuleList(){
        return this.modules;
    }
    public ArrayList<Module> getModulesInCategory(Category c){
        ArrayList<Module> mods = new ArrayList<Module>();
        for(Module m : this.modules){
            if(m.getCategory().name().equalsIgnoreCase(c.name())){
                mods.add(m);
            }
        }
        return mods;
    }
    
}
