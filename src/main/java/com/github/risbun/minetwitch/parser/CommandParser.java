package com.github.risbun.minetwitch.parser;

import com.github.risbun.minetwitch.interfaces.CustomScript;
import org.bukkit.Bukkit;

import static com.github.risbun.minetwitch.Main.classLoader;
import static com.github.risbun.minetwitch.Main.p;
import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getServer;

public class CommandParser {
    public void send(String alias, String command){
        if(!command.startsWith("custom")){
            sendCommand(command);
            if(command.startsWith("give")){
                Bukkit.broadcastMessage(alias);
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

        if(ClassToRun != null && ClassToRun.run())
            getScheduler().scheduleSyncDelayedTask(p, ClassToRun::revert, 600L);
    }

    public static void sendCommand(String command){
        getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }
    public static void sendAllCommand(String command){
        getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at @a run " + command);
    }
}
