package pl.spigotplugin.component;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

import java.util.concurrent.atomic.AtomicInteger;

public class Teleporter {
    private static final String PENDING_MESSAGE = "Teleport nastapi za 10 sekund";
    private static final String ERROR_MESSAGE = "error";
    private static final String SUCCESS_MESSAGE = "success";
    private static final String SUCCESSSPAWN_MESSAGE = "success";

    public static void sendRequest(Player p, Location location) {
        User u = UserManager.getUser(p);
        if (u.getCurrentTeleport() != null) return;

        if (p.hasPermission("chuj")) {
            p.teleport(location);
            return;
        }

        Location first = p.getLocation();
        AtomicInteger atomicInteger = new AtomicInteger(1);
        p.sendMessage(PENDING_MESSAGE);

        u.setCurrentTeleport(Bukkit.getScheduler().runTaskTimer(SpigotPlugin.getPlugin(), () -> {

            if (Bukkit.getPlayer(p.getUniqueId()) == null) {
                u.getCurrentTeleport().cancel();
                u.setCurrentTeleport(null);
                return;
            }

            if (first.distance(p.getLocation()) > .8) {
                u.getCurrentTeleport().cancel();
                u.setCurrentTeleport(null);
                p.sendMessage(ERROR_MESSAGE);
                return;
            }

            if (atomicInteger.getAndIncrement() >= 10) {
                p.sendMessage(SUCCESS_MESSAGE);
                u.getCurrentTeleport().cancel();
                u.setCurrentTeleport(null);
            }
            if (atomicInteger.getAndIncrement() >= 10) {
                p.sendMessage(SUCCESSSPAWN_MESSAGE);
                u.getCurrentTeleport().cancel();
                u.setCurrentTeleport(null);
            }
        }, 0, 20));
    }
}
