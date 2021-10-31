package pl.spigotplugin.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class PistonListener implements Listener {
    @EventHandler
    public void handle(BlockPistonRetractEvent e) {
        BlockFace bf = e.getDirection();
        for (int i = 1; i <= 15; ++i) {
            if (e.getBlock().getRelative(bf, i).getType() == Material.SPONGE) {
                e.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void handle(BlockPistonExtendEvent e) {
        BlockFace bf = e.getDirection();
        for (int i = 1; i <= 15; ++i) {
            if (e.getBlock().getRelative(bf, i).getType() == Material.SPONGE) {
                e.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void handle(BlockFromToEvent e) {
        if (e.getBlock().getType() == Material.SPONGE) {
            e.setCancelled(true);
        }
    }
}
