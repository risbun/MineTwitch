package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockDropItemEvent;

import java.util.Random;

import static com.github.risbun.minetwitch.MainClass.plugin;

public class DropsMultiplied implements CustomEvent {

    Random random = new Random();
    int maxMultiplicationCount = 10;

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
    public void onPlayerInteraction(BlockDropItemEvent e){
        for(Item i : e.getItems()){
            i.getItemStack().setAmount(random.nextInt(maxMultiplicationCount));
        }
    }
}
