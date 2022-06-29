package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.entity.Player;

public class DropEverything implements CustomEvent {
    @Override
    public boolean run() {

        for (Player p : MainClass.getPlayers()){
            for (int i = 0; i < 45; i++){
                var item = p.getInventory().getItem(i);
                if(item != null){
                    var item2 = p.getWorld().dropItem(p.getEyeLocation(), item);
                    item2.setPickupDelay(61);

                    item.setAmount(0);
                }
            }
        }

        for (Player p : MainClass.getPlayers()){
            p.updateInventory();
        }

        return false;
    }
}