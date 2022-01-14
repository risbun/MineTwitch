package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
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

        taskIndex = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.p, () -> {
            for(Player p : MainClass.getPlayers()){
                Block b = p.getLocation().getBlock();
                Location summon = b.getLocation();
                summon.setY(255);
                summon.add((random.nextFloat() * offset) - offset / 2, 0, (random.nextFloat() * offset) - offset / 2);
                summon.add(new Vector(0.5f, 0, 0.5f));

                Material mat = blocks.get(random.nextInt(blocks.size()));
                p.getWorld().spawnFallingBlock(summon, mat.createBlockData());
            }
        }, 0L, 2L);

        return true;
    }

    @Override
    public void revert() {
        Bukkit.getScheduler().cancelTask(taskIndex);
    }
}
