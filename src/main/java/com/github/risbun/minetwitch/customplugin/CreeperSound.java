package com.github.risbun.minetwitch.customplugin;

import com.github.risbun.minetwitch.interfaces.CustomPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CreeperSound implements CustomPlugin {

    @Override
    public boolean run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Vector inverseDirectionVec = p.getLocation().getDirection().normalize().multiply(-2);
            p.playSound(p.getLocation().add(inverseDirectionVec), Sound.ENTITY_CREEPER_PRIMED, 1, 1);
        }
        return false;
    }

    @Override
    public void revert() {

    }
}
