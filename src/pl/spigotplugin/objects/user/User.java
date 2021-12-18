package pl.spigotplugin.objects.user;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import pl.spigotplugin.enums.AchievmentTypeName;
import pl.spigotplugin.helper.JSONHelper;
import pl.spigotplugin.holder.SaveHolder;
import pl.spigotplugin.mysql.MySQLUtil;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemSerializer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User implements Comparable<User> {

    private String name;
    private long turboDrop = 0;
    private boolean autoMessages = true;
    private boolean privateMessages = true;
    private final List<Player> ignoreTell = new ArrayList<>();
    private final List<Player> ignoreTpa = new ArrayList<>();
    private final List<Player> tpa = new ArrayList<>();
    private BukkitTask currentTeleport;
    private long kit_mieso = 0;
    private long kit_test = 0;
    private long kit_start = 0;
    private long kit_vip = 0;
    private long kit_svip = 0;
    private int lvl = 1;
    private int exp = 0;
    private int wykStone = 0;
    private int wykObsidian = 0;
    private int koxy = 0;
    private int refile = 0;
    private int perly = 0;
    private int strzaly = 0;
    private int koxEaten = 0;
    private int refilEaten = 0;
    private int pearlThrown = 0;
    private int arrowsShoten = 0;
    private int coins = 0;
    private int easycase = 0;
    private int case611 = 0;
    private int kills = 0;
    private int asysty = 0;
    private int points = 1000;
    private int deaths = 0;
    private int ks = 0;
    private int maxks = 0;
    private long lastChat;
    private String guild = "";
    private String achievments = "0@0@0@0@0@0@0";
    private long time;
    private ItemStack[] enderchest = new ItemStack[0];
    private boolean incognito = false;
    private boolean inBeingChecked = false;
    private boolean isOnCuboid = false;
    private final Map<User, Long> lastKillers = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Material, Integer> drops = new ConcurrentHashMap<>();
    public String focused = "";

    public User(Player p) {
        this.name = p.getName();
        insert();
    }

    public User(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.turboDrop = rs.getLong("turboDrop");
        this.kit_start = rs.getLong("kit_start");
        this.kit_vip = rs.getLong("kit_vip");
        this.kit_svip = rs.getLong("kit_svip");
        this.lvl = rs.getInt("lvl");
        this.exp = rs.getInt("exp");
        this.wykStone = rs.getInt("wykStone");
        this.wykObsidian = rs.getInt("wykObsidian");
        this.drops = JSONHelper.jsonStringToDrops(rs.getString("drops"));
        this.koxy = rs.getInt("koxy");
        this.refile = rs.getInt("refile");
        this.perly = rs.getInt("perly");
        this.strzaly = rs.getInt("strzaly");
        this.koxEaten = rs.getInt("koxEaten");
        this.refilEaten = rs.getInt("refilEaten");
        this.pearlThrown = rs.getInt("pearlThrown");
        this.arrowsShoten = rs.getInt("arrowsShoten");
        this.coins = rs.getInt("coins");
        this.easycase = rs.getInt("easycase");
        this.case611 = rs.getInt("case611");
        this.kills = rs.getInt("kills");
        this.asysty = rs.getInt("asysty");
        this.points = rs.getInt("points");
        this.deaths = rs.getInt("deaths");
        this.ks = rs.getInt("ks");
        this.maxks = rs.getInt("maxks");
        this.lastChat = 0L;
        this.guild = rs.getString("guild");
        this.time = rs.getLong("time");
        this.enderchest = ItemSerializer.stringToItems(rs.getString("enderchest"));
    }

    public ConcurrentHashMap<Material, Integer> getDrops() {
        return drops;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        if (name == null) {
            return null;
        }

        return Bukkit.getPlayer(name);
    }

    public String getKDR() {
        if (this.kills == 0 || this.deaths == 0) {
            return "-";
        }
        return ChatUtil.round(this.kills / (double)this.deaths, 2, 2);
    }


    public Map<User, Long> getLastKillers() {
        return this.lastKillers;
    }

    public long getTurboDrop() { return turboDrop; }

    public void setTurboDrop(long turboDrop) { this.turboDrop = turboDrop; }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isAutoMessages() { return this.autoMessages; }

    public boolean isPrivateMessages() { return this.privateMessages; }

    public void setAutoMessages(boolean autoMessages) { this.autoMessages = autoMessages; }

    public List<Player> getTpa() { return tpa; }

    public long getKit_mieso() { return kit_mieso; }

    public long getKit_Test() { return kit_test; }

    public void setKit_mieso(long kit_mieso) { this.kit_mieso = kit_mieso; }

    public void setKit_test(long kit_test) { this.kit_test = kit_test; }

    public long getKit_start() { return kit_start; }

    public long getKit_vip() { return kit_vip; }

    public long getKit_svip() { return kit_svip;}

    public boolean isChat() {
        return System.currentTimeMillis() > this.lastChat;
    }

    public long getLastChat() { return lastChat; }

    public void setLastChat(long lastChat) {
        this.lastChat = lastChat;
    }

    public boolean isKitMieso() { return this.getKit_mieso() > System.currentTimeMillis(); }

    public boolean isKitTest() { return this.getKit_Test() > System.currentTimeMillis(); }

    public boolean isKitStart() { return kit_start > System.currentTimeMillis(); }

    public boolean isKitVip() { return kit_vip > System.currentTimeMillis(); }

    public boolean isKitSvip() { return kit_svip > System.currentTimeMillis();}

    public boolean isIgnoreTell(Player p) { return this.ignoreTell.contains(p); }

    public void addIgnoreTell(Player p) { this.ignoreTell.add(p); }

    public void removeIgnoreTell(Player p) { this.ignoreTell.remove(p); }

    public boolean isIgnoreTpa(Player p) { return this.ignoreTpa.contains(p); }

    public void addIgnoreTpa(Player p) { this.ignoreTpa.add(p); }

    public void removeIgnoreTpa(Player p) { this.ignoreTpa.remove(p); }

    public void setExp(int exp) { this.exp = exp; }

    public long getTime() { return time; }

    public void setWykStone(int wykstone) { this.wykStone = wykstone; }

    public void setWykObsidian(int wykObsidian) { this.wykObsidian = wykObsidian; }

    public int getWykStone() { return wykStone; }

    public int getWykObsidian() { return wykObsidian; }

    public int getExp() { return exp; }

    public int getLvl() { return lvl; }

    public int getCoins() { return coins; }

    public void setCoins(int coins) {this.coins = coins;}

    public int getkoxy() { return koxy; }

    public void setKoxy(int koxy) { this.koxy = koxy; }

    public int getKoxEaten() { return koxEaten; }

    public int getRefile() { return refile; }

    public void setRefile(int refile) { this.refile = refile; }

    public int getRefilEaten() { return refilEaten; }

    public int getPerly() { return perly; }

    public void setPerly(int perly) { this.perly = perly; }

    public int getPearlThrown() { return pearlThrown; }

    public int getStrzaly() { return strzaly; }

    public void setStrzaly(int strzaly) { this.strzaly = strzaly; }

    public int getArrowsShoten() { return arrowsShoten; }

    public int getEasycase() { return easycase; }

    public int getCase611() { return case611; }

    public int getKills() { return kills; }

    public void setKills(int kills) {this.kills = kills;}

    public void setAsysty(int asysty) {this.asysty = asysty;}

    public int getAsysty() { return asysty; }

    public void removeKoxy(int index) { this.koxy -= index; }

    public void removeRefile(int index) { this.refile -= index; }

    public void removePerly(int index) { this.perly -= index; }

    public void removeStrzaly(int index) { this.strzaly -= index; }

    public void removeCoins(int paramInt) { this.coins -= paramInt; }

    public void addKoxy(int index) { this.koxy += index; }

    public void addRefile(int index) { this.refile += index; }

    public void addPerly(int index) { this.perly += index; }

    public void addStrzaly(int index) { this.strzaly += index; }

    public void addkoxEaten(int index) { this.koxEaten += index; }

    public void addrefilEaten(int index) { this.refilEaten += index; }

    public void addpearlThrown(int index) { this.pearlThrown += index; }

    public void addarrowsShoten(int index) { this.arrowsShoten += index; }

    public void addCoins(int index) { this.coins += index; }

    public void addEasycase(int index) { this.easycase += index; }

    public void addCase611(int index) { this.case611 += index; }

    public void setKit_vip(long kit_vip) { this.kit_vip = kit_vip; }

    public void setKit_svip(long kit_svip) { this.kit_svip = kit_svip; }

    public void setKit_start(long kit_start) { this.kit_start = kit_start; }

    public void setLvl(int lvl) { this.lvl = lvl; }

    public void setEasycase(int easycase) { this.easycase = easycase; }

    public void setCase611(int case611) { this.case611 = case611; }

    public String getGuild() {return guild;}

    public void setGuild(String guild) {this.guild = guild;}

    public boolean isIncognito() {
        return incognito;
    }

    public void setIncognito(boolean incognito) {
        this.incognito = incognito;
    }

    public ItemStack[] getEnderchest() {return this.enderchest;}

    public void setEnderchest(ItemStack[] items) {
        this.enderchest = items;
    }

    private void insert() {
        Map<String,Object> data = new ConcurrentHashMap<>();
        data.put("name", name);
        data.put("turboDrop", turboDrop);
        data.put("kit_start", kit_start);
        data.put("kit_vip", kit_vip);
        data.put("kit_svip", kit_svip);
        data.put("lvl", lvl);
        data.put("exp", exp);
        data.put("wykStone", wykStone);
        data.put("wykObsidian", wykObsidian);
        data.put("drops", JSONHelper.dropsToJsonString(drops));
        data.put("koxy", koxy);
        data.put("refile", refile);
        data.put("perly", perly);
        data.put("strzaly", strzaly);
        data.put("koxEaten", koxEaten);
        data.put("refilEaten", refilEaten);
        data.put("pearlThrown", pearlThrown);
        data.put("arrowsShoten", arrowsShoten);
        data.put("coins", coins);
        data.put("easycase", easycase);
        data.put("case611", case611);
        data.put("kills", kills);
        data.put("asysty", asysty);
        data.put("points", points);
        data.put("deaths", deaths);
        data.put("ks", ks);
        data.put("maxks", maxks);
        data.put("time", time);
        data.put("os", achievments);
        data.put("guild", guild);
        data.put("enderchest", ItemSerializer.itemsToString(this.enderchest));
        MySQLUtil.insert("users", data);
    }

    public void save() {
        Map<String,Object> data = new ConcurrentHashMap<>();
        data.put("turboDrop", turboDrop);
        data.put("kit_start", kit_start);
        data.put("kit_vip", kit_vip);
        data.put("kit_svip", kit_svip);
        data.put("lvl", lvl);
        data.put("exp", exp);
        data.put("wykStone", wykStone);
        data.put("wykObsidian", wykObsidian);
        data.put("drops", JSONHelper.dropsToJsonString(drops));
        data.put("koxy", koxy);
        data.put("refile", refile);
        data.put("perly", perly);
        data.put("strzaly", strzaly);
        data.put("koxEaten", koxEaten);
        data.put("refilEaten", refilEaten);
        data.put("pearlThrown", pearlThrown);
        data.put("arrowsShoten", arrowsShoten);
        data.put("coins", coins);
        data.put("easycase", easycase);
        data.put("case611", case611);
        data.put("kills", kills);
        data.put("asysty", asysty);
        data.put("points", points);
        data.put("deaths", deaths);
        data.put("ks", ks);
        data.put("maxks", maxks);
        data.put("time", time);
        data.put("os", achievments);
        data.put("guild", guild);
        data.put("enderchest", ItemSerializer.itemsToString(this.enderchest));
        MySQLUtil.save("users", "name", name, data);
    }

    public void saveSync() {
        Map<String,Object> data = new ConcurrentHashMap<>();
        data.put("turboDrop", turboDrop);
        data.put("kit_start", kit_start);
        data.put("kit_vip", kit_vip);
        data.put("kit_svip", kit_svip);
        data.put("lvl", lvl);
        data.put("exp", exp);
        data.put("wykStone", wykStone);
        data.put("wykObsidian", wykObsidian);
        data.put("drops", JSONHelper.dropsToJsonString(drops));
        data.put("koxy", koxy);
        data.put("refile", refile);
        data.put("perly", perly);
        data.put("strzaly", strzaly);
        data.put("koxEaten", koxEaten);
        data.put("refilEaten", refilEaten);
        data.put("pearlThrown", pearlThrown);
        data.put("arrowsShoten", arrowsShoten);
        data.put("coins", coins);
        data.put("easycase", easycase);
        data.put("case611", case611);
        data.put("kills", kills);
        data.put("asysty", asysty);
        data.put("points", points);
        data.put("deaths", deaths);
        data.put("ks", ks);
        data.put("maxks", maxks);
        data.put("time", time);
        data.put("os", achievments);
        data.put("guild", guild);
        data.put("enderchest", ItemSerializer.itemsToString(this.enderchest));
        MySQLUtil.saveSync("users", "name", name, data);
    }
    public int getAchLvl(AchievmentTypeName type) {
        String[] split = achievments.split("@");
        Bukkit.broadcastMessage(split.length+"");
        Bukkit.broadcastMessage(Arrays.toString(split));
        switch (type) {
            case STONE:{
                return Integer.parseInt(split[0]);
            }
            case OBSIDIAN:{
                return Integer.parseInt(split[1]);
            }
            case KILLS:{
                return Integer.parseInt(split[2]);
            }
            case ASYSTY:{
                return Integer.parseInt(split[3]);
            }
            case KOX:{
                return Integer.parseInt(split[4]);
            }
            case REF:{
                return Integer.parseInt(split[5]);
            }
            case TIME:{
                return Integer.parseInt(split[6]);
            }
            default: return 0;
        }
    }

    public void setAchLvl(AchievmentTypeName type, int value) {
        switch (type) {
            case STONE:{
                achievments = value + "@" + getAchLvl(AchievmentTypeName.OBSIDIAN) + "@" + getAchLvl(AchievmentTypeName.KILLS) + "@" + getAchLvl(AchievmentTypeName.ASYSTY) + "@" + getAchLvl(AchievmentTypeName.KOX) + "@" + getAchLvl(AchievmentTypeName.REF) + "@" + getAchLvl(AchievmentTypeName.TIME);
                break;
            }
            case OBSIDIAN:{
                achievments = getAchLvl(AchievmentTypeName.STONE) + "@" + value + "@" + getAchLvl(AchievmentTypeName.KILLS) + "@" + getAchLvl(AchievmentTypeName.ASYSTY) + "@" + getAchLvl(AchievmentTypeName.KOX) + "@" + getAchLvl(AchievmentTypeName.REF) + "@" + getAchLvl(AchievmentTypeName.TIME);
                break;
            }
            case KILLS:{
                achievments = getAchLvl(AchievmentTypeName.STONE) + "@" + getAchLvl(AchievmentTypeName.OBSIDIAN) + "@" + value + "@" + getAchLvl(AchievmentTypeName.ASYSTY) + "@" + getAchLvl(AchievmentTypeName.KOX) + "@" + getAchLvl(AchievmentTypeName.REF) + "@" + getAchLvl(AchievmentTypeName.TIME);
                break;
            }
            case ASYSTY:{
                achievments = getAchLvl(AchievmentTypeName.STONE) + "@" + getAchLvl(AchievmentTypeName.OBSIDIAN) + "@" + getAchLvl(AchievmentTypeName.KILLS) + "@" + value + "@" + getAchLvl(AchievmentTypeName.KOX) + "@" + getAchLvl(AchievmentTypeName.REF) + "@" + getAchLvl(AchievmentTypeName.TIME);
                break;
            }
            case KOX:{
                achievments = getAchLvl(AchievmentTypeName.STONE) + "@" + getAchLvl(AchievmentTypeName.OBSIDIAN) + "@" + getAchLvl(AchievmentTypeName.KILLS) + "@" + getAchLvl(AchievmentTypeName.ASYSTY) + "@" + value + "@" + getAchLvl(AchievmentTypeName.REF) + "@" + getAchLvl(AchievmentTypeName.TIME);
                break;
            }
            case REF:{
                achievments = getAchLvl(AchievmentTypeName.STONE) + "@" + getAchLvl(AchievmentTypeName.OBSIDIAN) + "@" + getAchLvl(AchievmentTypeName.KILLS) + "@" + getAchLvl(AchievmentTypeName.ASYSTY) + "@" + getAchLvl(AchievmentTypeName.KOX) + "@" + value + "@" + getAchLvl(AchievmentTypeName.TIME);
                break;
            }
            case TIME:{
                achievments = getAchLvl(AchievmentTypeName.STONE) + "@" + getAchLvl(AchievmentTypeName.OBSIDIAN) + "@" + getAchLvl(AchievmentTypeName.KILLS) + "@" + getAchLvl(AchievmentTypeName.ASYSTY) + "@" + getAchLvl(AchievmentTypeName.KOX) + "@" + getAchLvl(AchievmentTypeName.REF) + "@" + value;
                break;
            }
        }
    }

    public void putForSave() {
        SaveHolder.USERS.putIfAbsent(this.name, this);
    }

    public BukkitTask getCurrentTeleport() {
        return currentTeleport;
    }

    public void setCurrentTeleport(BukkitTask currentTeleport) {
        this.currentTeleport = currentTeleport;
    }

    public boolean isInBeingChecked() {
        return inBeingChecked;
    }

    public void setInBeingChecked(boolean inBeingChecked) {
        this.inBeingChecked = inBeingChecked;
    }

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.name);
    }

    public boolean isOnCuboid() {
        return isOnCuboid;
    }

    public void setOnCuboid(boolean onCuboid) {
        isOnCuboid = onCuboid;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {this.deaths = deaths;}

    public int getKs() {return ks;}

    public void setKs(int ks) {this.ks = ks;}

    public int getMaxks() {
        return maxks;
    }

    public void setMaxks(int maxks) {this.maxks = maxks;}

    public void setPoints(int points) {this.points = points;}

    public int getPoints() {return this.points;}
}
