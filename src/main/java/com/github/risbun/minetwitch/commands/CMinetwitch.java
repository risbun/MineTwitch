package com.github.risbun.minetwitch.commands;

import com.github.risbun.minetwitch.TwitchBot;
import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.EventContainer;
import com.github.risbun.minetwitch.EventManager;
import org.bukkit.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static org.bukkit.Bukkit.getServer;
import static com.github.risbun.minetwitch.MainClass.*;

public class CMinetwitch implements CommandExecutor {

    private Scoreboard board;
    private Objective obj;
    private boolean hidden;
    public static boolean voteActive = false;
    public static boolean enabled = false;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0 && args[0].equals("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(prefix + " Config reloaded");
            return true;
        }

        enabled = !enabled;

        if(!enabled){
            Disable();
            return true;
        }

        MainClass.announceAll(prefix + " Starting...");
        BukkitScheduler scheduler = getServer().getScheduler();

        String oauthString = Objects.requireNonNull(config.getString("bot.oauth"));
        int voteDelay = config.getInt("vote.delay");
        int voteTime = config.getInt("vote.time");
        hidden = config.getBoolean("ingame.hide");

        if (oauthString.equals("oauth:xxxx")) {
            MainClass.announceAll(prefix + " OAuth in config not set, follow getting started on this page: https://risbun.github.io/MineTwitch/");
            enabled = false;
            return true;
        }

        if(!hidden){
            board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
            obj = board.registerNewObjective("minetwitch", "", prefix);
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            for (int i = 1; i < 4; i++) {
                Team t = board.registerNewTeam(i + ". ");
                t.addEntry(i + ". ");
                t.setSuffix("Loading...");
                obj.getScore(i + ". ").setScore(0);
            }
        }

        Runnable results = () -> {
            voteActive = false;
            int n = getWinning();

            TwitchBot.send(chosen.get(n).getKey());
            EventManager.Apply(chosen.get(n).getKey(), chosen.get(n).GetCommand());
        };

        Runnable voting = () -> {
            TwitchBot.send("Start voting now!");
            TwitchBot.ClearList();
            voteActive = true;

            votes = new int[3];
            chosen.clear();

            for (int i = 0; i < 3; i++) {
                int ran = rand.nextInt(plugin.allEvents.size());

                EventContainer event = plugin.allEvents.get(ran);

                String key = event.getKey();
                String value = event.GetCommand();

                chosen.add(new EventContainer(key, value));

                if (hidden) {
                    TwitchBot.send((i + 1) + ". " + chosen.get(i));
                } else {
                    UpdateScoreboard(i, 0);
                }
            }

            if (!hidden) {
                for (Team team : board.getTeams()) {
                    team.setSuffix(chosen.get(Integer.parseInt(team.getName().substring(0, 1)) - 1).getKey());
                }
            }

            scheduler.scheduleSyncDelayedTask(plugin, results, plugin.getConfig().getInt("vote.time") * 20L);
        };

        int padding = voteDelay + voteTime;
        scheduler.scheduleSyncRepeatingTask(plugin, voting, 0L, padding * 20L);

        announceAll(prefix + " Loaded, waiting 10 seconds");
        return true;
    }

    private int getWinning() {
        int largest = -1;
        List<Integer> currentWinning = new ArrayList<>();

        for ( int i = 0; i < 3; i++ )
        {
            if (votes[i] > largest) {
                largest = i;

                currentWinning.clear();
                currentWinning.add(i);
            }else if(votes[i] == votes[largest]){
                currentWinning.add(i);
            }
        }

        return rand.nextInt(currentWinning.size());
    }

    public void UpdateScoreboard(int s, int val){
        obj.getScore(++s + ". ").setScore(val);
    }

    public void Disable() {
        Bukkit.getScheduler().cancelTasks(plugin);
        MainClass.announceAll(prefix + " Disabled");

        if(board == null) return;

        for (Team team : board.getTeams()) {
            team.unregister();
        }
        if(board.getObjective("minetwitch") == null) return;

        Objects.requireNonNull(board.getObjective("minetwitch")).unregister();
    }
}
