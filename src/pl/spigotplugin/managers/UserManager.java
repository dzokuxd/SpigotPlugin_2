package pl.spigotplugin.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.objects.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class UserManager {
    private static ConcurrentHashMap<String, User> users;

    static {
        users = new ConcurrentHashMap<String, User>();
    }

    public static User getUser(String name) {
        for (User u : UserManager.users.values()) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }

    public static User getUser(Player p) {
        for (User u : UserManager.users.values()) {
            if (u.getName().equalsIgnoreCase(p.getName())) {
                return u;
            }
        }
        return null;
    }

    public static User createrUser(Player p) {
        User u = new User(p);
        UserManager.users.put(p.getName(), u);
        return u;
    }

    public static boolean canPlaceByBorder(Location loc) {
        return Math.abs(Config.BORDER_WORLD - loc.getBlockX()) >= 10 && Math.abs(Config.BORDER_WORLD - loc.getBlockZ()) >= 10 && Math.abs(-Config.BORDER_WORLD - loc.getBlockX()) >= 10 && Math.abs(-Config.BORDER_WORLD - loc.getBlockZ()) >= 10 && Math.abs(-Config.BORDER_WORLD - loc.getBlockX()) >= 10 && Math.abs(Config.BORDER_WORLD - loc.getBlockZ()) >= 10 && Math.abs(Config.BORDER_WORLD - loc.getBlockX()) >= 10 && Math.abs(-Config.BORDER_WORLD - loc.getBlockZ()) >= 10;
    }

    public static void loadUsers() {
        try {
            ResultSet rs = SpigotPlugin.getMySQL().query("SELECT * FROM `{P}users`");
            while (rs.next()) {
                User u = new User(rs);
                UserManager.users.put(u.getName(), u);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, User> getUsers() {
        return UserManager.users;
    }
}

