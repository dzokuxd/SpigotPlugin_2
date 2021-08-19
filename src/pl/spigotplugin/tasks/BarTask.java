package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.api.BossBarApi;

public class BarTask extends BukkitRunnable {
    @Override
    public void run() {
        for (String player : BossBarApi.getPlayers()) {
            final Player bukkitPlayer = Bukkit.getPlayer(player);

            if (bukkitPlayer == null)
                continue;

            BossBarApi.teleportBar(bukkitPlayer);
        }
    }
}
