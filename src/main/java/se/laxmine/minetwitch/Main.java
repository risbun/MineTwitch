package se.laxmine.minetwitch;

import com.github.twitch4j.TwitchClient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {
    static Objective minetwitch;
    static Scoreboard board;
    static List<String> chooses = new ArrayList<>();
    static List<String> globalVotes = new ArrayList<>();
    static List<String> votes = new ArrayList<>();
    static List<String> chosen = new ArrayList<>();
    static List<String> chosenActions = new ArrayList<>();
    static String activeCommand = null;
    static boolean votenow = false;
    static Plugin p = null;
    static boolean enabled = false;
    static FileConfiguration config;
    static TwitchClient twitchClient;
    static boolean hide = false;
    static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "MineTwitch" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
        p = this;
        Bukkit.broadcastMessage(prefix + " Type /mt to start MineTwitch.\n\nFirst time? Go to /plugins/MineTwitch/config.yml and setup the plugin");
        this.getCommand("mt").setExecutor(new CommandMinetwitch());

        this.saveDefaultConfig();
        this.saveConfig();
        config = this.getConfig();

        hide = config.getBoolean("hide");
    }

    @Override
    public void onDisable() {
        disable();
    }

    public static void disable(){
        twitchClient.close();
        for (Team team : Main.board.getTeams()) {
            team.unregister();
        }
        minetwitch.unregister();
        Bukkit.getScheduler().cancelTasks(p);
        Bukkit.broadcastMessage(prefix + " Disabled");
    }
}
