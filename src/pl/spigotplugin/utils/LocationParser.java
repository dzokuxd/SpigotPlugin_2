package pl.spigotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationParser
{
    public static String parseLocationToString(Location location) {
        String w = location.getWorld().getName();
        String x = String.valueOf(location.getX());
        String y = String.valueOf(location.getY());
        String z = String.valueOf(location.getZ());
        return w + "@" + x + "@" + y + "@" + z + "@" + location.getYaw() + "@" + location.getPitch();
    }

    public static Location parseStringToLocation(String str) {
        if (str.equals("")) {
            return null;
        }
        String[] splt = str.split("@");
        String w = splt[0];
        double x = Double.parseDouble(splt[1]);
        double y = Double.parseDouble(splt[2]);
        double z = Double.parseDouble(splt[3]);
        float yaw = Float.parseFloat(splt[4]);
        float pitch = Float.parseFloat(splt[5]);
        return new Location(Bukkit.getWorld(w), x, y, z, yaw, pitch);
    }
}
