package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class PotatoInventory implements CustomEvent {
    @Override
    public boolean run() {

        for (Player p : MainClass.getPlayers()){
            Inventory inventory = p.getInventory();

            for (int i = 0; i < 45; i++){
                ItemStack item = inventory.getItem(i);

                if(item == null) continue;



                ItemMeta itemMeta = item.getItemMeta();

                itemMeta.lore(Collections.singletonList(Component.text(String.valueOf(item.getType()))));

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

                if(itemMeta.lore() == null) continue;

                Material type = Material.matchMaterial(((TextComponent) itemMeta.lore().get(0)).content());

                item.setItemMeta(itemMeta);

                item.setType(type);
                item.lore(null);
            }
        }
    }
}
