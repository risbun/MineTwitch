package com.github.risbun.minetwitch;

import com.github.risbun.minetwitch.commands.CMinetwitch;
import com.github.risbun.minetwitch.commands.CTest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainClass extends JavaPlugin implements Listener {
    public static MainClass plugin = null;
    public static Random rand = new Random();
    public static int[] votes = new int[3];
    public static List<EventContainer> chosen = new ArrayList<>();
    public static String prefix = ChatColor.DARK_GRAY + "§7[§fMine§5Twitch§7]§r";
    public List<EventContainer> allEvents = new ArrayList<>();

    public ClassLoader classLoader;

    public static FileConfiguration config;
    public static FileConfiguration eventConfig;

    protected final CMinetwitch cMinetwitch = new CMinetwitch();

    @Override
    public void onEnable() {
        plugin = this;
        classLoader = this.getClassLoader();

        this.saveDefaultConfig();
        CreateCommandJSON();

        config = getConfig();

        announceAll(prefix + " Type /mt to start MineTwitch.\n\nFirst time? Go to /plugins/MineTwitch/config.yml and setup the plugin");

        Objects.requireNonNull(this.getCommand("minetwitch")).setExecutor(cMinetwitch);
        Objects.requireNonNull(this.getCommand("test")).setExecutor(new CTest());

        Iterator<String> keys = eventConfig.getKeys(false).iterator();
        Map<String, Object> values = eventConfig.getValues(false);

        while(keys.hasNext()) {
            String s = keys.next();

            allEvents.add(new EventContainer(s, (String) values.get(s)));
        }

        //TwitchBot twitchBot = new TwitchBot();
        //twitchBot.run();
    }

    @Override
    public void onDisable() {
        if(TwitchBot.client != null){
            List<String> channels = config.getStringList("bot.channels");

            for (String chl : channels) {
                TwitchBot.client.getChat().leaveChannel(chl);
            }
            TwitchBot.client.close();
        }

        cMinetwitch.Disable();
    }

    private void CreateCommandJSON() {
        File eventsFile = new File(getDataFolder(), "eventsConfig.json");

        if (!eventsFile.exists()) {
            saveResource("eventsConfig.json", false);
        }

        eventConfig = new YamlConfiguration();

        try {
            eventConfig.load(eventsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static List<Player> getPlayers(){
        List<Player> player = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(shouldBeAffected(p)) player.add(p);
        }
        return player;
    }

    public static boolean shouldBeAffected(Player p){
        switch (p.getGameMode()){
            case CREATIVE:
            case SPECTATOR:
                return false;
            case SURVIVAL:
            case ADVENTURE:
                return true;
        }

        return false;
    }

    public static void announceAll(String message){
        //plugin.getServer().getLogger().log(Level.INFO, message);
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(message);
        }
    }

    public static void debugLog(String message){
        //plugin.getServer().getLogger().log(Level.INFO, message);
        for(Player p : Bukkit.getOnlinePlayers()){
            if(!p.isOp()) continue;

            p.sendMessage(message);
        }
    }
}
