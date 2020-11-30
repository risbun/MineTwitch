package com.github.risbun.minetwitch;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.risbun.minetwitch.Main.*;

public class Events implements Listener
{
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) return;
        Player p = event.getPlayer();

        //All blocks have gravity
        if(customCommand.equals("gravity")){
            List<Block> blocks = getNearbyBlocks(p.getLocation());

            for (Block b : blocks) {
                if (b.getRelative(BlockFace.DOWN).getType().equals(Material.AIR) || b.getRelative(BlockFace.DOWN).getType().equals(Material.CAVE_AIR)) {
                    b.getWorld().spawnFallingBlock(b.getLocation().add(0.5, 0, 0.5), b.getBlockData());
                    b.setType(Material.AIR);
                }
            }
        }
    }

    private static List<Block> getNearbyBlocks(Location location) {
        List<Block> blocks = new ArrayList<>();
        for(int x = location.getBlockX() - 5; x <= location.getBlockX() + 5; x++) {
            for(int y = location.getBlockY() - 5; y <= location.getBlockY() + 5; y++) {
                for(int z = location.getBlockZ() - 5; z <= location.getBlockZ() + 5; z++) {
                    Block b = Objects.requireNonNull(location.getWorld()).getBlockAt(x, y, z);

                    if(!b.getType().equals(Material.CAVE_AIR) && !b.getType().equals(Material.AIR) && !b.isLiquid())
                        blocks.add(b);
                }
            }
        }
        return blocks;
    }
}