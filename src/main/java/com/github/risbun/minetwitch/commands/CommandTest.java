package com.github.risbun.minetwitch.commands;

import com.github.risbun.minetwitch.interfaces.CustomPlugin;
import com.github.risbun.minetwitch.parser.CommandParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

import static com.github.risbun.minetwitch.Main.classLoader;
import static com.github.risbun.minetwitch.Main.p;
import static org.bukkit.Bukkit.getScheduler;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandParser.runCustom(args[0]);
        return true;
    }
}
