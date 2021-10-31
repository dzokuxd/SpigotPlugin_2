package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

import java.util.concurrent.TimeUnit;

public class AutoMsgTask extends BukkitRunnable {
    int index = 0;

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            User u = UserManager.getUser(p);
            u.setTime(u.getTime() + TimeUnit.MINUTES.toMillis(1));

            if (core.MESSAGES_AUTOMSG.isEmpty())
                return;

            if (u.isAutoMessages()) {
                p.sendMessage(" ");
                p.sendMessage(core.MESSAGES_AUTOMSG.get(index));
                p.sendMessage(" ");
            }
        }
        index++;
        if (index >= core.MESSAGES_AUTOMSG.size()) {
            index = 0;
        }

    }
}
