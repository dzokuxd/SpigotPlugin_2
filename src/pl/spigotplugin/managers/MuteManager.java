package pl.spigotplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.objects.user.Mute;
import pl.spigotplugin.utils.DataUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class MuteManager {
    public static ConcurrentHashMap<String, Mute> mutes;

    static {
        MuteManager.mutes = new ConcurrentHashMap<String, Mute>();
    }

    public static Mute getMute(String name) {
        for (Mute mute : MuteManager.mutes.values()) {
            if (mute.getName().equalsIgnoreCase(name)) {
                return mute;
            }
        }
        return null;
    }

    public static Mute getMute(Player player) {
        for (Mute mute : MuteManager.mutes.values()) {
            if (mute.getName().equalsIgnoreCase(player.getName())) {
                return mute;
            }
        }
        return null;
    }

    public static void addMute(String name, Mute mute) {
        getMutes().put(name, mute);
        Player p = Bukkit.getPlayer(name);
        if (p != null) {
            p.sendMessage("&8\u00bb &cZostales wyciszony przez &7" + mute.getAdmin() + "&c, " + ((mute.getTime() == 0L) ? "na zawsze" : ("&cwygasa za: &7" + DataUtil.secondsToString(mute.getTime()))) + "&c. Powod: &7" + mute.getReason());
        }
    }

    public static void unmute(Mute mute) {
        getMutes().remove(mute.getName());
        SpigotPlugin.getMySQL().update("DELETE FROM mutes WHERE name ='" + mute.getName() + "'");
    }

    public static void unmuteAll() {
        for (Mute mute : getMutes().values()) {
            unmute(mute);
        }
    }

    public static void loadMutes() {
        try {
            ResultSet rs = SpigotPlugin.getMySQL().select("mutes");
            while (rs.next()) {
                Mute m = new Mute(rs);
                if (m.getTime() != 0L && m.getTime() < System.currentTimeMillis()) {
                    SpigotPlugin.getMySQL().update("DELETE FROM mutes WHERE name ='" + m.getName() + "'");
                } else {
                    MuteManager.mutes.put(m.getName(), m);
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, Mute> getMutes() {
        return MuteManager.mutes;
    }
}