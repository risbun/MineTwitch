package com.github.risbun.minetwitch.customplugin;

import com.github.risbun.minetwitch.interfaces.CustomPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class AdventureTime implements CustomPlugin {

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
