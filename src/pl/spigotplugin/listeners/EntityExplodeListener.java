package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.utils.*;

import java.util.LinkedList;
import java.util.List;

public class EntityExplodeListener implements Listener {
    private static final List<Material> SKIPPABLE = new LinkedList<>();

    static {
        SKIPPABLE.add(Material.CHEST);
        SKIPPABLE.add(Material.TRAPPED_CHEST);
        SKIPPABLE.add(Material.TNT);
        SKIPPABLE.add(Material.BEACON);
        SKIPPABLE.add(Material.SAND);
        SKIPPABLE.add(Material.GRAVEL);
        SKIPPABLE.add(Material.FURNACE);
        SKIPPABLE.add(Material.HOPPER);
        SKIPPABLE.add(Material.HOPPER_MINECART);
        SKIPPABLE.add(Material.STORAGE_MINECART);
        SKIPPABLE.add(Material.BURNING_FURNACE);
    }

    private void spawnEntity(Location location) {
        Entity entity = location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        ArmorStand stand = (ArmorStand) entity;
        stand.setCustomName(ChatUtil.color("&cTnT od 60 poziomu"));
        stand.setCustomNameVisible(true);
        stand.setVisible(false);
        stand.setArms(false);
        new BukkitRunnable() {

            @Override
            public void run() {
                stand.setHealth(0.0);
            }
        }.runTaskLaterAsynchronously(SpigotPlugin.getPlugin(), 40L);
    }
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getLocation().getBlockY() >= 60) {
            e.setCancelled(true);
            spawnEntity(e.getLocation());
            return;
        }
        if (!TNTUtil.isBetween()) {
            e.setCancelled(true);
            return;
        }
        Guild x = GuildManager.getGuildByLoc(e.getLocation());
        if (x == null) {
            e.setCancelled(true);
            return;
        }
        Guild g = GuildManager.getGuild(e.getEntity().getLocation());
        if (g != null) {
            g.setLastExplodeTime(System.currentTimeMillis() + TimeUtil.MINUTE.getTime(0));
            g.message("&4Na terenie gildii wybuchlo tnt!");
            if (g.getCreateTime() + TimeUtil.HOUR.getTime(24) > System.currentTimeMillis()) {
                e.setCancelled(true);
                return;
            }
        }
        List<Location> sphere = SpaceUtil.sphere(e.getLocation(), 2, 1, false, true, 0);
        for (Location location : sphere) {
            if (location.getBlock().getType() == Material.OBSIDIAN) {
                if (RandomUtil.getChance(25)) {
                    String builder = location.getBlock().getX() + ":" + location.getBlock().getY() + ":" + location.getBlock().getZ() + ":OBSIDIAN:" + location.getBlock().getData() + "!";
                    if (g != null) {
                        g.setRegen(g.getRegen() + builder);
                        g.setNeedsaveregen(true);
                    }
                    location.getBlock().setType(Material.AIR);
                }
            }
            if (location.getBlock().getType() == Material.STATIONARY_WATER) {
                if (RandomUtil.getChance(10)) {
                    location.getBlock().setType(Material.AIR);
                }
            } else if (location.getBlock().getType() == Material.STATIONARY_LAVA) {
                if (RandomUtil.getChance(10)) {
                    location.getBlock().setType(Material.AIR);
                }
            } else if (location.getBlock().getType() == Material.WATER) {
                if (RandomUtil.getChance(50)) {
                    location.getBlock().setType(Material.AIR);
                }
            } else if (location.getBlock().getType() == Material.LAVA) {
                if (RandomUtil.getChance(50)) {
                    location.getBlock().setType(Material.AIR);
                }
            }
        }
        for (Block b : e.blockList()) {
            Guild gu = GuildManager.getGuild(b.getLocation());
            if (gu != null && !SKIPPABLE.contains(b.getType())) {
                String builder = b.getX() + ":" + b.getY() + ":" + b.getZ() + ":" + b.getType() + ":" + b.getData() + "!";
                gu.setRegen(gu.getRegen() + builder);
                g.setNeedsaveregen(true);
            }
        }
    }
}
