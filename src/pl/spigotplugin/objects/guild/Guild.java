package pl.spigotplugin.objects.guild;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Guild {
    private final String tag;
    private final String name;
    private final Set<String> members = ConcurrentHashMap.newKeySet();

    public Guild(ResultSet rs) throws SQLException {
        this.tag = rs.getString("tag");
        this.name = rs.getString("name");
    }
    public String getTag() { return tag; }

    public Set<String> getMembers() {
        return members;
    }

    public boolean isMember(Player p) { return this.getMembers().contains(p.getName()); }
}
