package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class PumpkinOnHead implements CustomEvent {

    @Override
    public boolean run() {
        for(Player p : MainClass.getPlayers()){
            PlayerInventory inventory = p.getInventory();

            ItemStack helmet = inventory.getHelmet();

            if(helmet == null){
                helmet = new ItemStack(Material.AIR);
            }

            ItemMeta meta = helmet.getItemMeta();

            meta.setLore(Collections.singletonList(helmet.getType().toString()));
            helmet.setItemMeta(meta);

            helmet.setType(Material.CARVED_PUMPKIN);
            helmet.addEnchantment(Enchantment.BINDING_CURSE, 1);
        }

        return true;
    }

    @Override
    public void revert() {
        for(Player p : MainClass.getPlayers()){
            PlayerInventory iv = p.getInventory();
            ItemStack helmet = iv.getHelmet();

            if(helmet == null || helmet.getLore() == null) return;

            Material type = Material.matchMaterial(helmet.getLore().get(0));

            if(type == null) return;

            helmet.setType(type);
            helmet.removeEnchantment(Enchantment.BINDING_CURSE);
        }
    }
}
