package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
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

            var helmet = inventory.getHelmet();

            if(helmet == null){
                helmet = new ItemStack(Material.AIR);
            }

            ItemMeta meta = helmet.getItemMeta();

            meta.lore(Collections.singletonList(Component.text(helmet.getType().toString())));
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
            var helmet = iv.getHelmet();

            if(helmet == null || helmet.lore() == null) return;

            var type = Material.matchMaterial(((TextComponent) helmet.lore().get(0)).content());

            if(type == null) return;

            helmet.setType(type);
            helmet.removeEnchantment(Enchantment.BINDING_CURSE);
        }
    }
}
