package pl.spigotplugin.objects.guild;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Region {
    private int x;
    private int z;
    private int size;

    public Region() {
    }

    Region(int x, int z, int size) {
        this.x = x;
        this.z = z;
        this.size = size;
    }

    Region(Location location, int size) {
        this(location.getBlockX(), location.getBlockZ(), size);
    }

    public boolean isInCuboid(Location loc) {

        if (!loc.getWorld().getName().equals("world")) {
            return false;
        }

        int distancex = Math.abs(loc.getBlockX() - this.x);
        int distancez = Math.abs(loc.getBlockZ() - this.z);

        return distancex <= this.getSize() && distancez <= this.getSize();
    }

    public boolean isInCuboidByLoc(Location loc) {

        if (!loc.getWorld().getName().equals("world")) {
            return false;
        }

        int distancex = Math.abs(loc.getBlockX() - this.getX());
        int distancez = Math.abs(loc.getBlockZ() - this.getZ());
        return distancex - 1 <= this.getSize() && distancez - 1 <= this.getSize();
    }

    public boolean isInCentrum(Location loc, int top, int down, int wall) {
        Location c = this.getLocation().clone();
        return c.getBlockY() - down <= loc.getBlockY() && c.getBlockY() + top >= loc.getBlockY() &&
                loc.getBlockX() <= c.getBlockX() + wall
                && loc.getBlockX() >= c.getBlockX() - wall
                && loc.getBlockZ() <= c.getBlockZ() + wall
                && loc.getBlockZ() >= c.getBlockZ() - wall;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld("world"), this.getX(), 30, this.getZ());
    }

    public void addSize(int size) {
        this.size += size;
    }
}