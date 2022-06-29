package com.github.risbun.minetwitch.interfaces;

import com.github.risbun.minetwitch.enums.AnnounceLevel;
import org.bukkit.event.Listener;

public interface CustomEvent extends Listener {
    default AnnounceLevel getAnnounceLevel() {
        return null;
    }

    default void announceStart() {}
    boolean run();
    default void revert() {}
    default void announceEnd() {}

    default long delay() {
        return 600L;
    }
}

