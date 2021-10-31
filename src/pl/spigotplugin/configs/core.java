package pl.spigotplugin.configs;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.utils.IOUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class core
{
    private static final File file = new File(SpigotPlugin.getPlugin().getDataFolder(), "core.yml");
    private static FileConfiguration c = null;
    public static String MESSAGES_HELP = "&7&m----------&7[ &c&lPOMOC &7]&M----------" +
            "\n&c/kit &7- &6Lista dostepnych kitow" +
            "\n&c/incognito &7- &6Ukrycie swojego nicku oraz gildii" +
            "\n&c/drop &7- &6Zarzadzanie dropem" +
            "\n&c/spawn &7- &6Teleportacja na spawn" +
            "\n&c/vip &7- &6Informacje dotyczace rangi VIP" +
            "\n&c/svip &7- &6Informacje dotyczace rangi SVIP" +
            "\n&c/gracz <nick> &7- &6Statystyki danego gracza" +
            "\n&c/pay <nick> &7- &6Przelewa coinsy dla gracza" +
            "\n&c/wyjebane <nick> &7- &6Ignorowanie msg/tpa od graczy" +
            "\n&c/wiadomosci &7- &6Konfiguracja wiadomosci" +
            "\n&c/topki &7- &6Lista graczy znajdujacych sie w topce" +
            "\n&c/schowek &7- &6Depozyt koxow/refow/perel/strzal" +
            "\n&c/youtuber &7- &6Informacje dotyczace rangi YouTube" +
            "\n&c/gildie &7- &6Komendy gildii" +
            "\n&c/sklep &7- &6Sklep wymiana, sprzedasz i boosty!" +
            "\n&c/lvl &7- &6Statystyki Kopania" +
            "\n&c/helpop &7- &6Wiadomosc do administracji" +
            "\n&c/crafting &7- &6Lista receptur serwerowych" +
            "\n&c/smietnik &7- &6Smietnik" +
            "\n&7&m----------&7[ &c&lPOMOC &7]&M----------";
    public static String MESSAGES_YT = "&7YT" +
            "\n&7 yt";
    public static String MESSAGES_VIP = "&7VIP" +
            "\n&7 vip";
    public static String MESSAGES_SVIP = "&7SVIP" +
            "\n&7 svip";
    public static String CHAT_FORMAT_GLOBAL = "{GUILD}{PREFIX}{PLAYER}&8: &r{SUFFIX}{MESSAGE}";
    public static String CHAT_FORMAT_ADMIN = "{PREFIX}{PLAYER} &8\u2192 &r{SUFFIX}{MESSAGE}";
    public static String CHAT_FORMAT_GUILD = "&8[&c{TAG}&8] ";
    public static List<String> MESSAGES_AUTOMSG = new ArrayList<>();
    public static String TOP = "Przeteleportowano na najwyzszy blok!";
    public static String FLY_POPRAW = "Predkosc speed musi wynosic 1-10!";
    public static String FLY_SPEED = "Ustawile predkosc latania na {SPEEDFLY}";
    public static String FLY_YOU = "Latanie: {FLY}";
    public static String FLY_PLAYER = "Latanie dla {FLYPLAYER} {FLYSTATUS}";
    public static String CLEAR_YOU = "Wyczyszczono ekwipunek!";
    public static String CLEAR_OTHER = "Twoj ekwipunek zostal wyczyszczony przez {PLAYER}!";
    public static String CLEAR_PLAYER = "Wyczysciles eq gracza {PLAYER}!";
    public static String GAMEMODE_GM = "gamemode: {GAMEMODE}";
    public static String GAMEMODE_YOU = "gamemode: {GAMEMODESTATUS} dla {GAMEMODEPLAYER}";
    public static String GAMEMODE_PLAYER = "gamemode: {GAMEMODESTATUS} przez {GAMEMODEPLAYER}";
    public static String VANISH_FALSE = "vanish off";
    public static String VANISH_TRUE = "vanish on";
    public static String VANISH_SEEFALSE = "administrator {VANISHPLAYER} wylaczyl vanisha";
    public static String VANISH_SEETRUE = "administrator {VANISHPLAYER} wlaczyl vanisha";
    public static String COBBLEX_AUTOCX_TRUE = "&6Auto CobbleX &cwylaczono";
    public static String COBBLEX_AUTOCX_FALSE = "&6Auto CobbleX &awlaczono";
    public static String DRAGON_BROADCAST = "{PLAYER} zabil smoka gg!";

    public static String USAGE = "&cPoprawne uzycie: /{USAGE}";

    public static boolean usage(CommandSender sender, String usage) {
        sender.sendMessage(core.USAGE.replace("{USAGE}", usage));
        return false;
    }

    public static void loadLang() {
        try {
            if (!core.file.exists()) {
                core.file.getParentFile().mkdirs();
                InputStream is = SpigotPlugin.getPlugin().getResource(core.file.getName());
                if (is != null) {
                    IOUtil.copy(is, core.file);
                }
            }
            core.c = YamlConfiguration.loadConfiguration(core.file);
            for (Field f : core.class.getFields()) {
                if (core.c.isSet(f.getName().toLowerCase().replaceFirst("_", ",").replace(",", "."))) {
                    f.set(null, core.c.get(f.getName().toLowerCase().replaceFirst("_", ",").replace(",", ".")));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveLang() {
        try {
            for (Field f : core.class.getFields()) {
                core.c.set(f.getName().toLowerCase().replaceFirst("_", ",").replace(",", "."), f.get(null));
            }
            core.c.save(core.file);
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

