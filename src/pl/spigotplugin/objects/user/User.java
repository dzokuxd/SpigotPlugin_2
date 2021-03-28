package pl.spigotplugin.objects.user;

import org.bukkit.Bukkit;
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

    //KIT SYSTEM
    private long kit_mieso = 0;
    private long kit_start = 0;
    private long kit_vip = 0;
    private long kit_svip = 0;

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

    public boolean isIgnoreTell(Player p) { return this.ignoreTell.contains(p); }

    public boolean isIgnoreTpa(Player p) { return this.ignoreTpa.contains(p); }

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

    private void insert() {
        SpigotPlugin.getMySQL().update("INSERT INTO users (name, turboDrop, kit_start, kit_vip, kit_svip) VALUES ('" + name + "', "+turboDrop+", '" + kit_start + "','" + kit_vip + "','" + kit_svip + "')");
    }

    public void save() {
        SpigotPlugin.getMySQL().update("UPDATE users SET turboDrop = '" + turboDrop + "', kit_start =' " + kit_start + "',kit_svip = '" + kit_svip + "' WHERE name = '"+name+"'");
    }

    public BukkitTask getCurrentTeleport() {
        return currentTeleport;
    }

    public void setCurrentTeleport(BukkitTask currentTeleport) {
        this.currentTeleport = currentTeleport;
    }
}
