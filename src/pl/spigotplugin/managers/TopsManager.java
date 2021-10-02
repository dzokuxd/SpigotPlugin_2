package pl.spigotplugin.managers;

import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;

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

/*      stone.sort(Comparator.comparing(User::getWykStone).reversed());
        time.sort(Comparator.comparing(User::getTime).reversed());
        gapple.sort(Comparator.comparing(User::getKoxEaten).reversed());
        apple.sort(Comparator.comparing(User::getRefilEaten).reversed());
        obsidian.sort(Comparator.comparing(User::getWykObsidian).reversed());
        pearls.sort(Comparator.comparing(User::getPearlThrown).reversed());
        arrows.sort(Comparator.comparing(User::getArrowsShoten).reversed());
        lvl.sort(Comparator.comparing(User::getLvl).reversed());
        coins.sort(Comparator.comparing(User::getCoins).reversed());
        easycase.sort(Comparator.comparing(User::getEasycase).reversed());
        kills.sort(Comparator.comparing(User::getKills).reversed());
        asysty.sort(Comparator.comparing(User::getAsysty).reversed());
        case6.sort(Comparator.comparing(User::getCase611).reversed());//TODO zmienic na postawione casy*/
    }

    public static void sortGuild() {
        guildRankings.sort((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()));
    }

    public static int getPlaceUser(User user) {
        for (int num = 0; num < TopsManager.stone.size(); ++num) {
            if (TopsManager.stone.get(num).equals(user)) {
                return num + 1;
            }
        }
        return 0;
    }


}
