package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.entity.Player;

public class dropEverything implements CustomEvent {
    @Override
    public boolean run() {

        for (Player p : MainClass.getPlayers()){
            p.dropItem(true);

            p.getInventory().getContents();
        }

        return false;
    }
}
