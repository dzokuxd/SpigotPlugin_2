package pl.spigotplugin.mysql;

import org.bukkit.Bukkit;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.configs.Config;

import java.sql.*;

public class MySQL {
    private final String host;
    private final String dataBase;
    private final String user;
    private final String password;
    private Connection connection;

    public MySQL() throws SQLException {
        this.host = Config.host;
        this.dataBase = Config.dataBase;
        this.user = Config.user;
        this.password = Config.password;
        connect();

        update("CREATE TABLE IF NOT EXISTS users (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name TEXT, turboDrop BIGINT, kit_start BIGINT, kit_vip BIGINT, kit_svip BIGINT, lvl BIGINT, exp BIGINT, wykStone BIGINT, wykObsidian BIGINT,drops TEXT NOT NULL, koxy int(11) NOT NULL, refile int(11) NOT NULL, perly int(11) NOT NULL, strzaly int(11) NOT NULL, koxEaten int(11) NOT NULL, refilEaten int(11) NOT NULL, pearlThrown int(11) NOT NULL, arrowsShoten int(11) NOT NULL, coins int(11) NOT NULL, easycase int(11) NOT NULL, case611 int(11) NOT NULL, kills int(11) NOT NULL, asysty int(11) NOT NULL, time bigint(22) NOT NULL, `os` text NOT NULL)");
        update("CREATE TABLE IF NOT EXISTS guilds (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, tag TEXT, name TEXT, leader TEXT, region TEXT, members TEXT, deputy TEXT, home TEXT)");

        update("CREATE TABLE IF NOT EXISTS backups (id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(32) NOT NULL," +
                "time bigint(22) NOT NULL, " +
                "killer varchar(32) NOT NULL, " +
                "ping int(11) NOT NULL, " +
                "inventory text NOT NULL, " +
                "armor text NOT NULL, " +
                "enderchest text NOT NULL);");
        update("CREATE TABLE IF NOT EXISTS bans (id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(32) NOT NULL," +
                "time bigint(22) NOT NULL, " +
                "reason text NOT NULL, " +
                "admin varchar(32) NOT NULL, " +
                "start BIGINT(22) NOT NULL);");
        update("CREATE TABLE IF NOT EXISTS mutes (id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(32) NOT NULL," +
                "time bigint(22) NOT NULL, " +
                "reason text NOT NULL, " +
                "admin varchar(32) NOT NULL, " +
                "start BIGINT(22) NOT NULL);");

        MySQLUtil.mysql = this;
    }

    private void connect() throws SQLException {
        String url = "jdbc:mysql://" + host + ":3306/" + dataBase + "?autoReconnect=true&useSSL=false";
        this.connection = DriverManager.getConnection(url, user, password);
    }

    private boolean isDisconnected() throws SQLException {
        return connection == null || connection.isClosed();
    }

    public void update(String update) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotPlugin.getPlugin(), () -> {
            Statement statement;
            try {
                statement = this.connection.createStatement();
                statement.executeUpdate(update);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                try {
                    connect();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateSync(String update) {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.executeUpdate(update);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet query(String query) throws SQLException {
        if (isDisconnected()) connect();

        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet select(String table) throws SQLException {
        return query("SELECT * FROM `"+table+"`");
    }
}
