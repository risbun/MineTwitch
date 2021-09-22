package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.github.risbun.minetwitch.Main.p;

public class NoInteraction implements CustomScript {

    @Override
    public AnnounceLevel getAnnounceLevel() {
        return null;
    }

    @Override
    public void announceStart() {

    }

    @Override
    public boolean run() {
        p.getServer().getPluginManager().registerEvents(this, p);
        return true;
    }

    @Override
    public void revert() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void announceEnd() {

    }

    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent e){
        e.setCancelled(true);
    }
}
