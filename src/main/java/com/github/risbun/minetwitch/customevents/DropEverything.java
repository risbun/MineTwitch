package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropEverything implements CustomEvent {
    @Override
    public boolean run() {
        for (Player p : MainClass.getPlayers()){
            for (int i = 0; i < 45; i++){
                ItemStack item = p.getInventory().getItem(i);
                if(item != null){
                    Item item2 = p.getWorld().dropItem(p.getEyeLocation(), item);
                    item2.setPickupDelay(61);

                    item.setAmount(0);
                }
            }
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
        }

        for (Player p : MainClass.getPlayers()){
            p.updateInventory();
        }

        return false;
    }
}
