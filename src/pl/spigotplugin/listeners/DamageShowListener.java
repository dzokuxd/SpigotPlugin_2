package pl.spigotplugin.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.projectiles.ProjectileSource;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

public class DamageShowListener implements Listener {
    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent event) {
        Player damager = event.getPlayer();
        if (!(event.getCaught() instanceof Player)) {
            return;
        }
        Player victim = (Player) event.getCaught();
        String name = victim.getName();
        User u = UserManager.getUser(victim);
        if (u.isIncognito()) name = "&k" + victim.getName();
        damager.sendMessage("&c" + name + " &6ma &c" + ((Damageable) event.getCaught()).getHealth() + " &4\u2764");
    }

    @EventHandler
    public void onPlayerShootEvent(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player victim = (Player) event.getEntity();
        if (!(event.getDamager() instanceof Arrow)) {
            return;
        }
        ProjectileSource source = ((Arrow) event.getDamager()).getShooter();
        if (!(source instanceof Player)) {
            return;
        }
        Player damager = (Player) source;
        String name = victim.getName();
        User u = UserManager.getUser(victim);
        if (u.isIncognito()) name = "&k" + victim.getName();
        damager.sendMessage("&c" + name + " &6ma &c" + ((Damageable) event.getEntity()).getHealth() + " &4\u2764");
    }

    @EventHandler
    public void onPlayerSnowballEvent(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player victim = (Player) event.getEntity();
        if (!(event.getDamager() instanceof Snowball)) {
            return;
        }
        ProjectileSource source = ((Snowball) event.getDamager()).getShooter();
        if (!(source instanceof Player)) {
            return;
        }
        Player damager = (Player) source;
        String name = victim.getName();
        User u = UserManager.getUser(victim);
        if (u.isIncognito()) name = "&k" + victim.getName();
        damager.sendMessage("&c" + name + " &6ma &c" + ((Damageable) event.getEntity()).getHealth() + " &4\u2764");
    }
}
