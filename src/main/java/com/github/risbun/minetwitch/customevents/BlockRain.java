package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockRain implements CustomEvent {

    int taskIndex;
    float offset = 64;

    Random random = new Random();
    List<Material> blocks = new ArrayList<>();

    @Override
    public boolean run() {

        blocks.clear();
        for(Material mat : Material.values()){
            if(mat.isBlock() && mat.isSolid()) blocks.add(mat);
        }

        taskIndex = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.plugin, () -> {
            for(Player p : MainClass.getPlayers()){
                Block b = p.getLocation().getBlock();
                Location l = b.getLocation();
                l.add((random.nextFloat() * offset) - offset / 2, 0, (random.nextFloat() * offset) - offset / 2);
                l.add(new Vector(0.5f, 20, 0.5f));

                Material mat = blocks.get(random.nextInt(blocks.size()));
                p.getWorld().spawnFallingBlock(l, mat.createBlockData());
            }
        }, 0L, 2L);

        return true;
    }

    @Override
    public void revert() {
        Bukkit.getScheduler().cancelTask(taskIndex);
    }
}
