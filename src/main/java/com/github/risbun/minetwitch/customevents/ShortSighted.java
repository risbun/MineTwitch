package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.World;

import java.util.HashMap;

public class ShortSighted implements CustomEvent {

    HashMap<World, Integer> yep = new HashMap<>();

    @Override
    public boolean run() {
        yep.clear();
        for(World w : MainClass.plugin.getServer().getWorlds()){
            yep.put(w, w.getViewDistance());
            w.setViewDistance(2);
        }

        return true;
    }

    @Override
    public void revert() {
        for(World w : yep.keySet()){
            w.setViewDistance(yep.get(w));
        }
    }
}
