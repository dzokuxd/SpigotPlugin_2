package pl.spigotplugin.configs;

import org.bukkit.configuration.file.FileConfiguration;
import pl.spigotplugin.SpigotPlugin;

import java.io.File;
import java.lang.reflect.Field;

public class Config
{
    public static String host = "mysql.titanaxe.com";
    public static String dataBase = "srv155087";
    public static String user = "srv155087";
    public static String password = "DkUxXtrk";
    public static int LIMIT_STRZAL = 24;
    public static int LIMIT_PEARL = 4;
    public static int LIMIT_REFILE = 12;
    public static int LIMIT_KOX = 2;
    public static int BORDER_WORLD = 800;
    public static int BORDER_GTP = 800;
    public static int CUBOID_TNT_OD = 14;
    public static int CUBOID_TNT_DO = 22;
    public static int REGION_SIZE_SPAWN = 75;
    public static int REGION_SIZE_OUTSITE = 150;
    public static int REGION_BYPASSY = 50;
    public static long EVENTS_BEACON = 0L;
    public static long EVENTS_KILL = 0L;
    public static long EVENTS_CASE = 0L;
    public static long EVENTS_TURBO = 0L;
    public static int LVL = 1;
    public static boolean MANAGE_TPA = false;
    public static boolean MANAGE_SPAWN = false;
    public static boolean MANAGE_KIT = false;
    public static boolean MANAGE_DIAX = false;
    public static boolean MANAGE_SHOP = false;
    public static String IP = "easyage.pl";

    public static void loadConfig() {
        try {
            final FileConfiguration c = SpigotPlugin.getPlugin().getConfig();
            for (final Field f : Config.class.getFields()) {
                if (c.isSet("config." + f.getName().toLowerCase().replace("_", "."))) {
                    f.set(null, c.get("config." + f.getName().toLowerCase().replace("_", ".")));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            final FileConfiguration c = SpigotPlugin.getPlugin().getConfig();
            for (final Field f : Config.class.getFields()) {
                c.set("config." + f.getName().toLowerCase().replace("_", "."), f.get(null));
            }
            SpigotPlugin.getPlugin().saveConfig();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig() {
        SpigotPlugin.getPlugin().reloadConfig();
        loadConfig();
        saveConfig();
    }
}

