package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.managers.DataManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Fight;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.utils.*;

import java.util.concurrent.TimeUnit;

public class EnityDamageListener implements Listener {
    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player)) {
            return;
        }
       if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            if (CheckUtil.checkedPlayers.contains(player) || CheckUtil.checkedPlayers.contains(damager)) {
                e.setCancelled(true);
                return;
            }
        }

        if (e.getDamager() instanceof Player && e.getEntity().getWorld().getName().equals("world")) {
            if (CuboidUtil.isSpawn(e.getEntity().getLocation())) {
                if (e.getEntity().getLocation().getBlockY() <= (statues.REGION_BYPASSY)) {
                    return;
                }
                e.setCancelled(true);
                return;
            }
            else if (CuboidUtil.isSpawn(e.getDamager().getLocation()) && CuboidUtil.isOutsideSpawn(e.getEntity().getLocation())) {
                if (e.getEntity().getLocation().getBlockY() <= (statues.REGION_BYPASSY)) {
                    return;
                }
                e.setCancelled(true);
                return;
            }
        }
        Player p = (Player) e.getEntity();
        Player playerDamager = EntityUtil.getDamager(e);
        if (playerDamager != null) {
            if (p.getLocation().getBlockY() > statues.REGION_BYPASSY) {
                if (CuboidUtil.isOutsideSpawn(p.getLocation())) {
                    e.setCancelled(true);
                    return;
                }
                if (this.is(p, playerDamager,e)) {
                    return;
                }
            }
            if (p != playerDamager) {
                Fight playerFight2 = CombatManager.get(p);
                Fight damagerFight2 = CombatManager.get(playerDamager);

                if (playerFight2.getFightTime() < System.currentTimeMillis()) {
                    p.sendMessage("&4Zostales zaatakowany nie mozesz wylogowac sie przez 30 sekund!");
                }
                if (damagerFight2.getFightTime() < System.currentTimeMillis()) {
                    playerDamager.sendMessage("&4Zostales zaatakowany nie mozesz wylogowac sie przez 30 sekund!");
                }

                long fightTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30);

                playerFight2.setFightTime(fightTime);
                playerFight2.setAttacked(playerDamager);
                damagerFight2.setFightTime(fightTime);
                damagerFight2.setAttacked(p);
            }
        }
    }
    private boolean is(Player p, Player d, EntityDamageByEntityEvent e) {
        Guild g = GuildManager.getGuild(p);
        Guild o = GuildManager.getGuild(d);
        if (g == null || o == null) {
            return false;
        }
        if (g.equals(o)) {
            if (g.isPvp()) {
                e.setDamage(0.0);
            }
            else {
                e.setCancelled(true);
                d.sendMessage("&cWalka w gildii wylaczona!");
            }
            return true;
        }
        if (g.getAlly().contains(o.getTag())) {
            if (!g.isPvpAlly() || !o.isPvpAlly()) {
                e.setCancelled(true);
                d.sendMessage("&cWalka w sojuszu wylaczona!");
            }
            else {
                e.setDamage(0.0);
            }
            return true;
        }
        return false;
    }
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
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        Player player = null;
        if (!(entity instanceof Player)) {
            return;
        }
        player = (Player)entity;
        if (player.isSneaking() && DataManager.getShiftArmor().containsKey(player.getName())) {
            ItemStack[] armor = DataManager.getShiftArmor().get(player.getName());
            DataManager.getShiftArmor().remove(player.getName());
            player.getInventory().setArmorContents(armor);
            player.updateInventory();
        }
    }
}
