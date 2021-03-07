package pl.spigotplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

public class AutoMsgTask extends BukkitRunnable {
    int index = 0;

    @Override
    public void run() {
        if (GlobalMessage.MESSAGES_AUTOMSG.size() == 0) {
            return;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            User u = UserManager.getUser(p);
            if (u.isAutoMessages()) {
                p.sendMessage(GlobalMessage.MESSAGES_AUTOMSG.get(index));
            }
        }
        index++;
        if (index >= GlobalMessage.MESSAGES_AUTOMSG.size()) {
            index = 0;
        }

    }
}
