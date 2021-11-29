package com.github.risbun.minetwitch.parser;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.risbun.minetwitch.MainClass.classLoader;
import static com.github.risbun.minetwitch.MainClass.p;
import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getServer;

public class CommandParser {
    public void send(String alias, String command){
        if(!command.startsWith("custom")){
            sendCommand(command);
            if(command.startsWith("give")){
                MainClass.announceAll(alias);
                sendCommand("title @a title \"" +alias+"\"");
            }
        }else{
            String c = command.split(" ")[1];

            runCustom(c);
        }
    }

    public static void runCustom(String c) {
        CustomEvent ClassToRun = null;
        try {
            ClassToRun = classLoader
                    .loadClass("com.github.risbun.minetwitch.customscript." + c)
                    .asSubclass(CustomEvent.class).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            MainClass.announceAll(String.format("Error loading CustomEvent:\n[%s]", e));
        }

        if(ClassToRun == null) return;

        AnnounceLevel level = ClassToRun.getAnnounceLevel();

        if(level != null){
            if(level.equals(AnnounceLevel.Start) || level.equals(AnnounceLevel.Both)) ClassToRun.announceStart();
        }

        if(ClassToRun.run()){
            getScheduler().scheduleSyncDelayedTask(p, revertClassToRun(ClassToRun, level), 600L);
        }
    }

    private static @NotNull Runnable revertClassToRun(CustomEvent ClassToRun, AnnounceLevel level){
        return () -> {
            ClassToRun.revert();

            if(level != null){
                if(level.equals(AnnounceLevel.End) || level.equals(AnnounceLevel.Both)) ClassToRun.announceEnd();
            }
        };
    }

    public void sendCommand(String command){
        getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void sendAllCommand(String command){
        for(Player p : MainClass.GetPlayers()){
            getServer().dispatchCommand(Bukkit.getConsoleSender(), String.format("execute at %s run %s", p.getName(), command));
        }
    }
}
