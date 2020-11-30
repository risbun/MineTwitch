package com.github.risbun.minetwitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClientBuilder;
import org.bukkit.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;
import static com.github.risbun.minetwitch.Main.*;

public class CommandMinetwitch implements CommandExecutor {
    private final Random rand = new Random();

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp()) {
            if (!enabled) {
                enabled = true;
                Bukkit.broadcastMessage(prefix + " Starting...");

                BukkitScheduler scheduler = getServer().getScheduler();

                OAuth2Credential oauth;

                if (!Objects.requireNonNull(config.getString("oauth")).equals("oauth:xxxx")) {
                    oauth = new OAuth2Credential("twitch", Objects.requireNonNull(config.getString("oauth")));
                } else {
                    Bukkit.broadcastMessage(prefix + " OAuth in config not set, follow from step 5 under getting started on this page: https://gg.gg/MineTwitchSetup");
                    enabled = false;
                    return false;
                }

                if (twitchClient == null) {
                    twitchClient = TwitchClientBuilder.builder()
                            .withEnableHelix(true)
                            .withChatAccount(oauth)
                            .withEnableChat(true)
                            .build();

                    Bot.load(twitchClient);
                }

                Runnable results = () -> {
                    votenow = false;
                    int n = getWinning();

                    Bot.send(chosen.get(n));

                    CommandParser parser = new CommandParser();
                    parser.send(chosen.get(n), chosenActions.get(n));
                };

                Runnable voting = () -> {
                    Bot.send("Start voting now!");

                    votenow = true;
                    customCommand = "";

                    globalVotes.clear();
                    votes.clear();

                    chosen.clear();
                    chosenActions.clear();

                    ArrayList arr = (ArrayList) commandsConfig.get("arr");


                    for (int i = 0; i < 3; i++) {
                        int ran = rand.nextInt(Objects.requireNonNull(arr).size());

                        HashMap hash = (HashMap) arr.get(ran);

                        chosen.add(hash.get("name").toString());
                        chosenActions.add(hash.get("action").toString());

                        votes.add(String.valueOf(0));
                        update(i, 0);

                        if (hide) {
                            Bot.send((i + 1) + ". " + chosen.get(i));
                        }
                    }

                    for (Team team : board.getTeams()) {
                        team.setSuffix(chosen.get(Integer.parseInt(team.getName().substring(0, 1)) - 1));
                    }
                    scheduler.scheduleSyncDelayedTask(p, results, convertToLong(config.getInt("time")));
                };

                Runnable starting = () -> scheduler.scheduleSyncRepeatingTask(p, voting, 0L, convertToLong(config.getInt("delay") + config.getInt("time")));

                scheduler.scheduleSyncDelayedTask(p, starting, 200L);


                ScoreboardManager m = Bukkit.getScoreboardManager();
                board = m != null ? m.getMainScoreboard() : null;

                minetwitch = board != null ? board.registerNewObjective("minetwitch", "", prefix) : null;

                if (!hide) {
                    if (minetwitch != null) {
                        minetwitch.setDisplaySlot(DisplaySlot.SIDEBAR);
                    }
                }

                for (int i = 1; i < 4; i++) {
                    if (board != null) {
                        Team t;
                        t = board.registerNewTeam(i + ". ");
                        t.addEntry(i + ". ");
                        t.setSuffix("Loading...");
                        minetwitch.getScore(i + ". ").setScore(0);
                    }
                }

                Bukkit.broadcastMessage(prefix + " Loaded, waiting 10 seconds");
            } else {
                enabled = false;
                disable();
            }
        } else {
            sender.sendMessage(prefix + " You have to be OP to use this command");
        }
        return true;
    }

    private long convertToLong(int i) {
        return i * 20;
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
        minetwitch.getScore(n + ". ").setScore(val);
    }
}