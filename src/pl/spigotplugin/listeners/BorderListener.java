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
import pl.spigotplugin.configs.statues;

public class BorderListener implements Listener {
    public static void setBorder() {
        World w = Bukkit.getWorld("world");
        WorldBorder wb = w.getWorldBorder();
        Location loc = w.getSpawnLocation();
        wb.setCenter(loc);
        wb.setSize(statues.BORDER_WORLD * 2);
    }

    @EventHandler
    public static void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getTo().getBlockX() > statues.BORDER_WORLD || event.getTo().getBlockX() < -statues.BORDER_WORLD || event.getTo().getBlockZ() > statues.BORDER_WORLD || event.getTo().getBlockZ() < -statues.BORDER_WORLD) {
            event.setTo(event.getFrom());
            player.sendMessage("&cOsiagnales granice swiata!" + " (" + statues.BORDER_WORLD + " kratek)");
        }
        if (player.getWorld().getName().equals("gtp") && (event.getTo().getBlockX() > statues.BORDER_GTP || event.getTo().getBlockX() < -statues.BORDER_GTP || event.getTo().getBlockZ() > statues.BORDER_GTP || event.getTo().getBlockZ() < -statues.BORDER_GTP)) {
            event.setTo(event.getFrom());
            player.sendMessage("&cOsiagnales granice swiata!" + " (" + statues.BORDER_GTP + " kratek)");
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getName().equals("world") && e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL) && (e.getTo().getX() > statues.BORDER_WORLD || e.getTo().getX() < -statues.BORDER_WORLD || e.getTo().getZ() > statues.BORDER_WORLD || e.getTo().getZ() < -statues.BORDER_WORLD)) {
            e.setCancelled(true);
            p.sendMessage("&cOsiagnales granice swiata!" + " (" + statues.BORDER_WORLD + " kratek)");
        }
        if (p.getWorld().getName().equals("gtp") && e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL) && (e.getTo().getX() > statues.BORDER_GTP - 10.0 || e.getTo().getX() < -statues.BORDER_GTP - 10.0 || e.getTo().getZ() > statues.BORDER_GTP - 10.0 || e.getTo().getZ() < -statues.BORDER_GTP - 10.0)) {
            e.setCancelled(true);
            p.sendMessage("&cOsiagnales granice netheru!" + " (" + statues.BORDER_GTP + " kratek)");
        }
    }
}