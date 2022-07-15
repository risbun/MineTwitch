package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.EventManager;
import com.github.risbun.minetwitch.CustomEvent;

public class Watercage implements CustomEvent {

    @Override
    public boolean run() {
        EventManager.sendAllCommand("fill ~2 ~2 ~2 ~-2 ~-2 ~-2 stone hollow");
        EventManager.sendAllCommand("fill ~1 ~1 ~1 ~-1 ~-1 ~-1 water");
        return false;
    }
}
