package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.risbun.minetwitch.MainClass.plugin;

public class ItemRain implements CustomEvent {

    int taskIndex;
    float offset = 20;

    Random random = new Random();
    List<Material> items;

    @Override
    public boolean run() {
        TextComponent comp = Component.text("Items have started falling from the sky!")
                .color(TextColor.color(0, 255, 0));
        MainClass.announceAll(comp);

        items = new ArrayList<>();
        for(Material mat : Material.values()){
            if(mat.isItem()) items.add(mat);
        }

        taskIndex = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
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
        TextComponent comp = Component.text("Items have stopped falling from the sky!")
                .color(TextColor.color(255, 0, 0));
        MainClass.announceAll(comp);

        Bukkit.getScheduler().cancelTask(taskIndex);
    }
}
