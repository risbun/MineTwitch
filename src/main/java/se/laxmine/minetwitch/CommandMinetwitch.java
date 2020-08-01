package se.laxmine.minetwitch;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;
import static se.laxmine.minetwitch.Main.*;

public class CommandMinetwitch implements CommandExecutor {
    private final Random rand = new Random();
    private boolean first = true;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        sender.sendMessage(prefix + " Loading...");

        BukkitScheduler scheduler = getServer().getScheduler();

        Runnable results = () -> {
            VotingSocket.lock();
            int n = getWinning();

            CommandParser parser = new CommandParser();
            parser.send(chosen.get(n), chosenActions.get(n));
            InfoSocket.sendAll(chosen.get(0) +" | "+ chosen.get(1) +" | "+ chosen.get(2) );
            InfoSocket.sendAll(votes.get(0) +" | "+ votes.get(1) +" | "+ votes.get(2) );
            InfoSocket.sendAll(chosen.get(n) +  " | " + chosenActions.get(n) + "<br>");
        };

        Runnable voting = () -> {

            if(first){
                Bukkit.broadcastMessage(prefix + " Start!");
                first = false;
            }

            VotingSocket.reset();
            VotingSocket.unlock();

            customCommand = "";

            ArrayList arr = (ArrayList) commandsConfig.get("arr");


            for (int i = 0; i < 3; i++) {
                int ran = rand.nextInt(Objects.requireNonNull(arr).size());

                HashMap hash = (HashMap) arr.get(ran);

                chosen.set(i, hash.get("name").toString());
                chosenActions.set(i,hash.get("action").toString());

                votes.set(i,0);
            }
            VotingSocket.update();
            scheduler.scheduleSyncDelayedTask(p, results, convertToLong(config.getInt("time")));
        };

        Runnable starting = () -> scheduler.scheduleSyncRepeatingTask(p, voting, 0L, convertToLong(config.getInt("delay") + config.getInt("time")));

        scheduler.scheduleSyncDelayedTask(p, starting, convertToLong(10));

        for (int i = 0; i < 3; i++) {
            chosen.add("0");
            chosenActions.add("0");
            votes.add(0);
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
            if ( votes.get(i) > votes.get(largest)) {
                largest = i;
            }
            if (votes.get(i).equals(votes.get(largest))){
                if(rand.nextFloat() >= .5){
                    largest = i;
                }
            }
        }
        return largest;
    }
}
