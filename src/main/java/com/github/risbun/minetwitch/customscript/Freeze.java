package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

import static com.github.risbun.minetwitch.Main.p;

public class Freeze implements CustomScript {
    @Override
    public boolean run() {
        p.getServer().getPluginManager().registerEvents(this, p);
        return true;
    }

    @Override
    public void revert() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        e.setCancelled(true);
    }
}
