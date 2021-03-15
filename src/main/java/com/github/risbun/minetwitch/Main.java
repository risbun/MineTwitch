package com.github.risbun.minetwitch;

import com.github.risbun.minetwitch.commands.CommandMinetwitch;
import com.github.risbun.minetwitch.commands.CommandTest;
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
    public static Scoreboard board;
    public static List<String> globalVotes = new ArrayList<>();
    public static List<String> votes = new ArrayList<>();
    public static List<String> chosen = new ArrayList<>();
    public static List<String> chosenActions = new ArrayList<>();
    public static String customCommand = "";
    public static boolean votenow = false;
    public static boolean enabled = false;
    public static Plugin p = null;
    public static TwitchClient twitchClient = null;
    public static String prefix = ChatColor.DARK_GRAY + "§7[§fMine§5Twitch§7]§r";
    public static FileConfiguration commandsConfig;

    public static ClassLoader classLoader;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);

        p = this;
        classLoader = this.getClassLoader();

        Bukkit.broadcastMessage(prefix + " Type /mt to start MineTwitch.\n\nFirst time? Go to /plugins/MineTwitch/config.yml and setup the plugin");

        Objects.requireNonNull(this.getCommand("minetwitch")).setExecutor(new CommandMinetwitch());
        Objects.requireNonNull(this.getCommand("Test")).setExecutor(new CommandTest());

        this.saveDefaultConfig();

        CreateCommandJSON();
    }

    @Override
    public void onDisable() {
        List<String> channels = this.getConfig().getStringList("bot.channels");
        for (String chl : channels) {
            twitchClient.getChat().leaveChannel(chl);
        }
        twitchClient.close();
        disable();
    }

    public static void disable() {
        customCommand = "";

        for (Team team : board.getTeams()) {
            team.unregister();
        }
        if (board.getObjective("minetwitch") != null) {
            Objects.requireNonNull(board.getObjective("minetwitch")).unregister();
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
