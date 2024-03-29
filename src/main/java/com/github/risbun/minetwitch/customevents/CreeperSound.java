package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CreeperSound implements CustomEvent {
    @Override
    public boolean run() {
        for (Player p : MainClass.getPlayers()) {
            Vector inverseDirectionVec = p.getLocation().getDirection().normalize().multiply(-2);
            p.playSound(p.getLocation().add(inverseDirectionVec), Sound.ENTITY_CREEPER_PRIMED, 1, 1);
        }
        return false;
    }
}
