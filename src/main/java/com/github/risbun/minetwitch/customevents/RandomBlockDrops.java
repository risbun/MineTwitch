package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.risbun.minetwitch.MainClass.plugin;

public class RandomBlockDrops implements CustomEvent {

    Random random = new Random();
    List<Material> items;

    @Override
    public boolean run() {
        items = new ArrayList<>();
        for(Material mat : Material.values()){
            if(mat.isItem()) items.add(mat);
        }

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        return true;
    }

    @Override
    public void revert() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerInteraction(BlockBreakEvent e){
        ItemStack item = new ItemStack(items.get(random.nextInt(items.size())));

        e.setDropItems(false);

        Location loc = e.getBlock().getLocation();
        loc.add(0.5,0.5,0.5);
        loc.getWorld().dropItemNaturally(loc, item);
    }
}
