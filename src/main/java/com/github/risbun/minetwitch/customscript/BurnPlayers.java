package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BurnPlayers implements CustomScript {

    @Override
    public boolean run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setFireTicks(100);
        }
        return false;
    }

    @Override
    public void revert() {

    }
}
