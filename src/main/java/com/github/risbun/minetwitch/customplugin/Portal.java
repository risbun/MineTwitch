package com.github.risbun.minetwitch.customplugin;

import com.github.risbun.minetwitch.commands.CommandParser;
import com.github.risbun.minetwitch.interfaces.CustomPlugin;

public class Portal implements CustomPlugin {
    @Override
    public void run() {
        CommandParser.sendAllCommand("fill ~-1 ~-1 ~ ~2 ~3 ~ minecraft:obsidian");
        CommandParser.sendAllCommand("fill ~1 ~ ~ ~ ~2 ~ minecraft:air");
        CommandParser.sendAllCommand("setblock ~ ~ ~ fire");
    }

    @Override
    public void revert() {

    }
}
