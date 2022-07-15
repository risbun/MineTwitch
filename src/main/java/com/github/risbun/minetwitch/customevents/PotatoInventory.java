package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Objects;

public class PotatoInventory implements CustomEvent {
    @Override
    public boolean run() {

        for (Player p : MainClass.getPlayers()){
            Inventory inventory = p.getInventory();

            for (int i = 0; i < 45; i++){
                ItemStack item = inventory.getItem(i);

                if(item == null) continue;

                ItemMeta itemMeta = item.getItemMeta();

                itemMeta.setLore(Collections.singletonList(item.getType().toString()));

                item.setItemMeta(itemMeta);

                item.setType(Material.POTATO);
            }
        }

        return true;
    }

    @Override
    public void revert(){
        for (Player p : MainClass.getPlayers()){
            Inventory inventory = p.getInventory();

            for (int i = 0; i < 45; i++){
                ItemStack item = inventory.getItem(i);

                if(item == null) continue;

                ItemMeta itemMeta = item.getItemMeta();

                if(!itemMeta.hasLore()) continue;

                Material type = Material.matchMaterial(Objects.requireNonNull(itemMeta.getLore()).get(0));

                item.setItemMeta(itemMeta);

                item.setType(type == null ? Material.AIR : type);
                item.setLore(null);
            }
        }
    }
}
