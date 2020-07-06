package se.laxmine.minetwitch;

import com.github.twitch4j.TwitchClient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin implements Listener {
    static Objective minetwitch;
    static Scoreboard board;
    static List<String> globalVotes = new ArrayList<>();
    static List<String> votes = new ArrayList<>();
    static List<String> chosen = new ArrayList<>();
    static List<String> chosenActions = new ArrayList<>();
    static String customCommand = "";
    static boolean votenow = false;
    static Plugin p = null;
    static boolean enabled = false;
    static FileConfiguration config;
    static TwitchClient twitchClient = null;
    static boolean hide = false;
    static String prefix = ChatColor.DARK_GRAY + "§7[§fMine§5Twitch§7]§r";

    static FileConfiguration commandsConfig;


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
        p = this;
        Bukkit.broadcastMessage(prefix + " Type /mt to start MineTwitch.\n\nFirst time? Go to /plugins/MineTwitch/config.yml and setup the plugin");
        Objects.requireNonNull(this.getCommand("mt")).setExecutor(new CommandMinetwitch());
        Objects.requireNonNull(this.getCommand("mtreload")).setExecutor(new CommandReload());

        this.saveDefaultConfig();
        this.saveConfig();
        config = this.getConfig();

        hide = config.getBoolean("hide");

        CreateCommandJSON();
    }

    @Override
    public void onDisable() {
        String channel = config.getString("channel");
        if (channel != null) {
            twitchClient.getChat().leaveChannel(channel);
            twitchClient.close();
        }
        disable();
    }

    static void disable(){
        customCommand = "";

        for (Team team : Main.board.getTeams()) {
            team.unregister();
        }
        try {
            minetwitch.unregister();
        } catch (Exception e) {
            customCommand = "";
        }
        Bukkit.getScheduler().cancelTasks(p);
        Bukkit.broadcastMessage(prefix + " Disabled");
    }

    private void CreateCommandJSON() {
        File commandsFile = new File(getDataFolder(), "commands.json");
        if (!commandsFile.exists()) {
            saveResource("commands.json", false);
        }

        commandsConfig = new YamlConfiguration();
        try {
            commandsConfig.load(commandsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
