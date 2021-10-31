package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;

public class TurboTask extends BukkitRunnable {
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (CombatManager.isFighting(p)) {
                continue;
            }
            if (statues.EVENTS_TURBO > System.currentTimeMillis()) {
                ChatUtil.sendActionBar(p,"&eTURBODROP: ("+ DataUtil.secondsToString(statues.EVENTS_TURBO)+")");
            }
        }
    }
}
