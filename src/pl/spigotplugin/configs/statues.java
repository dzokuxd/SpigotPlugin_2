package pl.spigotplugin.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.utils.IOUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;

public class statues {

    private static final File file = new File(SpigotPlugin.getPlugin().getDataFolder(), "statues.yml");
    private static FileConfiguration c = null;

    public static String host = "mysql.titanaxe.com";
    public static String dataBase = "srv171912";
    public static String user = "srv171912";
    public static String password = "JxjuWRbh";
    public static int LIMIT_STRZAL = 24;
    public static int LIMIT_PEARL = 4;
    public static int LIMIT_REFILE = 12;
    public static int LIMIT_KOX = 2;
    public static int BORDER_WORLD = 800;
    public static int BORDER_GTP = 800;
    public static int REGION_SIZE_SPAWN = 75;
    public static int REGION_SIZE_OUTSITE = 150;
    public static int REGION_SIZE_400cuboid = 150;
    public static int REGION_SIZE_minus400cuboid = 150;
    public static int REGION_BYPASSY = 50;
    public static long EVENTS_BEACON = 0L;
    public static long EVENTS_KILL = 0L;
    public static long EVENTS_CASE = 0L;
    public static long EVENTS_TURBO = 0L;
    public static int LVL = 1;
    public static int CHAT_SLOWMODE = 10;
    public static boolean MANAGE_TPA = false;
    public static boolean MANAGE_ENCHANT = false;
    public static boolean MANAGE_SPAWN = false;
    public static boolean MANAGE_KIT = false;
    public static boolean MANAGE_DIAMOND = false;
    public static boolean MANAGE_SHOP = false;
    public static boolean MANAGE_PANEL = false;
    public static boolean MANAGE_BEACON = false;
    public static boolean MANAGE_DROPHEAD = false;
    public static boolean MANAGE_GUILDCREATE = false;
    public static String IP = "easyage.pl";

    public static void loadLang() {
        try {
            if (!statues.file.exists()) {
                statues.file.getParentFile().mkdirs();
                InputStream is = SpigotPlugin.getPlugin().getResource(file.getName());
                if (is != null) {
                    IOUtil.copy(is, file);
                }
            }
            c = YamlConfiguration.loadConfiguration(file);
            for (Field f : statues.class.getFields()) {
                if (c.isSet(f.getName().toLowerCase().replaceFirst("_", ",").replace(",", "."))) {
                    f.set(null, c.get(f.getName().toLowerCase().replaceFirst("_", ",").replace(",", ".")));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveLang() {
        try {
            for (Field f : statues.class.getFields()) {
                c.set(f.getName().toLowerCase().replaceFirst("_", ",").replace(",", "."), f.get(null));
            }
            c.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadLang() {
        loadLang();
        saveLang();
    }
}

