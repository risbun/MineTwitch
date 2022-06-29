package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Portal implements CustomEvent {
    @Override
    public boolean run() {
        for (Player p : MainClass.getPlayers()){
            for (int x = 0; x < 4; x++){
                for (int y = 0; y < 5; y++){
                    Location pos = p.getLocation().add(new Vector(x-2,y-1,0));
                    if(x % 3 == 0 || y % 4 == 0){
                        pos.getBlock().setType(Material.OBSIDIAN);
                    }else{
                        pos.getBlock().setType(Material.NETHER_PORTAL);
                    }
                }
            }

            p.getLocation().getBlock().setType(Material.FIRE);
        }
        return false;
    }
}
