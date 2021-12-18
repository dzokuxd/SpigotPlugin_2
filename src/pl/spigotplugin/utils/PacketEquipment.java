package pl.spigotplugin.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PacketEquipment
{
    public static void sendEquipment(Player player, int id, int slot, ItemStack item) {
        try {
            Class<?> packetClass = ReflectUtils.getCraftClass("PacketPlayOutEntityEquipment");
            Object packet = packetClass.newInstance();
            ReflectUtils.setValue(ReflectUtils.getField(packet.getClass(), "a"), packet, id);
            ReflectUtils.setValue(ReflectUtils.getField(packet.getClass(), "b"), packet, slot);
            ReflectUtils.setValue(ReflectUtils.getField(packet.getClass(), "c"), packet, ReflectUtils.getMethod(ReflectUtils.getBukkitClass("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class).invoke(null, item));
            Sender.sendPacket(player, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
