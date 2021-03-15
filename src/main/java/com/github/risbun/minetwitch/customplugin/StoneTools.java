package com.github.risbun.minetwitch.customplugin;

import com.github.risbun.minetwitch.interfaces.CustomPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class StoneTools implements CustomPlugin {
    @Override
    public boolean run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (ItemStack i : p.getInventory()) {
                if (i == null) continue;
                String yes = i.getType().getKey().toString();
                if (yes.contains("sword") || yes.contains("shovel") || yes.contains("axe")) {
                    String type = yes.split(":")[1].split("_")[1];
                    Material change = Material.getMaterial("STONE_" + type.toUpperCase());
                    i.setType(Objects.requireNonNull(change));
                }
            }
        }

        return false;
    }

    @Override
    public void revert() {

    }
}
