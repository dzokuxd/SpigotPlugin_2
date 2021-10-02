package pl.spigotplugin.objects.user;

import pl.spigotplugin.mysql.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Ban {
    private String name;
    private String admin;
    private String reason;
    private long time;
    private final long start;

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
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("name", name);
        data.put("admin", admin);
        data.put("reason", reason);
        data.put("time", time);
        data.put("start", start);
        MySQLUtil.insert("bans", data);
    }
    public String getAdmin() {
        return admin;
    }

    public String getName() {
        return name;
    }

    public long getStart() {
        return start;
    }

    public void setName(String name) {this.name = name;}

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

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}

