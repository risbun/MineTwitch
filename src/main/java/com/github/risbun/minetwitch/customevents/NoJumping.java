package com.github.risbun.minetwitch.customevents;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

import static com.github.risbun.minetwitch.MainClass.plugin;

public class NoJumping implements CustomEvent {
    @Override
    public boolean run() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        return true;
    }

    @Override
    public void revert() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent e){
        if(MainClass.shouldBeAffected(e.getPlayer())) e.setCancelled(true);
    }
}
