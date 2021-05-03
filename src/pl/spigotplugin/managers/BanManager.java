package pl.spigotplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.objects.user.Ban;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class BanManager {
    private static ConcurrentHashMap<String, Ban> bans;

    static {
        BanManager.bans = new ConcurrentHashMap<>();
    }

    public static Ban getBan(String name) {
        for (Ban ban : BanManager.bans.values()) {
            if (ban.getName().equalsIgnoreCase(name)) {
                return ban;
            }
        }
        return null;
    }

    public static Ban getBan(Player player) {
        for (Ban ban : BanManager.bans.values()) {
            if (ban.getName().equalsIgnoreCase(player.getName())) {
                return ban;
            }
        }
        return null;
    }

    public static void addBan(String name, Ban ban) {
        BanManager.getBans().put(name, ban);
        Player p = Bukkit.getPlayer(name);
        if (p != null) {
            String reason = "&cTwoje konto zostalo zbanowane!\n" +
                    "" +
                    "\n&cZbanowal: " +ban.getAdmin() +
                    "\n&cPowod: "+ban.getReason() +
                    "\n&cWygasa: "+ ((ban.getTime() == 0L) ? "&cNigdy!" : "&cza " + DataUtil.secondsToString(ban.getTime())) +
                    "\n" +
                    "\nMozesz kupic unbana" +
                    "\n"+ Config.IP+"/sklep";
            p.kickPlayer(ChatUtil.color(reason));
        }
    }

    public static void unban(Ban ban) {
        BanManager.getBans().remove(ban.getName());
        SpigotPlugin.getMySQL().update("DELETE FROM bans WHERE name ='" + ban.getName() + "'");
    }
    public static void unbanAll() {
        for (Ban ban : BanManager.getBans().values()) {
            unban(ban);
        }
    }

    public static void loadBans() {
        try {
            ResultSet rs = SpigotPlugin.getMySQL().select("bans");
            while (rs.next()) {
                Ban b = new Ban(rs);
                if (b.getTime() != 0L && b.getTime() < System.currentTimeMillis()) {
                    SpigotPlugin.getMySQL().update("DELETE FROM bans WHERE name ='" + b.getName() + "'");
                    continue;
                }
                BanManager.bans.put(b.getName(), b);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, Ban> getBans() {
        return BanManager.bans;
    }
}

