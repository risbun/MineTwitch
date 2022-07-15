package com.github.risbun.minetwitch.customevents;

import com.github.risbun.minetwitch.MainClass;
import com.github.risbun.minetwitch.CustomEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Objects;

public class YeetBlocks implements CustomEvent {
    @Override
    public boolean run() {
        for (Player p : MainClass.getPlayers()) {

            Block target = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

            for (int y = -3; y < 5; y++) {
                for (int x = -2; x < 3; x++) {
                    for (int z = -2; z < 3; z++) {
                        Block b = target.getLocation().add(x, (y * -1), z).getBlock();

                        if(b.getType() == Material.AIR) continue;

                        FallingBlock fb = Objects.requireNonNull(b.getLocation().getWorld()).spawnFallingBlock(b.getLocation().subtract(new Vector(-0.5, 0, -0.5)), b.getBlockData());

                        b.setType(Material.AIR);

                        fb.setVelocity(new Vector(0, 2 + y, 0));
                        fb.setDropItem(false);
                    }
                }
            }
        }
        return false;
    }
}
