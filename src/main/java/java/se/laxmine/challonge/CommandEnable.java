package java.se.laxmine.challonge;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.gikk.twirk.events.TwirkListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;

public class CommandEnable implements CommandExecutor {
    public Random rand = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Main.enabled) {
            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " Already enabled, type /chDisable to turn off");
        } else {
            Main.enabled = true;
            BukkitScheduler scheduler = getServer().getScheduler();

            Runnable task3 = new Runnable() {
                @Override
                public void run() {
                    Main.votenow = false;
                    int n = getWinning();
                    if (n == 0) {
                        Main.one.setPrefix(ChatColor.GREEN + " 1. " + Main.chosen.get(0) + " - " + Main.oneVotes.size());
                    } else if (n == 1) {
                        Main.two.setPrefix(ChatColor.GREEN + " 2. " + Main.chosen.get(1) + " - " + Main.twoVotes.size());
                    } else if (n == 2) {
                        Main.three.setPrefix(ChatColor.GREEN + " 3. " + Main.chosen.get(2) + " - " + Main.threeVotes.size());
                    }
                    Main.twirk.channelMessage("Vote ended, result: " + Main.chosen.get(n));

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Main.chosenActions.get(n));
                }
            };
            Runnable task2 = new Runnable() {
                @Override
                public void run() {
                    Main.twirk.channelMessage("Start voting now!");
                    Main.votenow = true;
                    Main.chosen.clear();
                    Main.chosenActions.clear();
                    int i1 = rand.nextInt(Main.chooses.size());
                    int i2 = rand.nextInt(Main.chooses.size());
                    int i3 = rand.nextInt(Main.chooses.size());
                    Main.chosen.add(new JSONObject(Main.chooses.get(i1)).getString("name"));
                    Main.chosen.add(new JSONObject(Main.chooses.get(i2)).getString("name"));
                    Main.chosen.add(new JSONObject(Main.chooses.get(i3)).getString("name"));
                    Main.chosenActions.add(new JSONObject(Main.chooses.get(i1)).getString("action"));
                    Main.chosenActions.add(new JSONObject(Main.chooses.get(i2)).getString("action"));
                    Main.chosenActions.add(new JSONObject(Main.chooses.get(i3)).getString("action"));
                    Main.globalVotes.clear();
                    Main.oneVotes.clear();
                    Main.twoVotes.clear();
                    Main.threeVotes.clear();
                    Main.one.setPrefix(ChatColor.WHITE + " 1. " + Main.chosen.get(0) + " - 0");
                    Main.two.setPrefix(ChatColor.WHITE + " 2. " + Main.chosen.get(1) + " - 0");
                    Main.three.setPrefix(ChatColor.WHITE + " 3. " + Main.chosen.get(2) + " - 0");
                    scheduler.scheduleSyncDelayedTask(Main.pulga, task3, convertToLong(Main.config.getInt("time")));
                }
            };
            Runnable task1 = new Runnable() {
                @Override
                public void run() {
                    scheduler.scheduleSyncRepeatingTask(Main.pulga, task2, 0L, convertToLong(Main.config.getInt("delay") + Main.config.getInt("time")));
                }
            };

            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Starting...");
            scheduler.scheduleSyncDelayedTask(Main.pulga, task1, 200L);

            try {
                Main.twirk = new TwirkBuilder("#" + Main.config.getString("channel"), Main.config.getString("username"), Main.config.getString("oauth")).build();
            } catch (
                    IOException e) {
                e.printStackTrace();
            }

            Main.twirk.addIrcListener(getOnDisconnectListener(Main.twirk));
            Main.twirk.addIrcListener(new Bot(Main.twirk));

            try {
                Main.twirk.connect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ScoreboardManager m = Bukkit.getScoreboardManager();
            Scoreboard b = m.getMainScoreboard();

            Main.challonge = b.registerNewObjective("challonge", "", "" + ChatColor.WHITE + ChatColor.BOLD + "Challonge");
            Main.challonge.setDisplaySlot(DisplaySlot.SIDEBAR);

            Main.one = b.registerNewTeam(ChatColor.RED.toString());
            Main.two = b.registerNewTeam(ChatColor.GREEN.toString());
            Main.three = b.registerNewTeam(ChatColor.BLUE.toString());

            Main.one.addEntry(ChatColor.RED.toString());
            Main.two.addEntry(ChatColor.GREEN.toString());
            Main.three.addEntry(ChatColor.BLUE.toString());

            Main.challonge.getScore(ChatColor.RED.toString()).setScore(4);
            Main.challonge.getScore(ChatColor.GREEN.toString()).setScore(3);
            Main.challonge.getScore(ChatColor.BLUE.toString()).setScore(2);

            Main.challonge.getScore(ChatColor.WHITE.toString()).setScore(1);
            Main.challonge.getScore(ChatColor.BLACK.toString()).setScore(5);

            Main.one.setPrefix(ChatColor.WHITE + " 1. Loading...");
            Main.two.setPrefix(ChatColor.WHITE + " 2. Loading...");
            Main.three.setPrefix(ChatColor.WHITE + " 3. Loading...");

            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Loaded, waiting 10 seconds");

            final JSONObject obj = new JSONObject(Main.config.getString("actions"));
            final JSONArray arr = obj.getJSONArray("arr");
            for (int i = 0; i < arr.length(); i++) {
                final JSONObject curr = arr.getJSONObject(i);
                Main.chooses.add(curr.toString());
            }
        }
        return true;
    }

    private long convertToLong(int i) {
        return Long.valueOf(i * 20);
    }

    private static TwirkListener getOnDisconnectListener(final Twirk twirk) {
        return new TwirkListener() {
            @Override
            public void onDisconnect() {
                try {
                    if( !twirk.connect() ) twirk.close();
                }
                catch (IOException e) { twirk.close(); }
                catch (InterruptedException e) {  }
            }
        };
    }

    private int getWinning() {
        if( Main.oneVotes.size() >= Main.twoVotes.size() && Main.oneVotes.size() >= Main.threeVotes.size()) return 0;
        else if (Main.twoVotes.size() >= Main.oneVotes.size() && Main.twoVotes.size() >= Main.threeVotes.size()) return 1;
        else return 2;
    }
}
