package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class StoneTools implements CustomScript {

    @Override
    public AnnounceLevel getAnnounceLevel() {
        return null;
    }

    @Override
    public void announceStart() {

    }

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

    @Override
    public void announceEnd() {

    }
}
