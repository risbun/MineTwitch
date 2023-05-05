package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.CustomEvent;
import com.github.risbun.minetwitch.MainClass;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.List;

public class RideClosest implements CustomEvent {
    @Override
    public boolean run() {
        for(Player p : MainClass.getPlayers()){
            List<Entity> entities = p.getNearbyEntities(64, 64, 64);

            Location playerLoc = p.getLocation();

            double dist = 64;
            Entity entity = null;
            for(Entity ent : entities){
                if(ent == p) continue;
                if(ent instanceof Player) continue;
                if(ent instanceof Item) continue;

                double testDist = ent.getLocation().distance(playerLoc);
                if(testDist < dist){
                    dist = testDist;
                    entity = ent;
                }
            }

            entities.add(entity);

            if(entity != null){
                p.teleport(entity);
                entity.addPassenger(p);
            }
        }
        return false;
    }
}
