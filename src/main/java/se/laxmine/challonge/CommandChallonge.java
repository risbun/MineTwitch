package se.laxmine.challonge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;

public class CommandChallonge implements CommandExecutor {
    public Random rand = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Main.enabled) {
            BukkitScheduler scheduler = getServer().getScheduler();

            Runnable task3 = () -> {
                Main.votenow = false;
                int n = getWinning();

                Bot.send(Main.chosen.get(n));

                getServer().dispatchCommand(Bukkit.getConsoleSender(), Main.chosenActions.get(n));
            };

            Runnable task2 = () -> {
                Bot.send("Starting voting now!");
                Bukkit.broadcastMessage("Voting time!");
                Main.votenow = true;

                Main.globalVotes.clear();
                Main.votes.clear();
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

                for (int i = 0; i < 3; i++) {
                    Main.votes.add(String.valueOf(0));
                    update(i, 0);
                }
                for (Iterator<Team> t = Main.board.getTeams().iterator(); t.hasNext(); ) {
                    Team team = t.next();
                    team.setSuffix(Main.chosen.get(Integer.parseInt(team.getName().substring(0, 1)) - 1));
                }
                scheduler.scheduleSyncDelayedTask(Main.pulga, task3, convertToLong(Main.config.getInt("time")));
            };

            Runnable task1 = () -> scheduler.scheduleSyncRepeatingTask(Main.pulga, task2, 0L, convertToLong(Main.config.getInt("delay") + Main.config.getInt("time")));

            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Starting...");
            scheduler.scheduleSyncDelayedTask(Main.pulga, task1, 200L);


            ScoreboardManager m = Bukkit.getScoreboardManager();
            Main.board = m.getMainScoreboard();

            Main.challonge = Main.board.registerNewObjective("challonge", "", "" + ChatColor.WHITE + ChatColor.BOLD + "Challonge");
            Main.challonge.setDisplaySlot(DisplaySlot.SIDEBAR);

            for (int i = 1; i < 4; i++) {
                Team t = Main.board.registerNewTeam(i + ". ");
                t.addEntry(i + ". ");
                t.setSuffix("Loading...");
                Main.challonge.getScore(i + ". ").setScore(0);
            }

            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Loaded, waiting 10 seconds");

            final JSONObject obj = new JSONObject(Main.config.getString("actions"));
            final JSONArray arr = obj.getJSONArray("arr");
            for (int i = 0; i < arr.length(); i++) {
                final JSONObject curr = arr.getJSONObject(i);
                Main.chooses.add(curr.toString());
            }
        }else{
            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Disabling...");
            Main.challonge.unregister();
            Bukkit.getScheduler().cancelTasks(Main.pulga);
            Main.enabled = false;
            Main.chooses.clear();
            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "Challonge" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Disabled");
        }
        return true;
    }

    private long convertToLong(int i) {
        return Long.valueOf(i * 20);
    }

    private int getWinning() {
        int largest = 0;
        for ( int i = 1; i < Main.votes.size(); i++ )
        {
            if ( Integer.parseInt(Main.votes.get(i)) > Integer.parseInt(Main.votes.get(largest))) {
                largest = i;
            }
        }
        return largest;
    }

    public static void update(int n, int val){
        n++;
        Main.challonge.getScore(n+". ").setScore(val);
    }
}
