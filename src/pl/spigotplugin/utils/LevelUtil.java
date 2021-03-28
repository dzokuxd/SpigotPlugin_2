package pl.spigotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pl.spigotplugin.objects.user.User;

public class LevelUtil {
    public static void checkLevel(User u) {
        int lvl = u.getLvl();
        if (lvl > 100) {
            return;
        }
        int wzor = u.getLvl() * 100 * u.getLvl();
        int exp = u.getExp();
        if (exp >= wzor) {
            u.setLvl(u.getLvl() + 1);
            u.save();
            Player p = u.getPlayer();
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 20.0f, 20.0f);
            ChatUtil.sendTitleMessage(p, "&6Awansowales na &c" + u.getLvl() + " &6poziom!", "&6Gratulacje &c"+u.getName(), 30, 70, 40);
            p.sendMessage("&7*** &6Awansowales na &c" + u.getLvl() + " &6poziom! &7***");
            if (u.getLvl() >= 10 && u.getLvl() % 10 == 0) {
                u.save();
                Bukkit.broadcastMessage("&7\u00bb &6Gracz &c" + u.getName() + " &6awansowal na &c" + u.getLvl() + " &6poziom!");
            }
        }
    }
}
