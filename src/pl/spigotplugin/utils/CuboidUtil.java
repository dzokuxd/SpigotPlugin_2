package pl.spigotplugin.utils;

import org.bukkit.Location;
import pl.spigotplugin.configs.statues;

public class CuboidUtil
{
    public static boolean isSpawn(Location location) {
        String cuboidCenter = ("world@0@256@0@0@0");
        int spawnRegionSize = (statues.REGION_SIZE_SPAWN);
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
        String cuboidCenter = ("world@0@256@0@0@0");
        int outsideSpawnRegionSize = (statues.REGION_SIZE_OUTSITE);
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
    public static boolean cuboid1(Location location) {
        String cuboidCenter = ("world@400@256@400@0@0");
        int cuboid1 = (statues.REGION_SIZE_400cuboid);
        Location center = LocationParser.parseStringToLocation(cuboidCenter);
        if (location.getWorld().getName().equals(center.getWorld().getName())) {
            int centermax_x = (int)(center.getX() + cuboid1);
            int centermin_x = (int)(center.getX() - cuboid1);
            int centermax_z = (int)(center.getZ() + cuboid1);
            int centermin_z = (int)(center.getZ() - cuboid1);
            return location.getX() < centermax_x && location.getX() >= centermin_x && location.getZ() < centermax_z && location.getZ() >= centermin_z;
        }
        return false;
    }
    public static boolean cuboid2(Location location) {
        String cuboidCenter = ("world@-400@256@-400@0@0");
        int cuboid2 = (statues.REGION_SIZE_minus400cuboid);
        Location center = LocationParser.parseStringToLocation(cuboidCenter);
        if (location.getWorld().getName().equals(center.getWorld().getName())) {
            int centermax_x = (int)(center.getX() + cuboid2);
            int centermin_x = (int)(center.getX() - cuboid2);
            int centermax_z = (int)(center.getZ() + cuboid2);
            int centermin_z = (int)(center.getZ() - cuboid2);
            return location.getX() < centermax_x && location.getX() >= centermin_x && location.getZ() < centermax_z && location.getZ() >= centermin_z;
        }
        return false;
    }
}
