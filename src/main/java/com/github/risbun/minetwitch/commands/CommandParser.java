package com.github.risbun.minetwitch.commands;

import com.github.risbun.minetwitch.interfaces.CustomPlugin;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;

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

            CustomPlugin ClassToRun = null;
            try {
                ClassToRun = classLoader
                        .loadClass("com.github.risbun.minetwitch.customplugin." + c)
                        .asSubclass(CustomPlugin.class).getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

            if(ClassToRun != null){
                if(ClassToRun.run())
                    getScheduler().scheduleSyncDelayedTask(p, ClassToRun::revert, 600L);
            }
        }
    }

    public static void sendCommand(String command){
        getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }
    public static void sendAllCommand(String command){
        getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at @a run " + command);
    }
}
