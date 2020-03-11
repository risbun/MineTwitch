package se.laxmine.minetwitch;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Objects;

import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getServer;
import static se.laxmine.minetwitch.Main.*;

class CommandParser {
    void send(String alias, String command){
        if(!command.startsWith("custom")){
            getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            if(command.startsWith("give")){
                Bukkit.broadcastMessage(alias);
                getServer().dispatchCommand(Bukkit.getConsoleSender(), "title @a title \"" +alias+"\"");
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
                getServer().dispatchCommand(Bukkit.getConsoleSender(), "replaceitem entity @p armor.head minecraft:carved_pumpkin{Enchantments:[{id:binding_curse,lvl:1}]}");

                Runnable removePumpkin = () -> getServer().dispatchCommand(Bukkit.getConsoleSender(), "replaceitem entity @p armor.head minecraft:air");

                getScheduler().scheduleSyncDelayedTask(p, removePumpkin, 600L);
            }
        }
    }
}
