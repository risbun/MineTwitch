package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.risbun.minetwitch.Main.p;

public class RandomDrops implements CustomScript {

    Random random = new Random();
    List<Material> items;

    @Override
    public AnnounceLevel getAnnounceLevel() {
        return null;
    }

    @Override
    public void announceStart() {

    }

    @Override
    public boolean run() {
        items = new ArrayList<>();
        for(Material mat : Material.values()){
            if(mat.isItem()) items.add(mat);
        }

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
    public void onPlayerInteraction(BlockBreakEvent e){
        ItemStack item = new ItemStack(items.get(random.nextInt(items.size())));

        e.setDropItems(false);

        Location loc = e.getBlock().getLocation();
        loc.add(0.5,0.5,0.5);
        loc.getWorld().dropItemNaturally(loc, item);
    }
}
