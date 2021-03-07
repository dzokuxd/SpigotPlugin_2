package pl.spigotplugin.objects.user;

import pl.spigotplugin.SpigotPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ban {
    private String name;
    private String admin;
    private String reason;
    private long time;
    private long start;

    public Ban(String name, String admin, String reason, long time) {
        this.name = name;
        this.admin = admin;
        this.reason = reason;
        this.time = time;
        this.start = System.currentTimeMillis();
        insert();
    }

    public Ban(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.admin = rs.getString("admin");
        this.reason = rs.getString("reason");
        this.time = rs.getLong("time");
        this.start = rs.getLong("start");
    }

    private void insert() {
        try {
            SpigotPlugin.getMySQL().update("INSERT INTO `{P}bans`(`id`, `name`, `admin`, `reason`, `time`, `start`) VALUES (NULL, '" + this.getName() + "','" + this.getAdmin() + "','" + this.getReason() + "','" + this.getTime() + "','" + this.getStart() + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getStart() {
        return start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}

