package pl.spigotplugin.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {
    @EventHandler
    private void onCreatureSpawnEvent(CreatureSpawnEvent e) {
        EntityType t = e.getEntityType();
        if (t == EntityType.ENDERMAN || t == EntityType.ARMOR_STAND || t == EntityType.WITHER) {
            return;
        }
        e.setCancelled(true);
    }
}
