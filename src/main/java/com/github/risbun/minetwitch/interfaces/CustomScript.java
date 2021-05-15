package com.github.risbun.minetwitch.interfaces;

import org.bukkit.event.Listener;

public interface CustomScript extends Listener {
    boolean run();
    void revert();
}

