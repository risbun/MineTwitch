package java.se.laxmine.challonge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandDisable implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Main.enabled) {
            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Disabling...");
            Main.twirk.disconnect();
            Main.challonge.unregister();
            Main.one.unregister();
            Main.two.unregister();
            Main.three.unregister();
            Bukkit.getScheduler().cancelTasks(Main.pulga);
            Main.enabled = false;
            Main.chooses.clear();
            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Disabled");
        } else {
            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " Not enabled, enable it with /chEnable");
        }
        return true;
    }
}
