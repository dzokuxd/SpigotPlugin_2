package pl.spigotplugin.managers;

import org.bukkit.entity.Player;
import pl.spigotplugin.utils.CombatUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CombatManager {
    private static Map<Player, CombatUtil> combats = new ConcurrentHashMap<>();

    public static CombatUtil getCombat(Player p) {
        return CombatManager.combats.get(p);
    }

    public static void removeCombat(Player p) {
        CombatManager.combats.remove(p);
    }

    public static Map<Player, CombatUtil> getCombats() {
        return CombatManager.combats;
    }
}
