package pl.spigotplugin.objects.guild;

import com.google.common.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.configs.guild;
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
    public int id;
    private int points = 0;
    private int kills = 0;
    private int deaths = 0;
    private int life = 2;
    private int hp = 50;
    public String regen = "";
    private int blocksToRegen = 0;
    private int gold = 0;
    private boolean startedRegen = false;
    private boolean needsaveregen = false;
    private long hpLastAttack = System.currentTimeMillis() + TimeUtil.HOUR.getTime(24);
    private Set<String> GuildWar = new HashSet<>();
    private final Set<Guild> allyInvites = new HashSet<>();
    private long createTime = System.currentTimeMillis();
    private long lastExplodeTime;
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
        this.points = rs.getInt("points");
        this.deaths = rs.getInt("deaths");
        this.kills = rs.getInt("kills");
        this.createTime = rs.getLong("createTime");
        this.life = rs.getInt("life");
        this.hp = rs.getInt("hp");
        this.gold = rs.getInt("gold");
        this.regen = rs.getString("regen");
        this.hpLastAttack = rs.getLong("hpLastAttack");
        this.GuildWar = new HashSet<>();

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
        this.lastExplodeTime = 0L;

        this.prolong = System.currentTimeMillis() + TimeUtil.DAY.getTime(guild.CUBOID_PROLONG_START);
        this.region = new Region(home, 20);
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
        data.put("ally", JSONHelper.GSON.toJson(ally));
        data.put("kills", kills);
        data.put("deaths", deaths);
        data.put("points", points);
        data.put("createTime", createTime);
        data.put("hp", hp);
        data.put("life", life);
        data.put("gold", gold);
        data.put("regen", regen);
        data.put("GuildWar", GuildWar);
        data.put("hpLastAttack", hpLastAttack);
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
        data.put("ally", JSONHelper.GSON.toJson(ally));
        data.put("kills", kills);
        data.put("deaths", deaths);
        data.put("points", points);
        data.put("createTime", createTime);
        data.put("hp", hp);
        data.put("life", life);
        data.put("gold", gold);
        data.put("regen", regen);
        data.put("GuildWar", GuildWar);
        data.put("hpLastAttack", hpLastAttack);
        data.put("region", JSONHelper.GSON.toJson(region));
        data.put("members", JSONHelper.GSON.toJson(members));

        MySQLUtil.save("guilds", "tag", tag, data);
    }

    public void saveSync() {
        Map<String, Object> data = new ConcurrentHashMap<>();

        data.put("leader", leader);
        data.put("deputy", deputy);
        data.put("home", LocationParser.parseLocationToString(home));
        data.put("prolong", prolong);
        data.put("playerslimit", playersLimit);
        data.put("ally", JSONHelper.GSON.toJson(ally));
        data.put("kills", kills);
        data.put("deaths", deaths);
        data.put("points", points);
        data.put("createTime", createTime);
        data.put("hp", hp);
        data.put("life", life);
        data.put("gold", gold);
        data.put("regen", regen);
        data.put("GuildWar", GuildWar);
        data.put("hpLastAttack", hpLastAttack);
        data.put("region", JSONHelper.GSON.toJson(region));
        data.put("members", JSONHelper.GSON.toJson(members));

        MySQLUtil.saveSync("guilds", "tag", tag, data);
    }

    public String getTag() { return tag; }

    public String getName() { return name; }

    public Set<String> getMembers() { return members; }

    public Region getRegion() { return region; }

    public long getProlong() {
        return prolong;
    }

    public boolean isMember(String p) { return this.getMembers().contains(p); }

    public boolean isLeader(String p) {return this.getLeader().equalsIgnoreCase(p);}

    public String getLeader() {
        return leader;
    }

    public String getDeputy() {
        return deputy;
    }

    public boolean isDeputy(String string) {return this.getLeader().equalsIgnoreCase(string) || this.getDeputy().equalsIgnoreCase(string);}

    public boolean isPvp() {
        return pvp;
    }

    public boolean isPvpAlly() {
        return pvpAlly;
    }

    public void setPvpAlly(boolean pvpAlly) {
        this.pvpAlly = pvpAlly;
    }

    public Set<Guild> getAllyinvites() {
        return allyInvites;
    }


    public Location getHome() { return home; }

    public void addSize(int size) {
        this.getRegion().addSize(size);
        putForSave();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setHome(Location home) {
        this.home = home;
        putForSave();
    }
    public void setLife(int life) {
        this.life = life;
        putForSave();
    }

    public long getHpLastAttack() {
        return this.hpLastAttack;
    }


    public void setHpLastAttack (long hpLastAttack) {
        this.hpLastAttack = hpLastAttack;
    }
    public void setProlong(long prolong) {
        this.prolong = prolong;
        putForSave();
    }
    public void removeAlly(String ally) {
        this.ally.remove(ally);
        putForSave();
    }
    public boolean isProtected() {
        return this.getCreateTime() + TimeUtil.HOUR.getTime(guild.CUBOID_PROTECTION_HOWHOUR) > System.currentTimeMillis();
    }
    public void saveGold(int gold) {
        this.gold = gold;
        putForSave();
    }
    public void saveRegen(String regen) {
        this.regen = regen;
    }
    public void addAlly(String ally) {
        this.ally.add(ally);
        putForSave();
    }

    public String getRegen() { return regen; }

    public void setRegen(String regen) {
        this.regen = regen;
        putForSave();
    }

    public int getBlocksToRegen() {
        return blocksToRegen;
    }

    public void setBlocksToRegen(int blocksToRegen) {
        this.blocksToRegen = blocksToRegen;
    }

    public boolean isNeedsaveregen() {
        return needsaveregen;
    }

    public void setNeedsaveregen(boolean needsaveregen) {
        this.needsaveregen = needsaveregen;
    }

    public boolean isStartedRegen() {
        return startedRegen;
    }

    public void setStartedRegen(boolean startedRegen) {
        this.startedRegen = startedRegen;
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

    public Set<String> getGuildWar() { return GuildWar; }

    public List<String> getwojnatags() {
        List<String> tags = new LinkedList<>();
        for (String s : GuildWar) {
            String[] ss = s.split("@");
            tags.add(ss[0]);
        }
        return tags;
    }

    public String getALlyList() {
        String s = "&7\u00bb &6Sojusze: ";
        if (this.getAlly().size() == 0) {
            s += "&c\u2716";
        } else {
            for (String name : this.getAlly()) {
                s += "&8, &c" + name;

            }
        }
        return s;
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

    public long getprottime() {return this.getCreateTime() + TimeUtil.HOUR.getTime(guild.CUBOID_PROTECTION_HOWHOUR);}

    public long getLastExplodeTime() {
        return lastExplodeTime;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {this.kills = kills;}

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {this.deaths = deaths;}

    public int getLife() {
        return life;
    }

    public int getHp() {
        return hp;
    }

    public void setLastExplodeTime(long lastExplodeTime) {
        this.lastExplodeTime = lastExplodeTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {this.points = points;}

    public int getGold() {return gold;}

    public void setGold(int gold) {this.gold = gold;}
}
