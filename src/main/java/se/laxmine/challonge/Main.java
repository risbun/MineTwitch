package java.se.laxmine.challonge;

import com.gikk.twirk.Twirk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {
    public static Twirk twirk;
    public static Objective challonge;
    public static Team one;
    public static Team two;
    public static Team three;
    public static List<String> chooses = new ArrayList<>();
    public static List<String> globalVotes = new ArrayList<>();
    public static List<String> oneVotes = new ArrayList<>();
    public static List<String> twoVotes = new ArrayList<>();
    public static List<String> threeVotes = new ArrayList<>();
    public static List<String> chosen = new ArrayList<>();
    public static List<String> chosenActions = new ArrayList<>();
    public static Boolean votenow = false;
    public static Plugin pulga = null;
    public static Boolean enabled = false;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        pulga = this;
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Type /chEnable to start Challonge. First time? Go to /plugins/Challonge/config.yml and setup the plugin");
        this.getCommand("chEnable").setExecutor(new CommandEnable());
        this.getCommand("chDisable").setExecutor(new CommandDisable());
        this.saveDefaultConfig();
        config = this.getConfig();
    }

    @Override
    public void onDisable() {
        if(enabled) {
            twirk.disconnect();
            challonge.unregister();
            one.unregister();
            two.unregister();
            three.unregister();
            Bukkit.getScheduler().cancelTasks(pulga);
            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Disabled");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Type /chEnable to start Challonge. First time? Go to /plugins/Challonge/config.yml and setup the plugin");
    }
}
