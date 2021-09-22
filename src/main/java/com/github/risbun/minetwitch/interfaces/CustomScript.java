package com.github.risbun.minetwitch.interfaces;

import com.github.risbun.minetwitch.enums.AnnounceLevel;
import org.bukkit.event.Listener;

public interface CustomScript extends Listener {
    AnnounceLevel getAnnounceLevel();

    void announceStart();
    boolean run();
    void revert();
    void announceEnd();
}

