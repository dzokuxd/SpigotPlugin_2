package pl.spigotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Reflection;

public class ParticleUtil {

    private static final Reflection.ConstructorInvoker c = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutWorldParticles"), String.class, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE);
    private static final Reflection.MethodInvoker sendPacket = Reflection.getMethod(Reflection.getMinecraftClass("PlayerConnection"), "sendPacket", Reflection.getMinecraftClass("Packet"));
    private static final Reflection.FieldAccessor<Object> playerConnection = Reflection.getSimpleField(Reflection.getMinecraftClass("EntityPlayer"), "playerConnection");
    private static final Reflection.MethodInvoker entityHandleMethod = Reflection.getMethod(Reflection.getCraftBukkitClass("entity.CraftEntity"), "getHandle", (Class<?>[])new Class[0]);

    public static void sendParticleToLocation(Location loc, ParticleType particle, float xOffset, float yOffset, float zOffset, float speed, int amount) {
        Object packet = ParticleUtil.c.invoke(particle.getName(), (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), xOffset, yOffset, zOffset, speed, amount);
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0])).length, i = 0; i < length; ++i) {
            Player p = onlinePlayers[i];
            if (loc.getWorld().equals(p.getWorld()) && p.getLocation().distance(loc) <= 50.0) {
                sendPacket(p, packet);
            }
        }
    }

    public static void sendPartileToPlayer(Player p, ParticleType particle, Location loc, float xOffset, float yOffset, float zOffset, float speed, int amount) {
        Object packet = ParticleUtil.c.invoke(particle.getName(), (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), xOffset, yOffset, zOffset, speed, amount);
        sendPacket(p, packet);
    }

    public static void sendPacket(Player player, Object... objects) {
        Object handle = getHandle(player);
        for (Object o : objects) {
            ParticleUtil.sendPacket.invoke(ParticleUtil.playerConnection.get(handle), o);
        }
    }
    public static Object getHandle(final Player p) {
        if (ParticleUtil.entityHandleMethod == null) {
            throw new IllegalArgumentException("HandleMethod can not be null!");
        }
        return ParticleUtil.entityHandleMethod.invoke(p);
    }

    public enum ParticleType {
        ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 21, "enchantmenttable");

        private final String name;

        ParticleType(final String s, final int n, final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}