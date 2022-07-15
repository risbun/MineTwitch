package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.entity.Player;

public class RemoveFood implements CustomEvent {
    @Override
    public boolean run() {
        for (Player p : MainClass.getPlayers()) {
            p.setFoodLevel(0);
        }
        return false;
    }
}
