package com.github.risbun.minetwitch.parser;

import com.github.risbun.minetwitch.Main;
import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import static com.github.risbun.minetwitch.Main.classLoader;
import static com.github.risbun.minetwitch.Main.p;
import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getServer;

public class CommandParser {
    public void send(String alias, String command){
        if(!command.startsWith("custom")){
            sendCommand(command);
            if(command.startsWith("give")){
                Main.announceAll(alias);
                sendCommand("title @a title \"" +alias+"\"");
            }
        }else{
            String c = command.split(" ")[1];

            runCustom(c);
        }
    }

    public static void runCustom(String c) {
        CustomScript ClassToRun = null;
        try {
            ClassToRun = classLoader
                    .loadClass("com.github.risbun.minetwitch.customscript." + c)
                    .asSubclass(CustomScript.class).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
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

    private static @NotNull Runnable revertClassToRun(CustomScript ClassToRun, AnnounceLevel level){
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
        getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at @a run " + command);
    }
}
