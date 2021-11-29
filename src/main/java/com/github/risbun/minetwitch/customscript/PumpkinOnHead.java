package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PumpkinOnHead implements CustomEvent {

    @Override
    public boolean run() {
        for(Player p : MainClass.GetPlayers()){
            ItemStack pumpkin = new ItemStack(Material.CARVED_PUMPKIN);
            pumpkin.addEnchantment(Enchantment.BINDING_CURSE, 1);

            p.getInventory().setHelmet(pumpkin);
        }

        return true;
    }

    @Override
    public void revert() {
        for(Player p : MainClass.GetPlayers()){
            PlayerInventory iv = p.getInventory();
            iv.setHelmet(null);
        }
    }
}
