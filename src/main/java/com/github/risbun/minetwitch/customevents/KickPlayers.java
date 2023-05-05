package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.entity.Player;

public class KickPlayers implements CustomEvent {
    @Override
    public boolean run() {
        for (Player p : MainClass.getPlayers()){
            p.kickPlayer("Your fate has been decided by twitch chat");
        }
        return false;
    }
}
