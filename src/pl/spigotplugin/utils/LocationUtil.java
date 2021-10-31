package pl.spigotplugin.utils;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Guild;

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

    public static int getDistanceFromBorder(final Location loc) {
        final int x = loc.getBlockX();
        final int z = loc.getBlockZ();
        final int distWest = Math.abs(-statues.BORDER_WORLD - x);
        final int distEast = Math.abs(statues.BORDER_WORLD - x);
        final int distNorth = Math.abs(-statues.BORDER_WORLD - z);
        final int distSouth = Math.abs(statues.BORDER_WORLD - z);
        final int distX = Math.min(distWest, distEast);
        final int distZ = Math.min(distNorth, distSouth);
        return Math.min(distX, distZ);
    }
}
