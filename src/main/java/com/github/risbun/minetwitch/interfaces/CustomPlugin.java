package com.github.risbun.minetwitch.interfaces;

import org.bukkit.event.Listener;

public interface CustomPlugin extends Listener {
    boolean run();
    void revert();
}

