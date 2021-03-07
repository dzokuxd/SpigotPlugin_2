package pl.spigotplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.spigotplugin.utils.CheckUtil;

public class EnityDamageListener implements Listener {
    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if (CheckUtil.checkedPlayers.contains(player) || CheckUtil.checkedPlayers.contains(damager)) {
                event.setCancelled(true);
            }
        }
    }
}
