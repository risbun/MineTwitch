package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class KickPlayers implements CustomEvent {
    @Override
    public boolean run() {
        for (Player p : MainClass.getPlayers()){
            p.kick(Component.text("Your fate has been decided by twitch chat"));
        }
        return false;
    }
}
