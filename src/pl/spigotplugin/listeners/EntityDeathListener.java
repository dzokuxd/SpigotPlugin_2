package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.utils.ItemUtil;

public class EntityDeathListener implements Listener {
    @EventHandler
    public void handle(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        if (entity.getKiller() == null) {
            return;
        }
        Player killer = entity.getKiller();
        if (killer.equals(entity)) {
            return;
        }
        e.setDroppedExp(0);
        e.getDrops().clear();
        if (entity.getType() == EntityType.ENDER_DRAGON) {
            ItemUtil.giveItems(killer, new ItemStack(Material.DRAGON_EGG, 1));
            killer.giveExp(e.getDroppedExp());
            Bukkit.broadcastMessage(core.DRAGON_BROADCAST.replace("{PLAYER}",killer.getName()));
        }
        if (entity.getType() == EntityType.WITHER) {
            ItemUtil.giveItems(killer, new ItemStack(Material.NETHER_STAR, 1));
        }
    }
}
