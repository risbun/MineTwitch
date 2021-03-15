package com.github.risbun.minetwitch.commands;

import com.github.risbun.minetwitch.interfaces.CustomPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

import static com.github.risbun.minetwitch.Main.classLoader;
import static com.github.risbun.minetwitch.Main.p;
import static org.bukkit.Bukkit.getScheduler;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CustomPlugin ClassToRun = null;
        try {
            ClassToRun = classLoader
                    .loadClass("com.github.risbun.minetwitch.customplugin." + args[0])
                    .asSubclass(CustomPlugin.class).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if(ClassToRun != null){
            if(ClassToRun.run())
                getScheduler().scheduleSyncDelayedTask(p, ClassToRun::revert, 600L);
        }else{
            sender.sendMessage("Class not found... " + args[0]);
        }

        return true;
    }
}
