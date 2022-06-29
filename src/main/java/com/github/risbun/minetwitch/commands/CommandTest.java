package com.github.risbun.minetwitch.commands;

import com.github.risbun.minetwitch.parser.CommandParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        String detshbiu = String.join(" ", args);

        sender.sendMessage(String.format("Trying to run command: [%s]", detshbiu));

        CommandParser.send("test", detshbiu);
        return true;
    }
}
