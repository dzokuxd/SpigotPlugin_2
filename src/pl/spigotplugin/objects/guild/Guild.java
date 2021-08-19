package pl.spigotplugin.objects.guild;

import com.google.common.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.configs.GuildConfig;
import pl.spigotplugin.helper.JSONHelper;
import pl.spigotplugin.holder.SaveHolder;
import pl.spigotplugin.mysql.MySQLUtil;
import pl.spigotplugin.utils.LocationParser;
import pl.spigotplugin.utils.TimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Guild {

    private final String tag;
    private final String name;

    private String leader;
    private String deputy = "Brak";
    private final Region region;
    private Location home;
    private int playersLimit = 15;
    private long prolong;
    private boolean pvp = false;
    private boolean pvpAlly = false;
    private Set<String> ally = ConcurrentHashMap.newKeySet();
    private final Set<UUID> invites = ConcurrentHashMap.newKeySet();
    private Set<String> members = ConcurrentHashMap.newKeySet();

    public String deleteCode = "";

    public Guild(ResultSet rs) throws SQLException {
        this.tag = rs.getString("tag");
        this.name = rs.getString("name");
        this.leader = rs.getString("leader");
        this.deputy = rs.getString("deputy");
        this.playersLimit = rs.getInt("playerslimit");
        this.prolong = rs.getLong("prolong");

        this.region = JSONHelper.GSON.fromJson(rs.getString("region"), Region.class);
        this.members = JSONHelper.GSON.fromJson(rs.getString("members"), new TypeToken<Set<String>>(){}.getType());
        this.ally = JSONHelper.GSON.fromJson(rs.getString("ally"), new TypeToken<Set<String>>(){}.getType());
        this.home = LocationParser.parseStringToLocation(rs.getString("home"));
    }

    public Guild(String tag, String name, Player leader, Location home) {
        this.tag = tag;
        this.name = name;
        this.leader = leader.getName();
        this.home = home;
        this.members.add(leader.getName());

        this.prolong = System.currentTimeMillis() + TimeUtil.DAY.getTime(GuildConfig.CUBOID_PROLONG_START);
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
        data.put("home", LocationParser.parseLocationToString(home));
        data.put("prolong", prolong);
        data.put("playerslimit", playersLimit);
        data.put("ally", ally);
        data.put("region", JSONHelper.GSON.toJson(region));
        data.put("members", JSONHelper.GSON.toJson(members));

        MySQLUtil.insert("guilds", data);
    }

    public void save() {
        Map<String, Object> data = new ConcurrentHashMap<>();

        data.put("leader", leader);
        data.put("deputy", deputy);
        data.put("home", LocationParser.parseLocationToString(home));
        data.put("prolong", prolong);
        data.put("playerslimit", playersLimit);
        data.put("ally", ally);
        data.put("region", JSONHelper.GSON.toJson(region));
        data.put("members", JSONHelper.GSON.toJson(members));

        MySQLUtil.save("guilds", "tag", tag, data);
    }

    public String getTag() { return tag; }

    public String getName() { return name; }

    public Set<String> getMembers() { return members; }

    public Region getRegion() { return region; }

    public long getProlong() {
        return prolong;
    }

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
    public boolean isPvp() {
        return pvp;
    }

    public boolean isPvpAlly() {
        return pvpAlly;
    }

    public void setPvpAlly(boolean pvpAlly) {
        this.pvpAlly = pvpAlly;
    }

    public Location getHome() { return home; }

    public void setHome(Location home) {
        this.home = home;
        putForSave();
    }
    public void setProlong(long prolong) {
        this.prolong = prolong;
        putForSave();
    }

    public void putForSave() {
        SaveHolder.GUILDS.putIfAbsent(this.tag, this);
    }

    public Set<String> getAlly() {
        return ally;
    }

    public Set<UUID> getInvites() { return invites; }

    public int getPlayersLimit() {
        return this.playersLimit;
    }

    public boolean isExits() {
        return this.getProlong() > System.currentTimeMillis();
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
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
    public void message(String msg) {
        for (Player p : this.getOnlineMembers()) {
            p.sendMessage(msg);
        }
    }
    public Set<Player> getOnlineMembers() {
        Set<Player> online = new HashSet<Player>();
        for (String u : members) {
            Player player = Bukkit.getPlayer(u);
            if (player != null) {
                online.add(player);
            }
        }
        return online;
    }

    public List<String> getOnlineMembersNames() {
        List<String> online = new LinkedList<>();
        for (String u : members) {
            Player player = Bukkit.getPlayer(u);
            if (player != null) {
                online.add(player.getName());
            }
        }
        return online;
    }
}
