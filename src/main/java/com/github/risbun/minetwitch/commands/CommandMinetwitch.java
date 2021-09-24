package com.github.risbun.minetwitch.commands;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.risbun.minetwitch.Bot;
import com.github.risbun.minetwitch.Main;
import com.github.risbun.minetwitch.parser.CommandParser;
import com.github.twitch4j.TwitchClientBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static org.bukkit.Bukkit.getServer;
import static com.github.risbun.minetwitch.Main.*;

public class CommandMinetwitch implements CommandExecutor {
    private final Random rand = new Random();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0 && args[0].equals("reload")) {
            p.reloadConfig();
            sender.sendMessage(prefix + " Config reloaded");
        } else {
            if (!enabled) {
                enabled = true;

                Main.announceAll(prefix + " Starting...");
                BukkitScheduler scheduler = getServer().getScheduler();
                OAuth2Credential oauth;

                String oauthString = Objects.requireNonNull(p.getConfig().getString("bot.oauth"));
                if (!oauthString.equals("oauth:xxxx")) {
                    oauth = new OAuth2Credential("twitch", oauthString);
                } else {
                    Main.announceAll(prefix + " OAuth in config not set, follow getting started on this page: https://risbun.github.io/MineTwitch/");
                    enabled = false;
                    return true;
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

                    ArrayList<?> arr = (ArrayList<?>) commandsConfig.getList("arr");

                    boolean hide = p.getConfig().getBoolean("ingame.hide");

                    for (int i = 0; i < 3; i++) {
                        int ran = rand.nextInt(Objects.requireNonNull(arr).size());

                        HashMap<?, ?> hash = (HashMap<?, ?>) arr.get(ran);

                        chosen.add(hash.get("name").toString());
                        chosenActions.add(hash.get("action").toString());

                        votes.add(String.valueOf(0));
                        if (hide) {
                            Bot.send((i + 1) + ". " + chosen.get(i));
                        } else {
                            update(i, 0);
                        }
                    }

                    if (!hide) {
                        for (Team team : board.getTeams()) {
                            team.suffix(Component.text(chosen.get(Integer.parseInt(team.getName().substring(0, 1)) - 1)));
                        }
                    }
                    scheduler.scheduleSyncDelayedTask(p, results, p.getConfig().getInt("vote.time") * 20L);
                };

                long period = (p.getConfig().getInt("vote.delay") + p.getConfig().getInt("vote.time")) * 20L;
                Runnable starting = () -> scheduler.scheduleSyncRepeatingTask(p, voting, 0L, period);
                scheduler.scheduleSyncDelayedTask(p, starting, 200L);

                if (!p.getConfig().getBoolean("ingame.hide")) {
                    board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();

                    Objective minetwitch = board.registerNewObjective("minetwitch", "", Component.text(prefix));
                    minetwitch.setDisplaySlot(DisplaySlot.SIDEBAR);

                    for (int i = 1; i < 4; i++) {
                        Team t;
                        t = board.registerNewTeam(i + ". ");
                        t.addEntry(i + ". ");
                        t.suffix(Component.text("Loading..."));
                        minetwitch.getScore(i + ". ").setScore(0);
                    }
                }

                Main.announceAll(prefix + " Loaded, waiting 10 seconds");
            } else {
                enabled = false;
                disable();
            }
        }
        return true;
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

    public static void update(int n, int val){
        n++;
        Objects.requireNonNull(board.getObjective("minetwitch")).getScore(n + ". ").setScore(val);
    }
}
