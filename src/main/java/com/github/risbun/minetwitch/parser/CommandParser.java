package com.github.risbun.minetwitch.parser;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.enums.AnnounceLevel;
import com.github.risbun.minetwitch.interfaces.CustomEvent;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.risbun.minetwitch.MainClass.classLoader;
import static com.github.risbun.minetwitch.MainClass.p;
import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getServer;

public class CommandParser {
    public static void send(String alias, String command){
        var args = command.split(" ");

        switch (args[0]) {
            case "command" -> runCustom(args[1]);
            case "give" -> {
                var item = args[1];
                var count = args[2];
                for (Player p : MainClass.getPlayers()) {

                    var itemMaterial = Material.matchMaterial(item);

                    if (itemMaterial == null) {
                        MainClass.debugLog(String.format("ITEM COULD NOT BE FOUND. [%s]", itemMaterial));
                        break;
                    }

                    ItemStack itemStack = new ItemStack(itemMaterial);
                    itemStack.setAmount(Integer.parseInt(count));

                    p.getInventory().addItem(itemStack);
                }
                MainClass.announceAll(alias);
            }
            case "everyone" -> {
                sendAllCommand(command.substring(9));
            }
            case "effect" -> {
                PotionEffectType type = PotionEffectType.getByName(args[1]);

                if(type == null){

                    MainClass.debugLog(String.format("EFFECT COULD NOT BE FOUND. [%s]", args[1]));

                    return;
                }

                PotionEffect potionEffect = new PotionEffect(type, Integer.parseInt(args[2]) * 20, Integer.parseInt(args[3]));

                for (Player p : MainClass.getPlayers()){
                    p.addPotionEffect(potionEffect);
                }
            }

            case "effectAll" -> {
                PotionEffectType type = PotionEffectType.getByName(args[1]);

                if(type == null){

                    MainClass.debugLog(String.format("ALLEFFECT COULD NOT BE FOUND. [%s]", args[1]));

                    return;
                }

                PotionEffect potionEffect = new PotionEffect(type, Integer.parseInt(args[2]) * 20, Integer.parseInt(args[3]));

                for (Player p : MainClass.getPlayers()){
                    for(Entity e : p.getWorld().getEntities()){
                        if(e instanceof Player plr){
                            if(!MainClass.shouldBeAffected(plr)) continue;
                        }

                        if(e instanceof LivingEntity living){
                            living.addPotionEffect(potionEffect);
                        }
                    }
                }
            }
            default -> sendCommand(command);
        }
    }

    public static void runCustom(String c) {
        CustomEvent ClassToRun = null;
        try {
            ClassToRun = classLoader
                    .loadClass("com.github.risbun.minetwitch.customscript." + c)
                    .asSubclass(CustomEvent.class).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            MainClass.debugLog(String.format("Error loading CustomEvent:\n[%s]", e));
        }

        if(ClassToRun == null) return;

        AnnounceLevel level = ClassToRun.getAnnounceLevel();

        if(level != null){
            if(level.equals(AnnounceLevel.Start) || level.equals(AnnounceLevel.Both)) ClassToRun.announceStart();
        }

        if(ClassToRun.run()){
            getScheduler().scheduleSyncDelayedTask(p, revertClassToRun(ClassToRun, level), ClassToRun.delay());
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

    public static void sendCommand(String command){
        getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public static void sendAllCommand(String command){
        for(Player p : MainClass.getPlayers()){
            getServer().dispatchCommand(Bukkit.getConsoleSender(), String.format("execute at %s run %s", p.getName(), command));
        }
    }
}
