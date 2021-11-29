package com.github.risbun.minetwitch;

import com.github.risbun.minetwitch.classes.MinetwitchEvent;
import com.github.risbun.minetwitch.commands.CommandMinetwitch;
import com.github.risbun.minetwitch.commands.CommandTest;
import com.github.twitch4j.TwitchClient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class MainClass extends JavaPlugin implements Listener {
    public static Scoreboard board;
    public static List<String> globalVotes = new ArrayList<>();
    public static List<String> votes = new ArrayList<>();
    public static List<String> chosen = new ArrayList<>();
    public static List<String> chosenActions = new ArrayList<>();
    public static boolean votenow = false;
    public static boolean enabled = false;
    public static Plugin p = null;
    public static TwitchClient twitchClient = null;
    public static String prefix = ChatColor.DARK_GRAY + "§7[§fMine§5Twitch§7]§r";

    public static FileConfiguration eventsConfig;
    public static FileConfiguration customConfig;

    public static ClassLoader classLoader;

    public static List<MinetwitchEvent> allEvents = new ArrayList<>();

    @Override
    public void onEnable() {
        p = this;
        classLoader = this.getClassLoader();

        announceAll(prefix + " Type /mt to start MineTwitch.\n\nFirst time? Go to /plugins/MineTwitch/config.yml and setup the plugin");

        Objects.requireNonNull(this.getCommand("minetwitch")).setExecutor(new CommandMinetwitch());
        Objects.requireNonNull(this.getCommand("Test")).setExecutor(new CommandTest());

        this.saveDefaultConfig();

        CreateCommandJSON();

        var keys = eventsConfig.getKeys(false).iterator();
        var values = eventsConfig.getValues(false);

        while(keys.hasNext()) {
            String s = keys.next();

            allEvents.add(new MinetwitchEvent(s, (String) values.get(s)));
        }

        var keys2 = customConfig.getKeys(false).iterator();
        var values2 = customConfig.getValues(false);

        while(keys2.hasNext()) {
            String s = keys2.next();

            allEvents.add(new MinetwitchEvent(s, (String) values2.get(s)));
        }
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
        for (Team team : board.getTeams()) {
            team.unregister();
        }
        if (board.getObjective("minetwitch") != null) {
            Objects.requireNonNull(board.getObjective("minetwitch")).unregister();
        }

        Bukkit.getScheduler().cancelTasks(p);
        announceAll(prefix + " Disabled");
    }

    private void CreateCommandJSON() {
        File eventsFile = new File(getDataFolder(), "eventsConfig.json");
        File customFile = new File(getDataFolder(), "customConfig.json");

        if (!eventsFile.exists()) {
            saveResource("eventsConfig.json", false);
        }

        if (!customFile.exists()) {
            saveResource("customConfig.json", false);
        }

        eventsConfig = new YamlConfiguration();
        customConfig = new YamlConfiguration();

        try {
            eventsConfig.load(eventsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        try {
            customConfig.load(customFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static List<Player> GetPlayers(){
        List<Player> player = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)) player.add(p);
        }
        return player;
    }

    public static boolean ShouldBeAffected(Player p){
        switch (p.getGameMode()){
            case CREATIVE, SPECTATOR -> {
                return false;
            }
            case SURVIVAL, ADVENTURE -> {
                return true;
            }
        }

        return false;
    }

    public static void announceAll(String message){
        announceAll(Component.text(message));
    }

    public static void announceAll(Component message){
        p.getServer().getLogger().log(Level.INFO, ((TextComponent) message).content());
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(message);
        }
    }
}
