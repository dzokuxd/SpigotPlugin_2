package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.objects.guild.Fight;
import pl.spigotplugin.utils.ChatUtil;

import java.util.Collection;

public class CombatTask extends BukkitRunnable {
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        final Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (final Player p : onlinePlayers) {
            if (!CombatManager.getFightMap().containsKey(p)) {
                continue;
            }
            final Fight value = CombatManager.getFightMap().get(p);
            if (value.getFightTime() > currentTimeMillis) {
                int time = (int)((value.getFightTime() - currentTimeMillis) / 1000L);
                if (time != 0) {
                    ChatUtil.sendActionBar(p, "&4&lAntylogout - " + time);
                }
                else {
                    ChatUtil.sendActionBar(p, "&aAntylogout!");
                }
            }
            else {
                if (value.getAttacked() == null) {
                    continue;
                }
                if (value.getFightTime() > currentTimeMillis) {
                    continue;
                }
                value.setAttacked(null);
                p.sendMessage("&aKoniec walki!");
                CombatManager.getFightMap().remove(p);
            }
        }
    }
}
