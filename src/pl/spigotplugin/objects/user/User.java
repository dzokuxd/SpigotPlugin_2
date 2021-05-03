package pl.spigotplugin.objects.user;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.spigotplugin.helper.JSONHelper;
import pl.spigotplugin.mysql.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User {
    public ConcurrentHashMap<Material, Integer> getDrops() {
        return drops;
    }

    private String name;
    private long turboDrop = 0;
    private boolean autoMessages = true;
    private boolean privateMessages = true;
    private final List<Player> ignoreTell = new ArrayList<>();
    private final List<Player> ignoreTpa = new ArrayList<>();
    private final List<Player> tpa = new ArrayList<>();
    private BukkitTask currentTeleport;
    private long kit_mieso = 0;
    private long kit_start = 0;
    private long kit_vip = 0;
    private long kit_svip = 0;
    private int lvl = 1;
    private int exp = 0;
    private int wykStone = 0;
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

    private ConcurrentHashMap<Material, Integer> drops = new ConcurrentHashMap<>();

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
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(name);
    }

    public long getTurboDrop() { return turboDrop; }

    public void setTurboDrop(long turboDrop) { this.turboDrop = turboDrop; }

    public boolean isAutoMessages() { return this.autoMessages; }

    public boolean isPrivateMessages() { return this.privateMessages; }

    public void setAutoMessages(boolean autoMessages) { this.autoMessages = autoMessages; }

    public List<Player> getTpa() { return tpa; }

    public long getKit_mieso() { return kit_mieso; }

    public void setKit_mieso(long kit_mieso) { this.kit_mieso = kit_mieso; }

    public long getKit_start() { return kit_start; }

    public long getKit_vip() { return kit_vip; }

    public long getKit_svip() { return kit_svip;}

    public boolean isKitMieso() { return this.getKit_mieso() > System.currentTimeMillis(); }

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

    public void setWykStone(int wykstone) { this.wykStone = wykstone; }

    public int getWykStone() { return wykStone; }

    public int getExp() { return exp; }

    public int getLvl() { return lvl; }

    public int getkoxy() { return koxy; }

    public int getRefile() { return refile; }

    public int getPerly() { return perly; }

    public int getStrzaly() { return strzaly; }

    public int getEasycase() { return easycase; }

    public int getCase611() { return case611; }

    public void removeKoxy(int index) { this.koxy -= index; }

    public void removeRefile(int index) { this.refile -= index; }

    public void removePerly(int index) { this.perly -= index; }

    public void removeStrzaly(int index) { this.strzaly -= index; }

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
        MySQLUtil.saveSync("users", "name", name, data);
    }

    public BukkitTask getCurrentTeleport() {
        return currentTeleport;
    }

    public void setCurrentTeleport(BukkitTask currentTeleport) {
        this.currentTeleport = currentTeleport;
    }
}
