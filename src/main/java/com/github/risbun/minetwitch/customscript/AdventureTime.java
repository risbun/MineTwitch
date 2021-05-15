package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class AdventureTime implements CustomScript {

    @Override
    public boolean run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setGameMode(GameMode.ADVENTURE);
        }
        return true;
    }

    @Override
    public void revert() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setGameMode(GameMode.SURVIVAL);
        }
    }
}
