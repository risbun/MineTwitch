package com.github.risbun.minetwitch.customscript;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.github.risbun.minetwitch.MainClass.p;

public class NoJumping implements CustomEvent {
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
    public void onPlayerJump(PlayerJumpEvent e){
        if(MainClass.shouldBeAffected(e.getPlayer())) e.setCancelled(true);
    }
}
