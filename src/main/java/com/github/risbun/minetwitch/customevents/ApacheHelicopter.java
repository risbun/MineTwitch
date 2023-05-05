package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ApacheHelicopter implements CustomEvent {
    @Override
    public boolean run() {

        for (Player p : MainClass.getPlayers()){
            World w = p.getWorld();

            Bat bat = w.spawn(p.getLocation(), Bat.class);
            Skeleton skeleton = w.spawn(p.getLocation(), Skeleton.class);
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 120,0, true, false));

            bat.addPassenger(skeleton);
        }

        return false;
    }
}
