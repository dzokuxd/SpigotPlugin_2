package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.managers.TopsManager;
import pl.spigotplugin.utils.TabUtil;

public class TabUpdate extends BukkitRunnable {

    @Override
    public void run() {
        if(TabUtil.i == 1) {
            TabUtil.i = 0;
        } else {
            ++TabUtil.i;
        }

        TopsManager.sortUser();
        TopsManager.sortGuild();

        for (Player p : Bukkit.getOnlinePlayers()) {
            TabUtil.update(p);
        }

    }

}
