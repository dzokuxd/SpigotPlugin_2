package pl.spigotplugin.objects.guild;

import com.google.common.reflect.TypeToken;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.helper.JSONHelper;
import pl.spigotplugin.holder.SaveHolder;
import pl.spigotplugin.mysql.MySQLUtil;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.LocationParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Guild {
    private final String tag;
    private final String name;
    private String leader;
    private String deputy;
    private final Region region;
    private Location home;
    private int playersLimit = 0;
    private final Set<UUID> invites = ConcurrentHashMap.newKeySet();
    private Set<String> members = ConcurrentHashMap.newKeySet();

    public String deleteCode = "";

    public Guild(ResultSet rs) throws SQLException {
        this.tag = rs.getString("tag");
        this.name = rs.getString("leader");
        this.leader = rs.getString("owner");
        this.deputy = rs.getString("deputy");
        this.playersLimit = rs.getInt("playerslimit");
        this.home = LocationParser.parseStringToLocation(rs.getString("home"));

        this.region = JSONHelper.GSON.fromJson(rs.getString("region"), Region.class);
        this.members = JSONHelper.GSON.fromJson(rs.getString("members"), new TypeToken<Set<String>>(){}.getType());
    }

    public Guild(String tag, String name, Player leader, Location home) {
        this.tag = tag;
        this.name = name;
        this.leader = leader.getName();
        this.deputy = "Brak";
        this.home = home;
        this.playersLimit = 15;
        this.members.add(leader.getName());

        this.region = new Region(home, 30);

        this.members.add(leader.getName());

        insert();
    }

    private void insert() {
        Map<String, Object> data = new ConcurrentHashMap<>();

        data.put("tag", tag);
        data.put("name", name);
        data.put("leader", leader);
        data.put("deputy", deputy);
        data.put("home", home);
        data.put("region", JSONHelper.GSON.toJson(region));
        data.put("members", JSONHelper.GSON.toJson(members));

        MySQLUtil.insert("guilds", data);
    }

    public String getTag() { return tag; }

    public String getName() { return name; }

    public Set<String> getMembers() { return members; }

    public Region getRegion() { return region; }

    public boolean isMember(String p) { return this.getMembers().contains(p); }

    public boolean isLeader(String p) {
        return this.getLeader().equalsIgnoreCase(p);
    }

    public String getLeader() {
        return leader;
    }

    public String getDeputy() {
        return deputy;
    }

    public boolean isDeputy(String string) {
        return this.getLeader().equalsIgnoreCase(string) || this.getDeputy().equalsIgnoreCase(string);
    }

    public Location getHome() { return home; }

    public void setHome(Location home) {
        this.home = home;
        putForSave();
    }

    public void putForSave() {
        SaveHolder.GUILDS.putIfAbsent(this.tag, this);
    }

    public Set<UUID> getInvites() { return invites; }

    public int getPlayersLimit() {
        return this.playersLimit;
    }

    public void addPlayersLimit() {
        this.playersLimit = playersLimit + 1;
        putForSave();
    }

    public void addMember(String add) {
        this.members.add(add);
        putForSave();
    }

    public void setDeputy(String deputy){
        this.deputy = deputy;
        putForSave();
    }
    public void removeMember(String remove){
        this.members.remove(remove);
        putForSave();
    }
    public void setLeader(String leader){
        this.leader = leader;
        putForSave();
    }

    public void save() {
        Map<String, Object> data = new ConcurrentHashMap<>();

        data.put("leader", leader);
        data.put("deputy", deputy);
        data.put("home", home);
        data.put("region", JSONHelper.GSON.toJson(region));
        data.put("members", JSONHelper.GSON.toJson(members));

        MySQLUtil.save("guilds", "tag", tag, data);
    }
}
