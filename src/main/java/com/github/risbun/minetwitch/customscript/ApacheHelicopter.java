package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;

public class ApacheHelicopter implements CustomEvent {
    @Override
    public boolean run() {

        for (Player p : MainClass.getPlayers()){
            World w = p.getWorld();

            Bat bat = w.spawn(p.getLocation(), Bat.class);
            Skeleton skeleton = w.spawn(p.getLocation(), Skeleton.class);
            skeleton.setShouldBurnInDay(false);

            bat.addPassenger(skeleton);
        }

        return false;
    }
}
