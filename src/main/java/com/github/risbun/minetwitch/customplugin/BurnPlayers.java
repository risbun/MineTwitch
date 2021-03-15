package com.github.risbun.minetwitch.customplugin;

import com.github.risbun.minetwitch.interfaces.CustomPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class BurnPlayers implements CustomPlugin {
    @Override
    public boolean run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setFireTicks(100);
        }
        return false;
    }
}
