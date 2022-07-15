package com.github.risbun.minetwitch.commands;

import com.github.risbun.minetwitch.EventManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(String.format("Trying to run command: [%s]", String.join(" ", args)));
        EventManager.Apply("test", String.join(" ", args));
        return true;
    }
}
