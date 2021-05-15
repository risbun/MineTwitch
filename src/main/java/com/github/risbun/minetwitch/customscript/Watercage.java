package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.parser.CommandParser;
import com.github.risbun.minetwitch.interfaces.CustomScript;

public class Watercage implements CustomScript {

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
