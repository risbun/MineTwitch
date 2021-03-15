package com.github.risbun.minetwitch.customplugin;

import com.github.risbun.minetwitch.commands.CommandParser;
import com.github.risbun.minetwitch.interfaces.CustomPlugin;

public class Watercage implements CustomPlugin {
    @Override
    public boolean run() {
        CommandParser.sendAllCommand("fill ~2 ~2 ~2 ~-2 ~-2 ~-2 stone hollow");
        CommandParser.sendAllCommand("fill ~1 ~1 ~1 ~-1 ~-1 ~-1 water");
        return false;
    }

    @Override
    public void revert() {

    }
}
