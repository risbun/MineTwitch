package com.github.risbun.minetwitch;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static com.github.risbun.minetwitch.Main.prefix;

public class CommandReload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.isOp()) {
            Main.p.reloadConfig();
            Main.config = Main.p.getConfig();
            sender.sendMessage(prefix + " Config reloaded");
        } else {
            sender.sendMessage(prefix + " You have to be OP to use this command");
        }
        return true;
    }
}
