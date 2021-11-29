package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;

import static com.github.risbun.minetwitch.MainClass.p;

public class NoDrops implements CustomEvent {
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
    public void onPlayerInteraction(BlockBreakEvent e){
        e.setDropItems(false);
    }
}
