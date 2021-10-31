package pl.spigotplugin.configs;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.utils.IOUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class guild {

    private static final File file = new File(SpigotPlugin.getPlugin().getDataFolder(), "guild.yml");
    private static FileConfiguration c = null;

    public static String GUILDHELP_MESSAGE = "&7&m-------------&r&7[  &c&lKomendy gildii  &7]&7&m-------------"+
            "\n&c/g zaloz <tag> <pelna_nazwa> &7- &6zalozenie gildii"+
            "\n&c/g dolacz <tag/nazwa> &7- &6dolaczasz do gildii"+
            "\n&c/g opusc &7- &6opuszczasz gildie"+
            "\n&c/g dom &7- &6teleportacja do gildii"+
            "\n&c/g odnow &7- &6oplaca gildie na 1 dni"+
            "\n&c/g ustawdom &7- &6ustawia baze gildii"+
            "\n&c/g wyrzuc <nick> &7- &6wyrzuca gracza z gildii"+
            "\n&c/g zapros <nick/all> &7- &6zaprasza gracza do gildii"+
            "\n&c/g lider <nick> &7- &6przekazuje wlasciciela gildii"+
            "\n&c/g zastepca <nick> &7- &6zmienia zastepce gildii"+
            "\n&c/g wojna &7- &6wywolywanie wojen gildyjnych"+
            "\n&c/g pvp &7- &6wlacza/wylacza pvp w gildii"+
            "\n&c/g pvp sojusz &7- &6wlacza/wylacza pvp w sojuszu"+
            "\n&c/g zapisz &7- &6zapisuje gildie na event"+
            "\n&c/gildia <gildia> &7- &6informacje o gildii"+
            "\n "+
            "\n&c! &8- &6Wiadomosc do gildii"+
            "\n&c!! &8- &6Wiadomosc do sojuszy"+
            "\n&c@ &8- &6Wiadomosc o pomoc do gildii"+
            "\n&7&m-------------&r&7[  &c&lKomendy gildii  &7]&7&m-------------";

    public static String PLAYER_ISOFFLINE = "Gracz jest offline";
    public static String PLAYER_USERNULL = "gracza nie bylo nigdy na serwerze";
    public static String PLAYER_DONTHAVEAGUILD = "&cGracz nie jest w twojej gildii!";
    public static String PLAYERYOU_DONTHAVEAGUILD = "Nie posiadasz gildii!";
    public static String PLAYER_NOPERMISSION = "nie posiadasz permisji";
    public static ItemStack COST_HP = new ItemStack(Material.DIAMOND, 8);
    public static ItemStack COST_LIMIT = new ItemStack(Material.DIAMOND, 8);
    public static ItemStack COST_POWIEKSZ = new ItemStack(Material.DIAMOND, 8);

    public static String CREATE_BROADCAST = "gildia {TAG} - {NAME} zostala zalozona przez {PLAYER}";
    public static String CREATE_HAVEGUILD = "posiadasz juz gildie!";
    public static String CREATE_WRONGTAGANDNAME = "tag gildi musi zawierac 2-5 zankow, nawzwa 4-32 znakow";
    public static String CREATE_ALLREADYEXISTSBYSHORTCUT = "Istenieje juz gildia o takim tagu!";
    public static String CREATE_ALLREADYEXISTSBYFULLNAME = "Istnieje juz gildia o takiej nazwie";
    public static String CREATE_SHORTCUTNOTALPHANUMERIC = "Tag nie moze byc alfanumeryczny";
    public static String CREATE_FULLNAMENOTALPHANUMERIC = "Nazwa nie moze byc alfanumeryczna";
    public static String CREATE_TOCLOSESPAWN = "Gildie mozna zakladac 250 kratek od spawnu!";
    public static String CREATE_TITLE = "zalozyles gildie {TAG}";
    public static String CREATE_SUBTITLE = "gratulacje {PLAYER}";
    public static List<ItemStack> CREATE_COST = Arrays.asList(
            new ItemStack(Material.DIAMOND, 64),
            new ItemStack(Material.GOLDEN_APPLE, 64),
            new ItemStack(Material.BOOKSHELF, 64),
            new ItemStack(Material.GLASS, 64),
            new ItemStack(Material.TNT, 64),
            new ItemStack(Material.LEAVES, 64),
            new ItemStack(Material.ENDER_PEARL, 16),
            new ItemStack(Material.ANVIL, 64),
            new ItemStack(Material.HAY_BLOCK, 64)
    );

    public static String INVITE_TARGET1 = "zostales zaproszony do gildii {TAG} przez {PLAYER}";
    public static String INVITE_TARGET2 = "wpisz /g dolacz {TAG}, aby dolaczyc do gildii!";
    public static String INVITE_GROUPMESSAGE = "Brak ludzi dookola ciebie w promieniu 5 kratek";
    public static String INVITE_GROUPERROR = "Jedna osoba posiada juz zaproszenie";
    public static String INVITE_COFNIETE1 = "zaproszenie do gildi {TAG} zostalo cofniete przez {PLAYER}";
    public static String INVITE_COFNIETE2 = "Cofnales zaproszenie do gildii dla gracza {TARGET}";
    public static ItemStack INVITE_COST = new ItemStack(Material.DIAMOND, 8);

    public static String JOIN_GUILDNOTFOUND = "&cGildia o takim tagu nie istnieje!";
    public static String JOIN_NOTINVITED = "&6Nie posiadasz zaproszenia do gildii &c{TAG}";
    public static String JOIN_HAVEMAXPLAYERS = "&cGildia do ktorej chcesz dolaczyc posiada maksymalna liczbe czlonkow!";
    public static String JOIN_BROADCAST = "&6Gracz &c{PLAYER} &6dolaczyl do gildii &7{TAG}";
    public static String JOIN_TELEPORT = "&cNie mozesz teleportowac sie na terenie wrogiej gildii!";

    public static String FRIENDLYFIRE_GUILD = "gracz {PLAYER} {GUILDSTATE} pvp w gildii";
    public static String FRIENDLYFIRE_ALLY = "gracz {PLAYER} {ALLYSTATE} pvp w sojuszu";

    public static String REGEN_BLOCKS = "&cGildia nie posiada zadnych blokow do regeneracji!";
    public static String REGEN_TNTBETWEEN = "&cGildie mozesz regenerowac gdy TNT jest wylaczone";
    public static String REGEN_ISSTARTED = "&cTwoja gildia jest wlasnie regenerowana";

    public static String WAR_MYGUILD = "&cNie mozesz wywolac wojny z wlasna gildia!";
    public static String WAR_ALLY = "&cNie mozesz wyzywac sojuszy do wojny!";
    public static String WAR_BETWEEN = "&cWojny mozesz wywolywac tylko gdy tnt jest wylaczone!";
    public static String WAR_ISSET = "&cTwoja gildia posiada juz wojne z {TARGETGUILD}";
    public static String WAR_BROADCAST = "gildia {TAG} wyzwala gildie {TARGETGUILD} na wojne";
    public static String WAR_TITLE = "&c&lWOJNY";
    public static String WAR_SUBTITLE = "&6Wywolales wojne z gildia &c{TARGETGUILD}";

    public static String ALLY_MYGUILD = "&cNie mozesz zawrzec sojuszu z wlasna gildia!";
    public static String ALLY_NOTALLY = "&cNie posiadasz sojuszu z gildia {TARGETGUILD}";
    public static String ALLY_BREAKBROADCAST = "gildia {TAG} &6zerwala sojusz z gildia {TARGETGUILD}";
    public static String ALLY_SUCCESS = "gildia {TAG} &6zawarla sojusz z gildia {TARGETGUILD}";
    public static String ALLY_ALLREADYALLY = "&cGildia posiada juz sojusz z  {TARGETGUILD}";
    public static String ALLY_CHUJ = "&6Zaproszenie do sojuszu z gildia {TARGETGUILD} &6zostalo cofniete!";
    public static String ALLY_CHUJ1 = "gildia {TAG} &6cofnela zaproszenie do sojuszu!";
    public static String ALLY_MAX = "gildia {TAG} &6cofnela zaproszenie do sojuszu!";
    public static String ALLY_INVITED = "&6Wyslales zaproszenie do sojuszu z gildia {TARGETGUILD}";
    public static String ALLY_LEADER = "&cLider gildii {LEADER} jest offline!";
    public static String ALLY_MESSAGETOMEMBERS1 = "&6Twoja gildia otrzymala zaproszenie dosojuszu z gildia {TAG}";
    public static String ALLY_MESSAGETOMEMBERS2 = "&6Wpisz &c/g sojusz zawrzyj {TAG} &6, aby zaakceptowac!";
    public static ItemStack ALLY_COST = new ItemStack(Material.DIAMOND, 8);

    public static String LEAVE_BROADCAST = "gracz {PLAYER} opuscil gildie {TAG}";

    public static String LIDER_BROADCAST = "gracz {TARGET} zostal nowym liderem gildii {TAG}";
    public static ItemStack LEADER_COST = new ItemStack(Material.DIAMOND, 8);

    public static String DEPUTY_CHANGE = "gracz {TARGET} zostal nowym zastepca gildii {TAG}";
    public static String DEPUTY_BROADCAST = "gracz {TARGET} nie jest juz zastepca gildii {TAG}";
    public static ItemStack DEPUTY_COST = new ItemStack(Material.DIAMOND, 8);

    public static String RENEW_MAX = "&cGildia jest przedluzona na maksymalny okres";
    public static String RENEW_SEND = "&6Przedluzylesz waznosc gildii o &c{RENEWADD}";
    public static int RENEW_ADD = 1;
    public static ItemStack RENEW_COST = new ItemStack(Material.DIAMOND, 8);

    public static String SETHOME_WRONGTERRAIN = "&cBaze gildii mozesz ustawic tylko na terenie gildii!";
    public static String SETHOME_SUCCESS = "&aUstawiles baze gildii!";

    public static String KICK_BROADCAST = "{TARGET} zostal wyjebany z gildii {TAG}";
    public static String KICK_CANTKICKLEADER = "Nie mozesz wyrzuci zalozyciela";
    public static String KICK_NOTINYOURGUILD = "Gracz nie jest w twojej gildii!";
    public static String KICK_SAMEGOSIEBIE = "Nie mozesz wyrzucic samego siebie!";

    public static String DELETE_GUILD = "&6Potwierdz usuniecie gildii: &c/g usun <{CODE}>";
    public static String DELETE_INVAILDCODE = "&cPodales zly kod";
    public static String DELETE_USAGECODE = "&6Prawidlowe uzycie: &c/g usun <kod>";
    public static String DELETE_BROADCAST = "gracz {PLAYER} rozwiazal gildie [{TAG}] - [{NAME}]";
    public static String DELETE_TITLE = "&cUsunales gildie: {TAG}";
    public static String DELETE_SUBTITLE = "Gratulacje {PLAYER}";

    public static int CUBOID_TNT_OD = 11;
    public static int CUBOID_TNT_DO = 22;
    public static int CUBOID_PROLONG_ADD = 1;
    public static int CUBOID_PROLONG_MAX = 4;
    public static int CUBOID_PROLONG_START = 2;
    public static int CUBOID_SIZE_START = 20;
    public static int CUBOID_SIZE_MAX = 50;
    public static int CUBOID_SIZE_ADD = 2;
    public static int CUBOID_PROTECTION_HOWHOUR = 24;
    public static List<String> BLOCKED_INCOMBAT = Arrays.asList("spawn", "home", "sethome", "tpa", "tpaccept", "tpdeny", "repair", "workbench", "ec", "baza", "ustawbaza");
    public static List<String> BLOCKED_INGUILD = Arrays.asList("spawn", "home", "sethome", "tpa", "tpaccept", "tpdeny", "baza", "ustawbaza");

    public static void loadLang() {
        try {
            if (!guild.file.exists()) {
                guild.file.getParentFile().mkdirs();
                InputStream is = SpigotPlugin.getPlugin().getResource(file.getName());
                if (is != null) {
                    IOUtil.copy(is, file);
                }
            }
            c = YamlConfiguration.loadConfiguration(file);
            for (Field f : guild.class.getFields()) {
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
            for (Field f : guild.class.getFields()) {
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
