package se.laxmine.challonge;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends JavaPlugin implements Listener {
    static Objective challonge;
    static Scoreboard board;
    static List<String> chooses = new ArrayList<>();
    static List<String> globalVotes = new ArrayList<>();
    static List<String> votes = new ArrayList<>();
    static List<String> chosen = new ArrayList<>();
    static List<String> chosenActions = new ArrayList<>();
    static boolean votenow = false;
    static Plugin p = null;
    static boolean enabled = false;
    static FileConfiguration config;
    static TwitchClient twitchClient;
    static boolean hide = false;
    static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
        p = this;
        Bukkit.broadcastMessage(prefix + " Type /chEnable to start Challonge. First time? Go to /plugins/Challonge/config.yml and setup the plugin");
        this.getCommand("ch").setExecutor(new CommandChallonge());
        this.saveDefaultConfig();
        config = this.getConfig();
        Main.hide = config.getBoolean("hide");
    }

    @Override
    public void onDisable() {
        twitchClient.close();
        for (Team team : Main.board.getTeams()) {
            team.unregister();
        }
        challonge.unregister();
        Bukkit.getScheduler().cancelTasks(p);
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Disabled");
    }

    public void disable(){
        this.setEnabled(false);
    }
}
