package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.Main;
import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BurnPlayers implements CustomScript {

    @Override
    public AnnounceLevel getAnnounceLevel() {
        return AnnounceLevel.Start;
    }

    @Override
    public void announceStart() {
        Component comp = Component.text("BURN BABY BURN!")
                .color(TextColor.color(255, 152, 0));
        Main.announceAll(comp);
    }

    @Override
    public boolean run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setFireTicks(100);
        }
        return false;
    }

    @Override
    public void revert() {

    }

    @Override
    public void announceEnd() {

    }
}
