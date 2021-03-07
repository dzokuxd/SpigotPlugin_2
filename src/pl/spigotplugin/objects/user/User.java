package pl.spigotplugin.objects.user;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.spigotplugin.SpigotPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private long turboDrop;
    private boolean autoMessages;
    private boolean privateMessages;
    private final List<Player> ignoreTell;
    private final List<Player> ignoreTpa;
    private final List<Player> tpa;
    private String home;

    private BukkitTask currentTeleport;

    public User(Player p) {
        this.name = p.getName();
        this.turboDrop = 0L;
        this.autoMessages = true;
        this.privateMessages = true;
        this.ignoreTell = new ArrayList<>();
        this.ignoreTpa = new ArrayList<>();
        this.tpa = new ArrayList<>();
        this.insert();
    }

    public User(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.turboDrop = rs.getLong("turboDrop");
        this.autoMessages = true;
        this.privateMessages = true;
        this.ignoreTell = new ArrayList<>();
        this.ignoreTpa = new ArrayList<>();
        this.tpa = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getName());
    }

    public long getTurboDrop() { return turboDrop; }

    public void setTurboDrop(long turboDrop) { this.turboDrop = turboDrop; }

    public boolean isAutoMessages() { return this.autoMessages; }

    public boolean isPrivateMessages() { return this.privateMessages; }

    public void setAutoMessages(boolean autoMessages) { this.autoMessages = autoMessages; }

    public void setPrivateMessages(boolean privateMessages) { this.privateMessages = privateMessages; }

    public boolean isIgnoreTell(Player p) { return this.ignoreTell.contains(p); }

    public void addIgnoreTell(Player p) { this.ignoreTell.add(p); }

    public void removeIgnoreTell(Player p) { this.ignoreTell.remove(p); }

    public boolean isIgnoreTpa(Player p) { return this.ignoreTpa.contains(p); }

    public void addIgnoreTpa(Player p) { this.ignoreTpa.add(p); }

    public void removeIgnoreTpa(Player p) { this.ignoreTpa.remove(p); }

    public List<Player> getTpa() { return tpa; }

    private void insert() {
        try {
            SpigotPlugin.getMySQL().update("INSERT INTO `{P}users`(`name`, `turboDrop`) VALUES ('" + this.getName() + "', '" + this.getTurboDrop() +"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            SpigotPlugin.getMySQL().update("UPDATE `{P}users` SET  `turboDrop` = '" + this.getTurboDrop() + "' WHERE `name` = '"+name+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BukkitTask getCurrentTeleport() {
        return currentTeleport;
    }

    public void setCurrentTeleport(BukkitTask currentTeleport) {
        this.currentTeleport = currentTeleport;
    }
}
