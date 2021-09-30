package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockDropItemEvent;

import java.util.Random;

import static com.github.risbun.minetwitch.Main.p;

public class DropsMultiplied implements CustomEvent {

    Random random = new Random();
    int maxMultiplicationCount = 10;

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
    public void onPlayerInteraction(BlockDropItemEvent e){
        for(Item i : e.getItems()){
            i.getItemStack().setAmount(random.nextInt(maxMultiplicationCount));
        }
    }
}
