package pl.spigotplugin.objects.user;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.spigotplugin.SpigotPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
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
    private String statystykikopania = "0@0@0@0@0@0@0";

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
        this.statystykikopania = rs.getString("statystykikopania");
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

    public void removeLvl(int index) {
        this.lvl -= index;
    }

    public int getLvl() {
        return lvl;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) { this.exp = exp; }

    public int getWykStone() {
        return wykStone;
    }

    public void setWykStone(int wykstone) {
        this.wykStone = wykstone;
    }

    public void setKit_vip(long kit_vip) {
        this.kit_vip = kit_vip;
        SpigotPlugin.getMySQL().update("UPDATE users SET kit_vip='" + kit_vip + "' WHERE name='" + name + "'");
    }

    public void setKit_svip(long kit_svip) {
        this.kit_svip = kit_svip;
        SpigotPlugin.getMySQL().update("UPDATE users SET kit_svip='" + kit_svip + "' WHERE name='" + name + "'");
    }

    public void setKit_start(long kit_start) {
        this.kit_start = kit_start;
        SpigotPlugin.getMySQL().update("UPDATE users SET kit_start='" + kit_start + "' WHERE name='" + name + "'");
    }
    public void setLvl(int lvl) {
        this.lvl = lvl;
            SpigotPlugin.getMySQL().update("UPDATE users SET lv` ='" + lvl + "', exp ='" + exp + "' WHERE name ='" + name + "'");
    }

    private void insert() {
        SpigotPlugin.getMySQL().update("INSERT INTO users (name, turboDrop, kit_start, kit_vip, kit_svip, lvl, exp) VALUES ('" + name + "', "+turboDrop+", '" + kit_start + "','" + kit_vip + "','" + kit_svip + "','" +lvl+"','" +exp+"')");
    }

    public void save() {
        SpigotPlugin.getMySQL().update("UPDATE users SET turboDrop = '" + turboDrop + "', kit_start =' " + kit_start + "',kit_svip = '" + kit_svip + "', lvl = '" + lvl + "', lvl = '" + exp + "' WHERE name = '"+name+"'");
    }

    public BukkitTask getCurrentTeleport() {
        return currentTeleport;
    }

    public void setCurrentTeleport(BukkitTask currentTeleport) {
        this.currentTeleport = currentTeleport;
    }
    public int getDiamond() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[0]);
    }

    public void setDiamond(int i) {
        this.statystykikopania = i + "@" + getEmerald() + "@" + getGold() + "@" + getIron() + "@" + getApple() + "@" + getObs() + "@" + getCoal() + "@" + getLeather() + "@" + getSand() + "@" + getPearl() + "@" + getGunPowder() + "@";
    }

    public int getEmerald() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[1]);
    }

    public void setEmerald(int i) {
        this.statystykikopania = getDiamond() + "@" + i + "@" + getGold() + "@" + getIron() + "@" + getApple() + "@" + getObs() + "@" + getCoal() + "@" + getLeather() + "@" + getSand() + "@" + getPearl() + "@" + getGunPowder() + "@";
    }

    public int getGold() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[2]);
    }

    public void setGold(int i) {
        this.statystykikopania = getDiamond() + "@" + getEmerald() + "@" + i + "@" + getIron() + "@" + getApple() + "@" + getObs() + "@" + getCoal() + "@" + getLeather() + "@" + getSand() + "@" + getPearl() + "@" + getGunPowder() + "@";
    }

    public int getIron() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[3]);
    }

    public void setIron(int i) {
        this.statystykikopania = getDiamond() + "@" + getEmerald() + "@" + getGold() + "@" + i + "@" + getApple() + "@" + getObs() + "@" + getCoal() + "@" + getLeather() + "@" + getSand() + "@" + getPearl() + "@" + getGunPowder() + "@";
    }

    public int getApple() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[4]);
    }

    public void setApple(int i) {
        this.statystykikopania = getDiamond() + "@" + getEmerald() + "@" + getGold() + "@" + getIron() + "@" + i + "@" + getObs() + "@" + getCoal() + "@" + getLeather() + "@" + getSand() + "@" + getPearl() + "@" + getGunPowder() + "@";
    }

    public int getObs() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[5]);
    }

    public void setObs(int i) {
        this.statystykikopania = getDiamond() + "@" + getEmerald() + "@" + getGold() + "@" + getIron() + "@" + getApple() + "@" + i + "@" + getCoal() + "@" + getLeather() + "@" + getSand() + "@" + getPearl() + "@" + getGunPowder() + "@";
    }

    public int getCoal() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[6]);
    }

    public void setCoal(int i) {
        this.statystykikopania = getDiamond() + "@" + getEmerald() + "@" + getGold() + "@" + getIron() + "@" + getApple() + "@" + getObs() + "@" + i + "@" + getLeather() + "@" + getSand() + "@" + getPearl() + "@" + getGunPowder() + "@";
    }

    public int getLeather() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[7]);
    }

    public void setLeather(int i) {
        this.statystykikopania = getDiamond() + "@" + getEmerald() + "@" + getGold() + "@" + getIron() + "@" + getApple() + "@" + getObs() + "@" + getCoal() + "@" + i + "@" + getSand() + "@" + getPearl() + "@" + getGunPowder() + "@";
    }

    public int getSand() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[8]);
    }

    public void setSand(int i) {
        this.statystykikopania = getDiamond() + "@" + getEmerald() + "@" + getGold() + "@" + getIron() + "@" + getApple() + "@" + getObs() + "@" + getCoal() + "@" + getLeather() + "@" + i + "@" + getPearl() + "@" + getGunPowder() + "@";
    }

    public int getPearl() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[9]);
    }

    public void setPearl(int i) {
        this.statystykikopania = getDiamond() + "@" + getEmerald() + "@" + getGold() + "@" + getIron() + "@" + getApple() + "@" + getObs() + "@" + getCoal() + "@" + getLeather() + "@" + getSand() + "@" + i + "@" + getGunPowder() + "@";
    }

    public int getGunPowder() {
        String[] splitter = this.statystykikopania.split("@");
        return Integer.parseInt(splitter[10]);
    }

    public void setGunPowder(int i) {
        this.statystykikopania = getDiamond() + "@" + getEmerald() + "@" + getGold() + "@" + getIron() + "@" + getApple() + "@" + getObs() + "@" + getCoal() + "@" + getLeather() + "@" + getSand() + "@" + getPearl() + "@" + i + "@";
    }

    public void setAmountByMaterial(Material material) {
        switch (material) {
            case DIAMOND: {
                setDiamond(getDiamond() + 1);
                break;
            }
            case EMERALD: {
                setEmerald(getEmerald() + 1);
                break;
            }
            case GOLD_INGOT: {
                setGold(getGold() + 1);
                break;
            }
            case IRON_INGOT: {
                setIron(getIron() + 1);
                break;
            }
            case APPLE: {
                setApple(getApple() + 1);
                break;
            }
            case OBSIDIAN: {
                setObs(getObs() + 1);
                break;
            }
            case COAL: {
                setCoal(getCoal() + 1);
                break;
            }
            case LEATHER: {
                setLeather(getLeather() + 1);
                break;
            }
            case SAND: {
                setSand(getSand() + 1);
                break;
            }
            case ENDER_PEARL: {
                setPearl(getPearl() + 1);
                break;
            }
            case SULPHUR: {
                setGunPowder(getGunPowder() + 1);
                break;
            }
        }
    }
    public int getAmountByMaterial(Material material) {
        switch (material) {
            case DIAMOND: {
                return getDiamond();
            }
            case EMERALD: {
                return getEmerald();
            }
            case GOLD_INGOT: {
                return getGold();
            }
            case IRON_INGOT: {
                return getIron();
            }
            case APPLE: {
                return getApple();
            }
            case OBSIDIAN: {
                return getObs();
            }
            case COAL: {
                return getCoal();
            }
            case LEATHER: {
                return getLeather();
            }
            case SAND: {
                return getSand();
            }
            case ENDER_PEARL: {
                return getPearl();
            }
            case SULPHUR: {
                return getGunPowder();
            }
        }
        return 0;
    }
}
