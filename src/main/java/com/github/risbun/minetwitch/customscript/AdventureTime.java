package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class AdventureTime implements CustomScript {

    @Override
    public AnnounceLevel getAnnounceLevel() {
        return null;
    }

    @Override
    public void announceStart() {

    }

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
            if(p.getGameMode().equals(GameMode.ADVENTURE)) p.setGameMode(GameMode.SURVIVAL);
        }
    }

    @Override
    public void announceEnd() {

    }
}
