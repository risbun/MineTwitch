package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;

import static com.github.risbun.minetwitch.MainClass.plugin;

public class NoDrops implements CustomEvent {
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
    public void onPlayerInteraction(BlockBreakEvent e){
        e.setDropItems(false);
    }
}
