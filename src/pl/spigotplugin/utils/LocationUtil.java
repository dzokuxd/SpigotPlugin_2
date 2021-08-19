package pl.spigotplugin.utils;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.LinkedList;
import java.util.List;

public class LocationUtil {

    public static List<Player> getPlayersInRadius(Location location, int size) {
        List<Player> players = new LinkedList<>();
        for (Player p : location.getWorld().getPlayers()) {
            if (location.distance(p.getLocation()) <= size) {
                players.add(p);
            }
        }
        return players;
    }

    public static boolean isSameLocation(final Location location, final Location location1) {
        return location.getWorld().equals(location1.getWorld()) && location.getBlockX() == location1.getBlockX() && location.getBlockY() == location1.getBlockY() && location.getBlockZ() == location1.getBlockZ();
    }

    public static boolean shouldContinueEvent(final PlayerMoveEvent event) {
        return !event.isCancelled() && !isSameLocation(event.getFrom(), event.getTo()) && event.getPlayer().getGameMode() != GameMode.SPECTATOR;
    }
}
