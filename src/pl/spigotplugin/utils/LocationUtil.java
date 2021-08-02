package pl.spigotplugin.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

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
}
