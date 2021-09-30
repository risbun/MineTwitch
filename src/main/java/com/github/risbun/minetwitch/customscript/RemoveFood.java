package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.Main;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.entity.Player;

public class RemoveFood implements CustomScript {
    @Override
    public boolean run() {
        for (Player p : Main.GetPlayers()) {
            p.setFoodLevel(0);
        }
        return false;
    }
}
