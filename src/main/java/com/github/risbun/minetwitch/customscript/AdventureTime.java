package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.Main;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class AdventureTime implements CustomScript {
    @Override
    public boolean run() {
        for (Player p : Main.GetPlayers()) {
            p.setGameMode(GameMode.ADVENTURE);
        }
        return true;
    }

    @Override
    public void revert() {
        for (Player p : Main.GetPlayers()) {
            if(p.getGameMode().equals(GameMode.ADVENTURE)) p.setGameMode(GameMode.SURVIVAL);
        }
    }
}
