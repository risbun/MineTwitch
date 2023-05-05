package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.spigotmc.event.entity.EntityDismountEvent;

import static com.github.risbun.minetwitch.MainClass.plugin;
import static com.github.risbun.minetwitch.MainClass.shouldBeAffected;

public class SpiderJockey implements CustomEvent {
    @Override
    public boolean run() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        
        for(Player p : MainClass.getPlayers()){
            Entity spider = p.getWorld().spawn(p.getLocation(), Spider.class);

            spider.addPassenger(p);
        }
        return false;
    }

    @Override
    public void revert() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerInteraction(EntityDismountEvent e){
        if(!(e.getEntity() instanceof Player)) return;

        Player plr = (Player) e.getEntity();

        if(!shouldBeAffected(plr)) return;
        e.setCancelled(true);
    }
}
