package com.github.risbun.minetwitch;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import static com.github.risbun.minetwitch.MainClass.plugin;
import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getServer;

public class EventManager {
    public static void Apply(String alias, String command){
        String[] args = command.split(" ");

        switch (args[0]) {
            case "command":
                runCustom(args[1]);
                break;
            case "give":
                String item = args[1];
                String count = args[2];
                for (Player p : MainClass.getPlayers()) {

                    Material itemMaterial = Material.matchMaterial(item);

                    if (itemMaterial == null) {
                        MainClass.debugLog(String.format("ITEM COULD NOT BE FOUND. [%s]", itemMaterial));
                        break;
                    }

                    ItemStack itemStack = new ItemStack(itemMaterial);
                    itemStack.setAmount(Integer.parseInt(count));

                    p.getInventory().addItem(itemStack);
                }
                MainClass.announceAll(alias);
                break;
            case "everyone":
                sendAllCommand(command.substring(9));
                break;
            case "effect":
                PotionEffectType type = PotionEffectType.getByName(args[1]);

                if(type == null){

                    MainClass.debugLog(String.format("EFFECT COULD NOT BE FOUND. [%s]", args[1]));

                    return;
                }

                PotionEffect potionEffect = new PotionEffect(type, Integer.parseInt(args[2]) * 20, Integer.parseInt(args[3]));

                for (Player p : MainClass.getPlayers()){
                    p.addPotionEffect(potionEffect);
                }
                break;

            case "effectAll":
                PotionEffectType type_ = PotionEffectType.getByName(args[1]);

                if(type_ == null){

                    MainClass.debugLog(String.format("ALLEFFECT COULD NOT BE FOUND. [%s]", args[1]));

                    return;
                }

                PotionEffect potionEffect_ = new PotionEffect(type_, Integer.parseInt(args[2]) * 20, Integer.parseInt(args[3]));

                for (Player p : MainClass.getPlayers()){
                    for(Entity e : p.getWorld().getEntities()){
                        if(e instanceof Player){
                            if(!MainClass.shouldBeAffected((Player) e)) continue;
                        }

                        if(e instanceof LivingEntity){
                            ((LivingEntity) e).addPotionEffect(potionEffect_);
                        }
                    }
                }
                break;
            default:
                sendCommand(command);
        }
    }

    public static void runCustom(String c) {
        CustomEvent ClassToRun = null;
        try {
            ClassToRun = plugin.classLoader
                    .loadClass("com.github.risbun.minetwitch.customevents." + c)
                    .asSubclass(CustomEvent.class).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            MainClass.debugLog(String.format("Error loading CustomEvent:\n[%s]", e));
        }

        if(ClassToRun == null) return;

        if(ClassToRun.run()){
            getScheduler().scheduleSyncDelayedTask(plugin, revertClassToRun(ClassToRun), ClassToRun.delay());
        }
    }

    private static @NotNull Runnable revertClassToRun(CustomEvent ClassToRun){
        return ClassToRun::revert;
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
