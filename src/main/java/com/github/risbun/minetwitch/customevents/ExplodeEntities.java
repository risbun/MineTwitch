package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ExplodeEntities implements CustomEvent {
    @Override
    public boolean run() {
        for (Player p : MainClass.getPlayers()){
            Collection<Entity> nearbyEntites = p.getWorld().getNearbyEntities(p.getLocation(), 64, 64, 64);

            for(Entity entity: nearbyEntites){
                if(entity instanceof Player) continue;

                entity.getLocation().createExplosion(4f);
            }
        }
        return false;
    }
}
