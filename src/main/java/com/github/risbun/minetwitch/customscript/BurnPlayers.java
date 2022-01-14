package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class BurnPlayers implements CustomEvent {

    @Override
    public AnnounceLevel getAnnounceLevel() {
        return AnnounceLevel.Start;
    }

    @Override
    public void announceStart() {
        Component comp = Component.text("BURN BABY BURN!")
                .color(TextColor.color(255, 152, 0));
        MainClass.announceAll(comp);
    }

    @Override
    public boolean run() {
        for (Player p : MainClass.getPlayers()) {
            p.setFireTicks(100);
        }
        return false;
    }
}
