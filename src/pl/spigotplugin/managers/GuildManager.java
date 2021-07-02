package pl.spigotplugin.managers;

import org.bukkit.entity.Player;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.objects.guild.Guild;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuildManager {
    private static final Map<String, Guild> guilds = new ConcurrentHashMap<>();

    public static Guild getGuild(String str) {
        return guilds.get(str.toUpperCase());
    }

    public static Guild getGuild(Player p) {
        for (Guild g : GuildManager.guilds.values()) {
            if (g.isMember(p)) {
                return g;
            }
        }
        return null;
    }
    public static void loadGuilds() {
        try {
            ResultSet rs = SpigotPlugin.getMySQL().query("SELECT * FROM `{P}guilds`");
            while (rs.next()) {
                /*Guild g = new Guild(rs, name);
                GuildManager.guilds.put(g.getTag(), g);
                //*RankingManager.addRanking(g);*/
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
