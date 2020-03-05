package se.laxmine.challonge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener
{
    /*
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

    }
    */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Type /ch to start Challonge. First time? Go to /plugins/Challonge/config.yml and setup the plugin");
    }
}