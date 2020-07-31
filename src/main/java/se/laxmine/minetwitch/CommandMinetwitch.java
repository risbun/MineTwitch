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
        sender.sendMessage(prefix + "Loading...");

        BukkitScheduler scheduler = getServer().getScheduler();

        Runnable results = () -> {
            WebsocketServer.lock();
            int n = getWinning();

            CommandParser parser = new CommandParser();
            parser.send(chosen.get(n), chosenActions.get(n));
        };

        Runnable voting = () -> {

            if(first){
                Bukkit.broadcastMessage(prefix + " Start!");
                first = false;
            }

            WebsocketServer.reset();
            WebsocketServer.unlock();

            customCommand = "";

            ArrayList arr = (ArrayList) commandsConfig.get("arr");


            for (int i = 0; i < 3; i++) {
                int ran = rand.nextInt(Objects.requireNonNull(arr).size());

                HashMap hash = (HashMap) arr.get(ran);

                chosen.set(i, hash.get("name").toString());
                chosenActions.set(i,hash.get("action").toString());

                votes.set(i,String.valueOf(0));
            }
            WebsocketServer.update();
            scheduler.scheduleSyncDelayedTask(p, results, convertToLong(config.getInt("time")));
        };

        Runnable starting = () -> scheduler.scheduleSyncRepeatingTask(p, voting, 0L, convertToLong(config.getInt("delay") + config.getInt("time")));

        scheduler.scheduleSyncDelayedTask(p, starting, 200L);
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
}
