package pl.spigotplugin.mysql;

import java.util.Map;

public class MySQLUtil {
    public static MySQL mysql;

    public static void insert(String where, Map<String, Object> data) {
        StringBuilder db =
                new StringBuilder(),
                dbX = new StringBuilder(),
                dbY = new StringBuilder();

        dbX.append("(");
        for (String s : data.keySet()) dbX.append(", `").append(s).append("`");
        dbX.append(")");

        dbY.append("(");
        for (Object s : data.values()) dbY.append(", '").append(s).append("'");
        dbY.append(")");

        db.append("INSERT INTO `").append(where).append("`").append(dbX.toString().replaceFirst(", ", "")).append(" VALUES ").append(dbY.toString().replaceFirst(", ", ""));

        mysql.update(db.toString());
    }

    public static void save(String what, String where, String whereWhat, Map<String, Object> data) {
        StringBuilder db = new StringBuilder();

        db.append("UPDATE `").append(what).append("` SET ");
        data.forEach((k, v) -> db.append(",`").append(k).append("` = ").append((v == null || v.toString().equalsIgnoreCase("null")) ? "NULL " : "'" + v + "' "));
        db.append("WHERE `").append(where).append("` = '").append(whereWhat).append("'");

        mysql.update(db.toString().replaceFirst(",", ""));
    }

    public static void saveSync(String what, String where, String whereWhat, Map<String, Object> data) {
        StringBuilder db = new StringBuilder();

        db.append("UPDATE `").append(what).append("` SET ");
        data.forEach((k, v) -> db.append(",`").append(k).append("` = ").append((v == null || v.toString().equalsIgnoreCase("null")) ? "NULL " : "'" + v + "' "));
        db.append("WHERE `").append(where).append("` = '").append(whereWhat).append("'");

        mysql.updateSync(db.toString().replaceFirst(",", ""));
    }
}
