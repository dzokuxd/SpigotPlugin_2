package pl.spigotplugin.managers;

import org.bukkit.entity.Player;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.objects.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private static final Map<String, User> users = new ConcurrentHashMap<>();

    public static User getUser(String name) {
        return users.get(name.toLowerCase());
    }

    public static Map<String, User> getUsers1() { return UserManager.users; }

    public static User getUser(Player p) {
        return users.get(p.getName().toLowerCase());
    }

    public static User createrUser(Player p) {
        User u = new User(p);
        users.put(p.getName().toLowerCase(), u);
        TopsManager.add(u);
        return u;
    }

    public static void loadUsers() {
        try {
            ResultSet rs = SpigotPlugin.getMySQL().select("users");
            while (rs.next()) {
                User u = new User(rs);
                u.setRankType(RankType.valueOf(rs.getString("rankType")));
                users.put(u.getName().toLowerCase(), u);
                TopsManager.add(u);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

