package pl.spigotplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.utils.CheckUtil;
import pl.spigotplugin.utils.CuboidUtil;

public class EnityDamageListener implements Listener {
    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            if (CheckUtil.checkedPlayers.contains(player) || CheckUtil.checkedPlayers.contains(damager)) {
                e.setCancelled(true);
            }
        }
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player && e.getEntity().getWorld().getName().equals("world")) {
            if (CuboidUtil.isSpawn(e.getEntity().getLocation())) {
                if (e.getEntity().getLocation().getBlockY() <= (Config.REGION_BYPASSY)) {
                    return;
                }
                e.setCancelled(true);
            }
            else if (CuboidUtil.isSpawn(e.getDamager().getLocation()) && CuboidUtil.isOutsideSpawn(e.getEntity().getLocation())) {
                if (e.getEntity().getLocation().getBlockY() <= (Config.REGION_BYPASSY)) {
                    return;
                }
                e.setCancelled(true);
            }
        }
        if (e.getEntity() instanceof Player) {
            final Player p = (Player)e.getEntity();
            if (p.getLocation().getBlockY() <= (Config.REGION_BYPASSY)) {
                return;
            }
            if (CuboidUtil.isOutsideSpawn(p.getLocation())) {
                e.setCancelled(true);
            }
        }
    }
}
