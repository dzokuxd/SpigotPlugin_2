package pl.spigotplugin.utils;

import org.bukkit.Location;
import pl.spigotplugin.configs.Config;

public class CuboidUtil
{
    public static boolean isSpawn(Location location) {
        String cuboidCenter = ("world@0@100@0@0@0");
        int spawnRegionSize = (Config.REGION_SIZE_SPAWN);
        Location center = LocationParser.parseStringToLocation(cuboidCenter);
        if (location.getWorld().getName().equals(center.getWorld().getName())) {
            int centermax_x = (int)(center.getX() + spawnRegionSize);
            int centermin_x = (int)(center.getX() - spawnRegionSize);
            int centermax_z = (int)(center.getZ() + spawnRegionSize);
            int centermin_z = (int)(center.getZ() - spawnRegionSize);
            return location.getX() < centermax_x && location.getX() >= centermin_x && location.getZ() < centermax_z && location.getZ() >= centermin_z;
        }
        return false;
    }

    public static boolean isOutsideSpawn(Location location) {
        String cuboidCenter = ("world@0@100@0@0@0");
        int outsideSpawnRegionSize = (Config.REGION_SIZE_OUTSITE);
        Location center = LocationParser.parseStringToLocation(cuboidCenter);
        if (location.getWorld().getName().equals(center.getWorld().getName())) {
            int centermax_x = (int)(center.getX() + outsideSpawnRegionSize);
            int centermin_x = (int)(center.getX() - outsideSpawnRegionSize);
            int centermax_z = (int)(center.getZ() + outsideSpawnRegionSize);
            int centermin_z = (int)(center.getZ() - outsideSpawnRegionSize);
            return location.getX() < centermax_x && location.getX() >= centermin_x && location.getZ() < centermax_z && location.getZ() >= centermin_z;
        }
        return false;
    }
}
