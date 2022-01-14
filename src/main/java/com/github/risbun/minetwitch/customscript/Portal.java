package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Portal implements CustomEvent {
    @Override
    public boolean run() {
        for (Player p : MainClass.getPlayers()){
            World w = p.getWorld();

            for (int x = 0; x < 4; x++){
                for (int y = 0; y < 5; y++){
                    var pos = p.getLocation().add(new Vector(x-2,y-1,0));
                    if(x % 3 == 0 || y % 4 == 0){
                        w.setType(pos, Material.OBSIDIAN);
                    }else{
                        w.setType(pos, Material.NETHER_PORTAL);
                    }
                }
            }

            w.setType(p.getLocation(), Material.FIRE);
        }
        return false;
    }
}
