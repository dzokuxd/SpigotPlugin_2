package pl.spigotplugin.managers;

import org.bukkit.entity.Player;
import pl.spigotplugin.objects.guild.Fight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CombatManager {
    private static final Map<Player, Fight> fightMap;

    public static Fight create(final Player p) {
        final Fight value = new Fight(p, null, 0L);
        CombatManager.fightMap.put(p, value);
        return value;
    }

    public static boolean isFighting(final Player p) {
        final Fight fight = get(p);
        return fight.getFightTime() > System.currentTimeMillis();
    }

    public static Fight get(final Player p) {
        final Fight f = CombatManager.fightMap.get(p);
        if (f == null) {
            return create(p);
        }
        return f;
    }

    public static void clear(final Player p) {
        if (isFighting(p)) {
            final Fight fight = get(p);
            fight.setAttacked(null);
            fight.setFightTime(0L);
            CombatManager.fightMap.remove(p);
        }
    }

    public static Map<Player, Fight> getFightMap() {
        return CombatManager.fightMap;
    }

    static {
        fightMap = new ConcurrentHashMap<>();
    }
}
