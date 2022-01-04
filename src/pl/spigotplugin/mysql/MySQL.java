package pl.spigotplugin.mysql;

import org.bukkit.Bukkit;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.configs.DropFile;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.managers.*;

import java.sql.*;

public class MySQL {
    private final String host;
    private final String dataBase;
    private final String user;
    private final String password;
    private Connection connection;

    public MySQL() throws SQLException {
        this.host = statues.host;
        this.dataBase = statues.dataBase;
        this.user = statues.user;
        this.password = statues.password;
        connect();

        update("CREATE TABLE IF NOT EXISTS users (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name TEXT, rankType TEXT, turboDrop BIGINT, kit_start BIGINT, kit_vip BIGINT, kit_svip BIGINT, lvl BIGINT, exp BIGINT, wykStone BIGINT, wykObsidian BIGINT,drops TEXT, koxy INT, refile INT, perly INT, strzaly INT, koxEaten INT, refilEaten INT, pearlThrown INT, arrowsShoten INT, easycase INT, case611 INT, kills INT, asysty INT, points INT,deaths INT,ks INT,maxks INT, time BIGINT, os TEXT, guild TEXT, enderchest TEXT)");
        update("CREATE TABLE IF NOT EXISTS guilds (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, tag TEXT, name TEXT, leader TEXT, deputy TEXT, members TEXT, region TEXT, home TEXT, prolong BIGINT, playerslimit INT, ally TEXT, kills int NOT NULL,deaths int NOT NULL, life int NOT NULL, points int, hp int, createTime BIGINT, gold int, regen TEXT, hpLastAttack BIGINT, wars TEXT)");
        update("CREATE TABLE IF NOT EXISTS `{P}savedGuilds` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, `tag` text NOT NULL);");


        update("CREATE TABLE IF NOT EXISTS backups (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(32) NOT NULL," +
                "time bigint(22) NOT NULL, " +
                "killer varchar(32) NOT NULL, " +
                "ping int NOT NULL, " +
                "inventory text NOT NULL, " +
                "armor text NOT NULL, " +
                "enderchest text NOT NULL);");
        update("CREATE TABLE IF NOT EXISTS bans (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(32) NOT NULL," +
                "time bigint(22) NOT NULL, " +
                "reason text NOT NULL, " +
                "admin varchar(32) NOT NULL, " +
                "start BIGINT(22) NOT NULL);");
        update("CREATE TABLE IF NOT EXISTS mutes (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
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
