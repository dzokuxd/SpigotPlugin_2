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
            Player p = u.getPlayer();
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 20.0f, 20.0f);
            ChatUtil.sendTitleMessage(p, "&fAwansowales na &d" + u.getLvl() + " &fpoziom!", "&fGratulacje &d"+u.getName(), 30, 70, 40);
            p.sendMessage(ChatUtil.color("&fAwansowales na &d" + u.getLvl() + " &fpoziom!"));
            if (u.getLvl() >= 10 && u.getLvl() % 10 == 0) {
                u.save();
                ItemUtil.giveItems(p, VoucherUtil.turbo);
                Bukkit.broadcastMessage(ChatUtil.color("&fGracz &d" + u.getName() + " &fawansowal na &d" + u.getLvl() + " &fpoziom!"));
            }
        }
    }
}
