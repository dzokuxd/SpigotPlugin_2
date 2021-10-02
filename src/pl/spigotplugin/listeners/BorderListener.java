package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.spigotplugin.configs.Config;

public class BorderListener implements Listener {
    public static void setBorder() {
        World w = Bukkit.getWorld("world");
        WorldBorder wb = w.getWorldBorder();
        Location loc = w.getSpawnLocation();
        wb.setCenter(loc);
        wb.setSize(Config.BORDER_WORLD * 2);
    }

    @EventHandler
    public static void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getTo().getBlockX() > Config.BORDER_WORLD || event.getTo().getBlockX() < -Config.BORDER_WORLD || event.getTo().getBlockZ() > Config.BORDER_WORLD || event.getTo().getBlockZ() < -Config.BORDER_WORLD) {
            event.setTo(event.getFrom());
            player.sendMessage("&cOsiagnales granice swiata!" + " (" + Config.BORDER_WORLD + " kratek)");
        }
        if (player.getWorld().getName().equals("gtp") && (event.getTo().getBlockX() > Config.BORDER_GTP || event.getTo().getBlockX() < -Config.BORDER_GTP || event.getTo().getBlockZ() > Config.BORDER_GTP || event.getTo().getBlockZ() < -Config.BORDER_GTP)) {
            event.setTo(event.getFrom());
            player.sendMessage("&cOsiagnales granice swiata!" + " (" + Config.BORDER_GTP + " kratek)");
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getName().equals("world") && e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL) && (e.getTo().getX() > Config.BORDER_WORLD || e.getTo().getX() < -Config.BORDER_WORLD || e.getTo().getZ() > Config.BORDER_WORLD || e.getTo().getZ() < -Config.BORDER_WORLD)) {
            e.setCancelled(true);
            p.sendMessage("&cOsiagnales granice swiata!" + " (" + Config.BORDER_WORLD + " kratek)");
        }
        if (p.getWorld().getName().equals("gtp") && e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL) && (e.getTo().getX() > Config.BORDER_GTP - 10.0 || e.getTo().getX() < -Config.BORDER_GTP - 10.0 || e.getTo().getZ() > Config.BORDER_GTP - 10.0 || e.getTo().getZ() < -Config.BORDER_GTP - 10.0)) {
            e.setCancelled(true);
            p.sendMessage("&cOsiagnales granice netheru!" + " (" + Config.BORDER_GTP + " kratek)");
        }
    }
}