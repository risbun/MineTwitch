package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;

public class SpiderJockey implements CustomEvent {
    @Override
    public boolean run() {
        for(Player p : MainClass.getPlayers()){
            Entity spider = p.getWorld().spawn(p.getLocation(), Spider.class);

            spider.addPassenger(p);
        }
        return false;
    }
}
