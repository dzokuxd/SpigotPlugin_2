package pl.spigotplugin.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class ChatUtil {

    private static final DecimalFormat df;
    private static boolean state;
    private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]*$");

    static {
        df = new DecimalFormat();
        ChatUtil.state = true;
    }

    public static String color(String s) {
        if (s == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }


    public static void sendHoverMessageCommand(Player p, String s1, String s2, String cmd) {
        IChatBaseComponent msg = IChatBaseComponent.ChatSerializer.a(color("{\"text\":\"" + s1 + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + s2 + "\"}]}},\"clickEvent\":{\"action\":\"execute_command\",\"value\":\"" + cmd + "\"}}"));
        PacketPlayOutChat hover = new PacketPlayOutChat(msg);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(hover);
    }


    public static boolean isInteger(String string) {
        return Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
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

    public static String round(final double value, final int maxplaces, final int minplaces) {
        ChatUtil.df.setMaximumFractionDigits(maxplaces);
        ChatUtil.df.setMinimumFractionDigits(minplaces);
        return ChatUtil.df.format(value);
    }

    public static double xD(double value, int decimals) {
        double p = Math.pow(10, decimals);
        return Math.round(value * p) / p;
    }

    public static boolean isAlphaNumeric(String s) {
        return pattern.matcher(s).matches();
    }
}
