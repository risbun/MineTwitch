package se.laxmine.minetwitch;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

import static se.laxmine.minetwitch.Main.*;

public class Events implements Listener
{
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) return;
        Player p = event.getPlayer();

        //All blocks have gravity
        if(activeCommand.equals("All blocks have gravity")){
            List<Block> blocks = getNearbyBlocks(p.getLocation(), 5);

            for (Block b : blocks) {
                if (b.getRelative(BlockFace.DOWN).getType().equals(Material.AIR) || b.getRelative(BlockFace.DOWN).getType().equals(Material.CAVE_AIR)) {
                    b.getWorld().spawnFallingBlock(b.getLocation().add(0.5, 0, 0.5), b.getBlockData());
                    b.setType(Material.AIR);
                }
            }
        }
    }

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    Block b = location.getWorld().getBlockAt(x, y, z);

                    if(!b.getType().equals(Material.CAVE_AIR) && !b.getType().equals(Material.AIR) && !b.isLiquid())
                        blocks.add(b);
                }
            }
        }
        return blocks;
    }
}