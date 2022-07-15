package com.github.risbun.minetwitch;

import org.bukkit.event.Listener;

public interface CustomEvent extends Listener {
    boolean run();
    default void revert() {}

    default long delay() {
        return 600L;
    }
}

