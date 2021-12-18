package pl.spigotplugin.managers;

import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.DataUtil;

import java.util.*;

public class TopsManager {

    public static final List<Guild> guildRankings = new LinkedList<>();

    public static List<User> stone = new ArrayList<>();
    public static List<User> time = new ArrayList<>();
    public static List<User> gapple = new ArrayList<>();
    public static List<User> apple = new ArrayList<>();
    public static List<User> obsidian = new ArrayList<>();
    public static List<User> pearls = new ArrayList<>();
    public static List<User> arrows = new ArrayList<>();
    public static List<User> lvl = new ArrayList<>();
    public static List<User> coins = new ArrayList<>();
    public static List<User> easycase = new ArrayList<>();
    public static List<User> case6 = new ArrayList<>();
    public static List<User> kills = new ArrayList<>();
    public static List<User> asysty = new ArrayList<>();
    public static List<User> points = new ArrayList<>();

    public static void add(User u) {
        stone.add(u);
        time.add(u);
        gapple.add(u);
        apple.add(u);
        obsidian.add(u);
        pearls.add(u);
        arrows.add(u);
        lvl.add(u);
        coins.add(u);
        easycase.add(u);
        case6.add(u);
        kills.add(u);
        asysty.add(u);
        points.add(u);
    }

    public static void sortUser() {
        points.sort((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()));
        stone.sort((o1, o2) -> Integer.compare(o2.getWykStone(), o1.getWykStone()));
        gapple.sort((o1, o2) -> Integer.compare(o2.getKoxEaten(), o1.getKoxEaten()));
        apple.sort((o1, o2) -> Integer.compare(o2.getRefilEaten(), o1.getRefilEaten()));
        obsidian.sort((o1, o2) -> Integer.compare(o2.getWykObsidian(), o1.getWykObsidian()));
        pearls.sort((o1, o2) -> Integer.compare(o2.getPearlThrown(), o1.getPearlThrown()));
        arrows.sort((o1, o2) -> Integer.compare(o2.getArrowsShoten(), o1.getArrowsShoten()));
        lvl.sort((o1, o2) -> Integer.compare(o2.getLvl(), o1.getLvl()));
        coins.sort((o1, o2) -> Integer.compare(o2.getCoins(), o1.getCoins()));
        easycase.sort((o1, o2) -> Integer.compare(o2.getEasycase(), o1.getEasycase()));
        case6.sort((o1, o2) -> Integer.compare(o2.getCase611(), o1.getCase611()));
        kills.sort((o1, o2) -> Integer.compare(o2.getKills(), o1.getKills()));
        asysty.sort((o1, o2) -> Integer.compare(o2.getAsysty(), o1.getAsysty()));
    }

    public static void sortGuild() {
        guildRankings.sort((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()));
    }

    public static int getPlaceUser(User user) {
        for (int num = 0; num < TopsManager.points.size(); ++num) {
            if (TopsManager.points.get(num).equals(user)) {
                return num + 1;
            }
        }
        return 0;
    }


}
