package se.laxmine.challonge;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClientBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

import static org.bukkit.Bukkit.getServer;
import static se.laxmine.challonge.Main.*;

public class CommandChallonge implements CommandExecutor {
    private Random rand = new Random();

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp()) {
            if (!enabled) {
                enabled = true;
                BukkitScheduler scheduler = getServer().getScheduler();

                OAuth2Credential oauth = null;
                if(!config.getString("oauth").equals("oauth:xxxx")){
                    oauth = new OAuth2Credential("twitch", config.getString("oauth"));
                }else{
                    Bukkit.broadcastMessage("Wrong oauth id.");
                }
                twitchClient = TwitchClientBuilder.builder()
                        .withEnableHelix(true)
                        .withChatAccount(oauth)
                        .withEnableChat(true)
                        .build();

                Bot.load(twitchClient);

                Runnable task3 = () -> {
                    votenow = false;
                    int n = getWinning();

                    Bot.send(chosen.get(n));

                    getServer().dispatchCommand(Bukkit.getConsoleSender(), chosenActions.get(n));
                };

                Runnable task2 = () -> {
                    Bot.send("Starting voting now!");
                    votenow = true;

                    globalVotes.clear();
                    votes.clear();
                    chosen.clear();
                    chosenActions.clear();

                    for (int i = 0; i < 3; i++) {
                        int ran = rand.nextInt(chooses.size());

                        chosen.add(new JSONObject(chooses.get(ran)).getString("name"));
                        chosenActions.add(new JSONObject(chooses.get(ran)).getString("action"));

                        votes.add(String.valueOf(0));
                        update(i, 0);
                        if (hide) {
                            Bot.send((i + 1) + ". " + chosen.get(i));
                        }
                    }
                    for (Team team : board.getTeams()) {
                        team.setSuffix(chosen.get(Integer.parseInt(team.getName().substring(0, 1)) - 1));
                    }
                    scheduler.scheduleSyncDelayedTask(p, task3, convertToLong(config.getInt("time")));
                };

                Runnable task1 = () -> scheduler.scheduleSyncRepeatingTask(p, task2, 0L, convertToLong(config.getInt("delay") + config.getInt("time")));

                Bukkit.broadcastMessage(prefix + " Starting...");
                scheduler.scheduleSyncDelayedTask(p, task1, 200L);


                ScoreboardManager m = Bukkit.getScoreboardManager();
                board = m != null ? m.getMainScoreboard() : null;

                challonge = board != null ? board.registerNewObjective("challonge", "", "" + ChatColor.WHITE + ChatColor.BOLD + "Challonge") : null;

                if (!hide) {
                    if (challonge != null) {
                        challonge.setDisplaySlot(DisplaySlot.SIDEBAR);
                    }
                }

                for (int i = 1; i < 4; i++) {
                    Team t = null;
                    if (board != null) {
                        t = board.registerNewTeam(i + ". ");
                        t.addEntry(i + ". ");
                        t.setSuffix("Loading...");
                        challonge.getScore(i + ". ").setScore(0);
                    }
                }

                Bukkit.broadcastMessage(prefix + " Loaded, waiting 10 seconds");

                final JSONObject obj = new JSONObject(config.getString("actions"));
                final JSONArray arr = obj.getJSONArray("arr");
                for (int i = 0; i < arr.length(); i++) {
                    final JSONObject curr = arr.getJSONObject(i);
                    chooses.add(curr.toString());
                }
            } else {
                sender.sendMessage(prefix + " Disabling...");
                challonge.unregister();
                Bukkit.getScheduler().cancelTasks(p);
                enabled = false;
                chooses.clear();

                for (Team team : board.getTeams()) {
                    team.unregister();
                }

                sender.sendMessage(prefix + " Disabled");
            }
        }else{
            sender.sendMessage(prefix + " You have to be OP to send this command");
        }
        return true;
    }

    private long convertToLong(int i) {
        return (long) (i * 20);
    }

    private int getWinning() {
        int largest = 0;
        for ( int i = 1; i < votes.size(); i++ )
        {
            if ( Integer.parseInt(votes.get(i)) > Integer.parseInt(votes.get(largest))) {
                largest = i;
            }
        }
        return largest;
    }

    static void update(int n, int val){
        n++;
        challonge.getScore(n+". ").setScore(val);
    }
}
