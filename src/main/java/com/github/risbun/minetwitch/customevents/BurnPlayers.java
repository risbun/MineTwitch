package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BurnPlayers implements CustomEvent {
    @Override
    public boolean run() {
        MainClass.announceAll(ChatColor.DARK_RED + "BURN BABY BURN");

        for (Player p : MainClass.getPlayers()) {
            p.setFireTicks(100);
        }
        return false;
    }
}
