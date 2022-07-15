package com.github.risbun.minetwitch;

import com.github.risbun.minetwitch.commands.CMinetwitch;
import com.github.risbun.minetwitch.commands.CTest;
import com.github.twitch4j.TwitchClient;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
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
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class MainClass extends JavaPlugin implements Listener {
    public static MainClass plugin = null;
    public static Random rand;
    public static int[] votes = new int[3];
    public static List<EventContainer> chosen = new ArrayList<>();
    public static String prefix = ChatColor.DARK_GRAY + "§7[§fMine§5Twitch§7]§r";
    public List<EventContainer> allEvents = new ArrayList<>();

    public ClassLoader classLoader;
    private BukkitAudiences adventure;
    public static FileConfiguration config;

    protected final CMinetwitch cMinetwitch = new CMinetwitch();

    @Override
    public void onEnable() {
        adventure = BukkitAudiences.create(this);
        plugin = this;
        classLoader = this.getClassLoader();

        announceAll(prefix + " Type /mt to start MineTwitch.\n\nFirst time? Go to /plugins/MineTwitch/config.yml and setup the plugin");

        Objects.requireNonNull(this.getCommand("minetwitch")).setExecutor(cMinetwitch);
        Objects.requireNonNull(this.getCommand("test")).setExecutor(new CTest());

        this.saveDefaultConfig();

        CreateCommandJSON();

        Iterator<String> keys = config.getKeys(false).iterator();
        Map<String, Object> values = config.getValues(false);

        while(keys.hasNext()) {
            String s = keys.next();

            allEvents.add(new EventContainer(s, (String) values.get(s)));
        }
    }

    @Override
    public void onDisable() {
        List<String> channels = this.getConfig().getStringList("bot.channels");
        for (String chl : channels) {
            TwitchBot.client.getChat().leaveChannel(chl);
        }
        TwitchBot.client.close();
        cMinetwitch.Disable();

        if(adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    private void CreateCommandJSON() {
        File eventsFile = new File(getDataFolder(), "eventsConfig.json");

        if (!eventsFile.exists()) {
            saveResource("eventsConfig.json", false);
        }

        config = new YamlConfiguration();

        try {
            config.load(eventsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static List<Player> getPlayers(){
        List<Player> player = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)) player.add(p);
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
        announceAll(Component.text(message));
    }

    public static void announceAll(TextComponent message){
        plugin.getServer().getLogger().log(Level.INFO, message.content());
        for(Player p : Bukkit.getOnlinePlayers()){
            Audience a = plugin.adventure.player(p);
            a.sendMessage(message);
        }
    }

    public static void debugLog(String message){
        debugLog(Component.text(message));
    }

    public static void debugLog(Component message){
        plugin.getServer().getLogger().log(Level.INFO, ((TextComponent) message).content());
        for(Player p : Bukkit.getOnlinePlayers()){
            if(!p.isOp()) continue;
            Audience a = plugin.adventure.player(p);
            a.sendMessage(message);
        }
    }
}
