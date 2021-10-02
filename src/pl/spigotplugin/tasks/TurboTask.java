package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.CombatUtil;
import pl.spigotplugin.utils.DataUtil;

public class TurboTask extends BukkitRunnable {
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (CombatManager.isFighting(p)) {
                continue;
            }
            if (Config.EVENTS_TURBO > System.currentTimeMillis()) {
                ChatUtil.sendActionBar(p,"&eTURBODROP: ("+ DataUtil.secondsToString(Config.EVENTS_TURBO)+")");
            }
            Guild g = GuildManager.getGuild(p);
            if (g != null) {
                if (g.getLastExplodeTime() > System.currentTimeMillis()) {
                    ChatUtil.sendActionBar(p,"&r &8| &cMozliwosc budowania za: " + DataUtil.secondsToString(g.getLastExplodeTime()));
                }
            }
        }
    }
}
