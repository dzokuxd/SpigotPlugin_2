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
    public static String MESSAGES_HELP = "&4\u00D7&7&m----------&7[ \u00BB &c&lPOMOC &7\u00AB ]&7&m----------&r&4\u00D7" +
            "\n&7\u00BB &d/kit &7- &fLista dostepnych kitow" +
            "\n&7\u00BB &d/eventy &7- &fSprawdz liste aktywnych eventow" +
            "\n&7\u00BB &d/incognito &7- &fUkrycie swojego nicku oraz gildii" +
            "\n&7\u00BB &d/drop &7- &fZarzadzanie dropem" +
            "\n&7\u00BB &d/spawn &7- &fTeleportacja na spawn" +
            "\n&7\u00BB &d/gameplay &7- &fSprawdz aktualny gameplay" +
            "\n&7\u00BB &d/vip &7- &fInformacje dotyczace rangi VIP" +
            "\n&7\u00BB &d/svip &7- &fInformacje dotyczace rangi SVIP" +
            "\n&7\u00BB &d/ranking <nick> &7- &fStatystyki danego gracza" +
            "\n&7\u00BB &d/wyjebane <nick> &7- &fIgnorowanie msg/tpa od graczy" +
            "\n&7\u00BB &d/ustawienia &7- &fKonfiguracja wiadomosci" +
            "\n&7\u00BB &d/topki &7- &fLista graczy znajdujacych sie w topce" +
            "\n&7\u00BB &d/schowek &7- &fDepozyt koxow/refow/perel/strzal" +
            "\n&7\u00BB &d/osiagniecia &7- &fosiagniecia na serwerze" +
            "\n&7\u00BB &d/youtuber &7- &fInformacje dotyczace rangi YouTube" +
            "\n&7\u00BB &d/gildie &7- &fKomendy gildii" +
            "\n&7\u00BB &d/lvl &7- &fStatystyki Kopania" +
            "\n&7\u00BB &d/helpop &7- &fWiadomosc do administracji" +
            "\n&7\u00BB &d/craftingi &7- &fLista receptur serwerowych" +
            "\n&7\u00BB &d/kosz &7- &fSmietnik" +
            "\n&7\u00BB &d/odbierz &7- &fOdbierz swoje skrzynki" +
            "\n&7\u00BB &d/autocx &7- &fAutomatyczne craftowanie cx'ow" +
            "\n&7\u00BB &d/bloki &7- &fWymien sztabki na bloki" +
            "\n&4\u00D7&7&m----------&7[ \u00BB &c&lPOMOC &7\u00AB ]&7&m----------&r&4\u00D7";
    public static String MESSAGES_YT = "&4\u00D7&7&m----------&7[ \u00BB &3&lMedia &7\u00AB ]&7&m----------&r&4\u00D7" +
            "\n&7\u00BB &d nagraj film i to dostaniesz"+
            "\n&7\u00BB &d/"+
            "\n&4\u00D7&7&m----------&7[ \u00BB &3&lMedia &7\u00AB ]&7&m----------&r&4\u00D7";
    public static String MESSAGES_VIP = "&4\u00D7&7&m----------&7[ \u00BB &6&lVIP &7\u00AB ]&7&m----------&r&4\u00D7" +
            "\n&7\u00BB &d/"+
            "\n&4\u00D7&7&m----------&7[ \u00BB &6&lVIP &7\u00AB ]&7&m----------&r&4\u00D7";
    public static String MESSAGES_SVIP = "&4\u00D7&7&m----------&7[ \u00BB &d&lSVIP &7\u00AB ]&7&m----------&r&4\u00D7" +
            "\n&7\u00BB &d/"+
            "\n&4\u00D7&7&m----------&7[ \u00BB &d&lSVIP &7\u00AB ]&7&m----------&r&4\u00D7";
    public static String CHAT_FORMAT_GLOBAL = "&8[{LVL}] {GUILD}{PREFIX}{PLAYER}&8\u00BB &r{SUFFIX}{MESSAGE}";
    public static String CHAT_FORMAT_ADMIN = "{PREFIX}{PLAYER} &8\u00BB &r{SUFFIX}{MESSAGE}";
    public static String CHAT_FORMAT_GUILD = "&8[&c{TAG}&8] ";
    public static List<String> MESSAGES_AUTOMSG = new ArrayList<>();
    public static String TOP = "&aPrzeteleportowano na najwyzszy blok!";
    public static String FLY_POPRAW = "&cPredkosc speed musi wynosic 1-10!";
    public static String FLY_SPEED = "&aUstawile predkosc latania na {SPEEDFLY}";
    public static String FLY_YOU = "&fLatanie: &d{FLY}";
    public static String FLY_PLAYER = "&d{FLYSTATUS} &flatanie dla &d{FLYPLAYER}";
    public static String CLEAR_YOU = "&aWyczyszczono ekwipunek!";
    public static String CLEAR_OTHER = "&cTwoj ekwipunek zostal wyczyszczony przez {PLAYER}!";
    public static String CLEAR_PLAYER = "&aWyczysciles eq gracza {PLAYER}!";
    public static String GAMEMODE_GM = "&fZmieniles tryb gry na &d{GAMEMODE}";
    public static String GAMEMODE_YOU = "&fZmieniles tryb gry na &d{GAMEMODESTATUS} &fdla &d{GAMEMODEPLAYER}";
    public static String GAMEMODE_PLAYER = "&d{GAMEMODEPLAYER} &fzmienił twoj tryb na &d{GAMEMODESTATUS}";
    public static String VANISH_FALSE = "&cOd teraz jesteś widoczny";
    public static String VANISH_TRUE = "&cStałeś się niewidoczny";
    public static String VANISH_SEEFALSE = "&3Administrator {VANISHPLAYER} jest widoczny";
    public static String VANISH_SEETRUE = "&3Administrator {VANISHPLAYER} jest teraz niewidoczny";
    public static String COBBLEX_AUTOCX_TRUE = "&fAuto CobbleX &cwylaczono";
    public static String COBBLEX_AUTOCX_FALSE = "&fAuto CobbleX &awlaczono";
    public static String DRAGON_BROADCAST = "&4&lGracz {PLAYER} zabil smoka! Gratulacje!";

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

