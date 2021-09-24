package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ExplodeEntities implements CustomScript {
    @Override
    public AnnounceLevel getAnnounceLevel() {
        return null;
    }

    @Override
    public void announceStart() {

    }

    @Override
    public boolean run() {
        for (Player p : Bukkit.getOnlinePlayers()){
            Collection<Entity> nearbyEntites = p.getWorld().getNearbyEntities(p.getLocation(), 64, 64, 64);

            for(Entity entity: nearbyEntites){
                if(entity instanceof Player) continue;

                entity.getLocation().createExplosion(6.5f);
            }
        }
        return false;
    }

    @Override
    public void revert() {

    }

    @Override
    public void announceEnd() {

    }
}
