package pl.spigotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class Sender
{
    private static final String packageName = Bukkit.getServer().getClass().getPackage().getName();
    private static final String version = Sender.packageName.substring(Sender.packageName.lastIndexOf(".") + 1);

    public static void sendPacket(Player player, Object... os) {
        sendPacket(new Player[] { player }, os);
    }

    public static void sendPacket(Player[] players, Object... os) {
        try {
            Class<?> packetClass = Class.forName("net.minecraft.server." + Sender.version + ".Packet");
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + Sender.version + ".entity.CraftPlayer");
            for (Player p : players) {
                Object cp = craftPlayer.cast(p);
                Object handle = craftPlayer.getMethod("getHandle", new Class[0]).invoke(cp);
                Object con = handle.getClass().getField("playerConnection").get(handle);
                Method method = con.getClass().getMethod("sendPacket", packetClass);
                for (Object o : os) {
                    if (o != null) {
                        method.invoke(con, o);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
