package se.laxmine.minetwitch;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Objects;

import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getServer;
import static se.laxmine.minetwitch.Main.*;

class CommandParser {
    void send(String alias, String command){
        if(!command.startsWith("custom")){
            sendCommand(command);
            if(command.startsWith("give")){
                Bukkit.broadcastMessage(alias);
                sendCommand("title @a title \"" +alias+"\"");
            }
        }else{
            String c = command.split(" ")[1];
            if(c.equals("gravity")){
                customCommand = c;
            }
            if(c.equals("burn")){
                for(Entity e : Bukkit.getOnlinePlayers()){
                    e.setFireTicks(100);
                }
            }
            if(c.equals("creeper")){
                for(Player p : Bukkit.getOnlinePlayers()){
                    Vector inverseDirectionVec = p.getLocation().getDirection().normalize().multiply(-2);
                    p.playSound(p.getLocation().add(inverseDirectionVec), Sound.ENTITY_CREEPER_PRIMED, 1, 1);
                }
            }
            if(c.equals("jump")){
                for(Player p : Bukkit.getOnlinePlayers()){

                    Block target = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

                    for(int y = 0; y < 5; y++){
                        for (int x = 0; x < 3; x++) {
                            for (int z = 0; z < 3; z++) {
                                Block b = target.getLocation().add(x, (y*-1), z).getBlock();
                                FallingBlock fb = Objects.requireNonNull(b.getLocation().getWorld()).spawnFallingBlock(b.getLocation().subtract(new Vector(-0.5, 0, -0.5)), b.getBlockData());

                                b.setType(Material.AIR);

                                fb.setVelocity(new Vector(0, 2, 0));
                            }
                        }
                    }
                }
            }
            if(c.equals("pumpkin")){
                sendCommand("replaceitem entity @a armor.head minecraft:carved_pumpkin{Enchantments:[{id:binding_curse,lvl:1}]}");

                Runnable removePumpkin = () -> sendCommand("replaceitem entity @a armor.head minecraft:air");

                getScheduler().scheduleSyncDelayedTask(p, removePumpkin, 600L);
            }
            if(c.equals("adventure")){
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.setGameMode(GameMode.ADVENTURE);
                }
                Runnable removeAdventure = () -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.setGameMode(GameMode.SURVIVAL);
                    }
                };

                getScheduler().scheduleSyncDelayedTask(p, removeAdventure, 600L);
            }
            if(c.equals("water")){
                sendAllCommand("fill ~2 ~2 ~2 ~-2 ~-2 ~-2 stone hollow");
                sendAllCommand("fill ~1 ~1 ~1 ~-1 ~-1 ~-1 water");
            }
            if(c.equals("portal")){
                sendAllCommand("fill ~-1 ~-1 ~ ~2 ~3 ~ minecraft:obsidian");
                sendAllCommand("fill ~1 ~ ~ ~ ~2 ~ minecraft:air");
                sendAllCommand("setblock ~ ~ ~ fire");
            }
            if (c.equals("stone")){
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (ItemStack i : p.getInventory()) {
                        if (i == null) continue;
                        String yes = i.getType().getKey().toString();
                        if (yes.contains("sword") || yes.contains("shovel") || yes.contains("axe")) {
                            String type = yes.split(":")[1].split("_")[1];
                            Material change = Material.getMaterial("STONE_" + type.toUpperCase());
                            i.setType(Objects.requireNonNull(change));
                        }
                    }
                }
            }
        }
    }

    void sendCommand(String command){
        getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }
    void sendAllCommand(String command){
        getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at @a run " + command);
    }
}
