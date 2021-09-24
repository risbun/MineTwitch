package com.github.risbun.minetwitch.commands;

import com.github.risbun.minetwitch.parser.CommandParser;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(String.format("Trying to run command: [%s]", args[0]));
        CommandParser.runCustom(args[0]);
        return true;
    }
}
