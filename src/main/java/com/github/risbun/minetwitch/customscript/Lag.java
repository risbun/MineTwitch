package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.Main;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Lag implements CustomScript {

    int taskIndex;
    HashMap<Player, Location> playerLocation = new HashMap<>();

    @Override
    public boolean run() {

        playerLocation.clear();

        taskIndex = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.p, () -> {
            for(Player p : playerLocation.keySet()){
                Location oldPLocation = playerLocation.get(p);
                Location pLocation = p.getLocation();

                Vector vec = pLocation.toVector().subtract(oldPLocation.toVector()).divide(new Vector(4,4,4));

                oldPLocation.setYaw(pLocation.getYaw());
                oldPLocation.setPitch(pLocation.getPitch());

                Vector vel = p.getVelocity();
                p.teleport(pLocation.subtract(vec));
                p.setVelocity(vel);
            }
            playerLocation.clear();
            for(Player p : Main.GetPlayers()){
                playerLocation.put(p, p.getLocation());
            }
        }, 0L, 20L);

        return true;
    }

    @Override
    public void revert() {
        Bukkit.getScheduler().cancelTask(taskIndex);
    }
}
