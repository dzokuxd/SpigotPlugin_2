package pl.spigotplugin.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.CombatUtil;
import pl.spigotplugin.utils.DataUtil;

public class CombatTask extends BukkitRunnable {
    public void run() {
        for (CombatUtil u : CombatManager.getCombats().values()) {
            if (u == null) {
                continue;
            }
            Player p = u.getPlayer();
            if (u.hasFight()) {
                String str = "&4&lAnty Logout:" + DataUtil.secondsToString(u.getLastAttactTime());
                ChatUtil.sendActionBar(p, str);
                continue;
            }
            if (!u.wasFight() || u.hasFight()) {
                continue;
            }
            p.sendMessage("&aSkonczyles walczyc! Mozesz sie bezpiecznie wylogowac");
            u.setLastAttactkPlayer(null);
            u.setLastAsystPlayer(null);
        }
    }
}
