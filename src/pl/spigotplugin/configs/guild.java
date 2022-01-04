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

    public static String GUILDHELP_MESSAGE = "&7&m-------------&r&7[  &d&lKomendy gildii  &7]&7&m-------------"+
            "\n&d/g zaloz <tag> <pelna_nazwa> &7- &fzalozenie gildii"+
            "\n&d/g dolacz <tag/nazwa> &7-&fdolaczasz do gildii"+
            "\n&d/g opusc &7- &fopuszczasz gildie"+
            "\n&d/g dom &7- &fteleportacja do gildii"+
            "\n&d/g odnow &7- &foplaca gildie na 1 dni"+
            "\n&d/g ustawdom &7- &fustawia baze gildii"+
            "\n&d/g wyrzuc <nick> &7- &fwyrzuca gracza z gildii"+
            "\n&d/g zapros <nick/all> &7- &fzaprasza gracza do gildii"+
            "\n&d/g lider <nick> &7- &fprzekazuje wlasciciela gildii"+
            "\n&d/g zastepca <nick> &7- &fzmienia zastepce gildii"+
            "\n&d/g wojna &7- &fwywolywanie wojen gildyjnych"+
            "\n&d/g pvp &7- &fwlacza/wylacza pvp w gildii"+
            "\n&d/g pvp sojusz &7- &fwlacza/wylacza pvp w sojuszu"+
            "\n&d/g zapisz &7- &fzapisuje gildie na event"+
            "\n&d/gildia <gildia> &7- &finformacje o gildii"+
            "\n "+
            "\n&d! &8- &fWiadomosc do gildii"+
            "\n&d!! &8- &fWiadomosc do sojuszy"+
            "\n&d@ &8- &fWiadomosc o pomoc do gildii"+
            "\n&7&m-------------&r&7[  &d&lKomendy gildii  &7]&7&m-------------";

    public static String PLAYER_ISOFFLINE = "&cGracz jest offline";
    public static String PLAYER_USERNULL = "&cTaki gracz nigdy nie był na serwerze";
    public static String PLAYER_DONTHAVEAGUILD = "&cGracz nie jest w twojej gildii!";
    public static String PLAYER_YOUDONTHAVEAGUILD = "&cNie posiadasz gildii!";
    public static String PLAYER_NOPERMISSION = "&cNie posiadasz permisji";
    public static ItemStack COST_HP = new ItemStack(Material.DIAMOND, 8);
    public static ItemStack COST_LIMIT = new ItemStack(Material.DIAMOND, 8);
    public static ItemStack COST_POWIEKSZ = new ItemStack(Material.DIAMOND, 8);
    public static ItemStack COST_WITHER = new ItemStack(Material.DIAMOND, 8);

    public static String CREATE_BROADCAST = "&d{PLAYER} &fzalożył gildie &d{TAG} &f- &d{NAME}";
    public static String CREATE_HAVEGUILD = "&cPosiadasz juz gildie!";
    public static String CREATE_WRONGTAGANDNAME = "&cTag gildii musi zawierac &d2-5 &fznakow, nazwa &d4-32 &fznakow";
    public static String CREATE_ALLREADYEXISTSBYSHORTCUT = "&cIstenieje juz gildia o takim tagu!";
    public static String CREATE_ALLREADYEXISTSBYFULLNAME = "&cIstnieje juz gildia o takiej nazwie!";
    public static String CREATE_SHORTCUTNOTALPHANUMERIC = "&cTag nie moze byc alfanumeryczny";
    public static String CREATE_FULLNAMENOTALPHANUMERIC = "&cNazwa nie moze byc alfanumeryczna";
    public static String CREATE_TOCLOSESPAWN = "&cGildie mozna zakladac 250 kratek od spawnu!";
    public static String CREATE_TITLE = "&fZalożyleś gildie &d{TAG}";
    public static String CREATE_SUBTITLE = "&fGratulacje &d{PLAYER}";
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

    public static String INVITE_TARGET1 = "&fZostales zaproszony do gildii &d{TAG} &fprzez &d{PLAYER}";
    public static String INVITE_TARGET2 = "&fwpisz &d/g dolacz {TAG}&f, aby dolaczyc do gildii!";
    public static String INVITE_GROUPMESSAGE = "&cBrak ludzi dookola ciebie w promieniu 5 kratek";
    public static String INVITE_GROUPERROR = "&cJedna osoba posiada juz zaproszenie";
    public static String INVITE_COFNIETE1 = "&fZaproszenie do gildi &d{TAG} &fzostalo &ccofniete &fprzez &d{PLAYER}";
    public static String INVITE_COFNIETE2 = "&fCofnales zaproszenie do gildii dla gracza &d{TARGET}";
    public static ItemStack INVITE_COST = new ItemStack(Material.DIAMOND, 8);

    public static String JOIN_GUILDNOTFOUND = "&cGildia o takim tagu nie istnieje!";
    public static String JOIN_NOTINVITED = "&cNie posiadasz zaproszenia do gildii {TAG}";
    public static String JOIN_HAVEMAXPLAYERS = "&cGildia do ktorej chcesz dolaczyc posiada maksymalna liczbe czlonkow!";
    public static String JOIN_BROADCAST = "&fGracz &d{PLAYER} &fdolaczyl do gildii &c{TAG}";
    public static String JOIN_TELEPORT = "&cNie mozesz teleportowac sie na terenie wrogiej gildii!";

    public static String FRIENDLYFIRE_GUILD = "&f{PLAYER} {GUILDSTATE} &fpvp w gildi";
    public static String FRIENDLYFIRE_ALLY = "&f{PLAYER} {ALLYSTATE} &fpvp w sojuszu";

    public static String REGEN_BLOCKS = "&cGildia nie posiada zadnych blokow do regeneracji!";
    public static String REGEN_TNTBETWEEN = "&cGildie mozesz regenerowac gdy TNT jest wylaczone";
    public static String REGEN_ISSTARTED = "&cTwoja gildia jest wlasnie regenerowana";

    public static String WAR_MYGUILD = "&cNie mozesz wywolac wojny z wlasna gildia!";
    public static String WAR_ALLY = "&cNie mozesz wyzywac sojuszy do wojny!";
    public static String WAR_BETWEEN = "&cWojny mozesz wywolywac tylko gdy tnt jest wylaczone!";
    public static String WAR_ISSET = "&cTwoja gildia posiada juz wojne z {TARGETGUILD}";
    public static String WAR_BROADCAST = "&fGildia &d{TAG} &fwyzwala gildie &d{TARGETGUILD} &fna wojne";
    public static String WAR_TITLE = "&d&lWOJNY";
    public static String WAR_SUBTITLE = "&fWywolales wojne z gildia &d{TARGETGUILD}";

    public static String ALLY_MYGUILD = "&cNie mozesz zawrzec sojuszu z wlasna gildia!";
    public static String ALLY_NOTALLY = "&cNie posiadasz sojuszu z gildia {TARGETGUILD}";
    public static String ALLY_BREAKBROADCAST = "&fGildia &d{TAG} &fzerwala sojusz z gildia &d{TARGETGUILD}";
    public static String ALLY_SUCCESS = "&fgildia &d{TAG} &fzawarla sojusz z gildia &d{TARGETGUILD}";
    public static String ALLY_ALLREADYALLY = "&cGildia posiada juz sojusz z {TARGETGUILD}";
    public static String ALLY_CHUJ = "&cZaproszenie do sojuszu z gildia {TARGETGUILD} zostalo cofniete!";
    public static String ALLY_CHUJ1 = "&cGildia {TAG} cofnela zaproszenie do sojuszu!";
    public static String ALLY_MAX = "&cTwoja gildia posiada max limit sojuszu";
    public static String ALLY_INVITED = "&fWyslales zaproszenie do sojuszu z gildia &d{TARGETGUILD}";
    public static String ALLY_LEADER = "&cLider gildii {LEADER} jest offline!";
    public static String ALLY_MESSAGETOMEMBERS1 = "&fTwoja gildia otrzymala zaproszenie dosojuszu z gildia &d{TAG}";
    public static String ALLY_MESSAGETOMEMBERS2 = "&fWpisz &d/g sojusz zawrzyj {TAG} &f, aby zaakceptowac!";
    public static ItemStack ALLY_COST = new ItemStack(Material.DIAMOND, 8);

    public static String LEAVE_BROADCAST = "&fGracz &d{PLAYER} &fopuscil gildie &d{TAG}";

    public static String LIDER_BROADCAST = "&fGracz &d{TARGET} &fzostal nowym liderem gildii &d{TAG}";
    public static ItemStack LIDER_COST = new ItemStack(Material.DIAMOND, 8);

    public static String DEPUTY_CHANGE = "&fGracz &d{TARGET} &fzostal nowym zastepca gildii &d{TAG}";
    public static String DEPUTY_BROADCAST = "&fGracz &d{TARGET} &fnie jest juz zastepca gildii &d{TAG}";
    public static ItemStack DEPUTY_COST = new ItemStack(Material.DIAMOND, 8);

    public static String RENEW_MAX = "&cGildia jest przedluzona na maksymalny okres";
    public static int RENEW_ADD = 1;
    public static ItemStack RENEW_COST = new ItemStack(Material.DIAMOND, 8);

    public static String SETHOME_WRONGTERRAIN = "&cBaze gildii mozesz ustawic tylko na terenie gildii!";
    public static String SETHOME_SUCCESS = "&aUstawiles baze gildii!";

    public static String KICK_BROADCAST = "&fGracz &d{TARGET} &fzostal wyrzucony z gildii &d{TAG}";
    public static String KICK_CANTKICKLEADER = "&cNie mozesz wyrzucic zalozyciela";
    public static String KICK_NOTINYOURGUILD = "&cGracz nie jest w twojej gildii!";
    public static String KICK_SAMEGOSIEBIE = "&cNie mozesz wyrzucic samego siebie!";

    public static String DELETE_GUILD = "&fPotwierdz usuniecie gildii: &d/g usun <{CODE}>";
    public static String DELETE_INVAILDCODE = "&cPodales zly kod";
    public static String DELETE_USAGECODE = "&cPrawidlowe uzycie: /g usun <kod>";
    public static String DELETE_BROADCAST = "&fGracz &d{PLAYER} &frozwiazal gildie &d{TAG} &f- &d{NAME}";
    public static String DELETE_TITLE = "&cUsunales gildie: {TAG}";
    public static String DELETE_SUBTITLE = "&cSzkoda {PLAYER}";

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
