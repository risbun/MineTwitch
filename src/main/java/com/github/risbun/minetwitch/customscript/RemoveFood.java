package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RemoveFood implements CustomScript {
    @Override
    public boolean run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setFoodLevel(0);
        }
        return false;
    }

    @Override
    public void revert() {

    }
}
