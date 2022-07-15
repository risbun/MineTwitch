package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.risbun.minetwitch.MainClass.plugin;

public class RandomMobLoot implements CustomEvent {

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
    public void onEntityDeath(EntityDeathEvent e){
        for (ItemStack item : e.getDrops()) {
            item.setType(items.get(random.nextInt(items.size())));
        }
    }
}
