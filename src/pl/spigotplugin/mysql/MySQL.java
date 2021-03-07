package pl.spigotplugin.mysql;

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

        update("CREATE TABLE IF NOT EXISTS `{P}users` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "`name` varchar(32) NOT NULL,`turboDrop` bigint(22) NOT NULL);");
        update("CREATE TABLE IF NOT EXISTS `{P}backups` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "`name` varchar(32) NOT NULL," +
                "`time` bigint(22) NOT NULL, " +
                "`killer` varchar(32) NOT NULL, " +
                "`ping` int(11) NOT NULL, " +
                "`inventory` text NOT NULL, " +
                "`armor` text NOT NULL, " +
                "`enderchest` text NOT NULL);");
        update("CREATE TABLE IF NOT EXISTS `{P}bans` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "`name` varchar(32) NOT NULL," +
                "`time` bigint(22) NOT NULL, " +
                "`reason` text NOT NULL, " +
                "`admin` varchar(32) NOT NULL, " +
                "`start` BIGINT(22) NOT NULL);");
        update("CREATE TABLE IF NOT EXISTS `{P}mutes` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "`name` varchar(32) NOT NULL," +
                "`time` bigint(22) NOT NULL, " +
                "`reason` text NOT NULL, " +
                "`admin` varchar(32) NOT NULL, " +
                "`start` BIGINT(22) NOT NULL);");
    }

    private void connect() throws SQLException {
        String url = "jdbc:mysql://" + host + ":3306/" + dataBase + "?autoReconnect=true";
        this.connection = DriverManager.getConnection(url, user, password);
    }

    private boolean isDisconnected() throws SQLException {
        return connection == null || connection.isClosed();
    }

    public void update(String update) throws SQLException {
        if (isDisconnected()) connect();

        Statement statement = this.connection.createStatement();
        statement.executeUpdate(update);
    }

    public ResultSet query(String query) throws SQLException {
        if (isDisconnected()) connect();

        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }
}
