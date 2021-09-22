package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PumpkinOnHead implements CustomScript {

    @Override
    public AnnounceLevel getAnnounceLevel() {
        return null;
    }

    @Override
    public void announceStart() {

    }

    @Override
    public boolean run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            ItemStack pumpkin = new ItemStack(Material.CARVED_PUMPKIN);
            pumpkin.addEnchantment(Enchantment.BINDING_CURSE, 1);

            p.getInventory().setHelmet(pumpkin);
        }

        return true;
    }

    @Override
    public void revert() {
        for(Player p : Bukkit.getOnlinePlayers()){
            PlayerInventory iv = p.getInventory();
            iv.setHelmet(null);
        }
    }

    @Override
    public void announceEnd() {

    }
}
