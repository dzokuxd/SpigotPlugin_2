package pl.spigotplugin.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityUtil {
    public static Player getDamager(final EntityDamageByEntityEvent event) {
        final Entity damager = event.getDamager();
        if (damager instanceof Player) {
            return (Player)damager;
        }
        if (damager instanceof Projectile) {
            final Projectile p = (Projectile)damager;
            if (p.getShooter() instanceof Player) {
                return (Player)p.getShooter();
            }
        }
        return null;
    }
}
