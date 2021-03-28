package pl.spigotplugin.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ChatUtil {
    public static String color(String s) {
        if (s == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public static void sendHoverMessageCommand(Player p, String s1, String s2, String cmd) {
        IChatBaseComponent msg = IChatBaseComponent.ChatSerializer.a(color("{\"text\":\"" + s1 + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + s2 + "\"}]}},\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + cmd + "\"}}"));
        PacketPlayOutChat hover = new PacketPlayOutChat(msg);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(hover);
    }
    public static void sendHoverMessage(CommandSender p, String s1, String s2) {
        IChatBaseComponent msg = IChatBaseComponent.ChatSerializer.a(color("{\"text\":\"" + s1 + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + s2 + "\"}]}},\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"\"}}"));
        PacketPlayOutChat hover = new PacketPlayOutChat(msg);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(hover);
    }
    public static void giveItems(Player p, ItemStack... items) {
        Inventory i = p.getInventory();
        HashMap<Integer, ItemStack> notStored = i.addItem(items);
        for (Map.Entry<Integer, ItemStack> e : notStored.entrySet()) {
            p.getWorld().dropItemNaturally(p.getLocation(), e.getValue());
        }
    }
    public static void removeItems(Player p, ItemStack... items) {
        Inventory i = p.getInventory();
        HashMap<Integer, ItemStack> notStored = i.removeItem(items);
        for (Map.Entry<Integer, ItemStack> e : notStored.entrySet()) {
        }
    }
    public static boolean isInteger(String string) {
        return Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
    }
    public static ItemStack getPlayerHead(String name) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(name);
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public static void sendTitleMessage(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (title == null) {
            title = "";
        }
        if (subtitle == null) {
            subtitle = "";
        }

        title = title.replace("&", "\u00A7");
        subtitle = subtitle.replace("&", "\u00A7");

        CraftPlayer craftPlayer = (CraftPlayer) player;

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetTitle);

        IChatBaseComponent chatSubtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
        PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubtitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubtitle);
    }

    public static void sendActionBar(Player player, String s) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatUtil.color(s) + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
    }
    public static Material getMaterial(String materialName) {
        Material returnMaterial = null;
        if (isInteger(materialName)) {
            int id = Integer.parseInt(materialName);
            returnMaterial = Material.getMaterial(id);
        } else {
            returnMaterial = Material.matchMaterial(materialName);
        }
        return returnMaterial;
    }
}
