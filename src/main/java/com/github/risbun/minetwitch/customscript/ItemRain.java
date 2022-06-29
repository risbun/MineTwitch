package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemRain implements CustomEvent {

    int taskIndex;
    float offset = 20;

    Random random = new Random();
    List<Material> items;

    @Override
    public AnnounceLevel getAnnounceLevel() {
        return AnnounceLevel.Both;
    }

    @Override
    public void announceStart() {
        Component comp = Component.text("Items have started falling from the sky!")
                .color(TextColor.color(0, 255, 0));
        MainClass.announceAll(comp);
    }

    @Override
    public boolean run() {
        items = new ArrayList<>();
        for(Material mat : Material.values()){
            if(mat.isItem()) items.add(mat);
        }

        taskIndex = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.p, () -> {
            for(Player p : MainClass.getPlayers()){
                Location l = p.getLocation();
                l.setY(255);
                l.add((random.nextFloat() * offset) - offset / 2, 0, (random.nextFloat() * offset) - offset / 2);

                ItemStack item = new ItemStack(items.get(random.nextInt(items.size())));
                p.getWorld().dropItem(l, item);
            }
        }, 0L, 2L);

        return true;
    }

    @Override
    public void revert() {
        Bukkit.getScheduler().cancelTask(taskIndex);
    }

    @Override
    public void announceEnd() {
        Component comp = Component.text("Items have stopped falling from the sky!")
                .color(TextColor.color(255, 0, 0));
        MainClass.announceAll(comp);
    }
}