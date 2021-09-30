package com.github.risbun.minetwitch.customscript;

import com.github.risbun.minetwitch.parser.CommandParser;
import com.github.risbun.minetwitch.interfaces.CustomEvent;

public class Portal implements CustomEvent {
    @Override
    public boolean run() {
        CommandParser.sendAllCommand("fill ~-1 ~-1 ~ ~2 ~3 ~ minecraft:obsidian");
        CommandParser.sendAllCommand("fill ~1 ~ ~ ~ ~2 ~ minecraft:air");
        CommandParser.sendAllCommand("setblock ~ ~ ~ fire");

        return false;
    }
}
