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
    public static Objective challonge;
    public static Scoreboard board;
    public static List<String> chooses = new ArrayList<>();
    public static List<String> globalVotes = new ArrayList<>();
    public static List<String> votes = new ArrayList<>();
    public static List<String> chosen = new ArrayList<>();
    public static List<String> chosenActions = new ArrayList<>();
    public static boolean votenow = false;
    public static Plugin pulga = null;
    public static boolean enabled = false;
    public static FileConfiguration config;
    public static TwitchClient twitchClient;
    public static boolean hide = false;
    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
        pulga = this;
        Bukkit.broadcastMessage(prefix + " Type /chEnable to start Challonge. First time? Go to /plugins/Challonge/config.yml and setup the plugin");
        this.getCommand("ch").setExecutor(new CommandChallonge());
        this.saveDefaultConfig();
        config = this.getConfig();

        Main.hide = config.getBoolean("hide");

        OAuth2Credential oauth = new OAuth2Credential("twitch", config.getString("oauth"));
        twitchClient = TwitchClientBuilder.builder()
                .withEnableHelix(true)
                .withChatAccount(oauth)
                .withEnableChat(true)
                .build();

        Bot.load(twitchClient);
    }

    @Override
    public void onDisable() {
        twitchClient.close();
        for (Iterator<Team> t = Main.board.getTeams().iterator(); t.hasNext();) {
            Team team = t.next();
            team.unregister();
        }
        challonge.unregister();
        Bukkit.getScheduler().cancelTasks(pulga);
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Disabled");
    }
}
