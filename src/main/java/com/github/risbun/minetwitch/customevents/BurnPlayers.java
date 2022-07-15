package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class BurnPlayers implements CustomEvent {
    @Override
    public boolean run() {
        TextComponent comp = Component.text("BURN BABY BURN!")
                .color(TextColor.color(255, 152, 0));
        MainClass.announceAll(comp);

        for (Player p : MainClass.getPlayers()) {
            p.setFireTicks(100);
        }
        return false;
    }
}
