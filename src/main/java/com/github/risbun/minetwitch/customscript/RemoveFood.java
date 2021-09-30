package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.Main;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.entity.Player;

public class RemoveFood implements CustomEvent {
    @Override
    public boolean run() {
        for (Player p : Main.GetPlayers()) {
            p.setFoodLevel(0);
        }
        return false;
    }
}
