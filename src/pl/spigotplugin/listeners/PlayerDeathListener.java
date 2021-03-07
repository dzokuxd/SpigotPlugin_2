package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.managers.CageManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.Backup;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onDeaths(PlayerDeathEvent e) {
        Player p = e.getEntity();
        User u = UserManager.getUser(p);
        Player k = p.getKiller();
        if(k==null)
            return;
        /*Guild g = GuildManager.getGuild(p);
        Guild g2 = GuildManager.getGuild(k);*/
        e.setDeathMessage(null);
        new BukkitRunnable() {
            public void run() {
                if (p.isInsideVehicle()) {
                    p.leaveVehicle();
                }
                p.spigot().respawn();
                ChatUtil.giveItems(p, new ItemStack(Material.STONE_PICKAXE));
                ChatUtil.giveItems(p, new ItemStack(Material.ENDER_CHEST));
                ChatUtil.giveItems(p, new ItemStack(Material.COOKED_BEEF, 64));
                ChatUtil.giveItems(p, new ItemStack(Material.WOOD, 48));
                p.updateInventory();
            }
        }.runTaskLater(SpigotPlugin.getPlugin(),5L);
        new Backup(p, desc(p));
        if (u != null) {
            u.save();
        }
        if (e.getEntity().getKiller() != null) {
            Player killer = p.getKiller();
            if (CageManager.getList().contains(p)) {
                CageManager.getList().remove(p);
                Bukkit.broadcastMessage("&6" + CageManager.getList().get(0).getName() + " wygral klatke!");
                Bukkit.getScheduler().runTaskAsynchronously(SpigotPlugin.getPlugin(), () -> {
                    CageManager.getList().remove(killer);
                    killer.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                    CageManager.setup(killer);
                    killer.getInventory().clear();
                    killer.getInventory().setArmorContents(null);
                });
            }
        }
    }
    private String desc(Player p) {
        EntityDamageEvent e = p.getLastDamageCause();
        String cause;
        if (e == null) {
            cause = "logouth";
        } else if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (p.getKiller() != null) {
                cause = p.getKiller().getName();
            } else {
                cause = "mob";
            }
        } else {
            cause = p.getLastDamageCause().getCause().name().toLowerCase();
        }
        return cause;
    }
    /*if (g != null && g2 != null && !g.equals(g2) && Config.EVENTS_KILL > System.currentTimeMillis()) {
        if (g.isOwner(p)) {
            if (RandomUtil.getChance(40.0)) {
                ItemStack d = new ItemBuilder(Material.CHEST, 1).setTitle(ChatUtil.color("&c&lSkrzynia " + Config.IP)).build();
                Bukkit.broadcastMessage("&6Gracz &c" + k.getName() + " &6wydropil &cSkrzynie " + Config.IP);
                Bukkit.broadcastMessage("&6Do konca eventu pozostalo &c" + DataUtil.secondsToString(Config.EVENTS_KILL) + " &c/event");
                ChatUtil.sendMessage("&6Trafiles na: &cSkrzynke &7(1szt)");
                ChatUtil.giveItems(k, d);
            }
        } else if (g.isLeader(p)) {
            if (RandomUtil.getChance(30.0)) {
                ItemStack d = new ItemBuilder(Material.CHEST, 1).setTitle(ChatUtil.color("&c&lSkrzynia " + Config.IP)).build();
                Bukkit.broadcastMessage("&6Gracz &c" + k.getName() + " &6wydropil &cSkrzynie " + Config.IP);
                Bukkit.broadcastMessage("&6Do konca eventu pozostalo &c" + DataUtil.secondsToString(Config.EVENTS_KILL) + " &c/event");
                e.sendMessage("&6Trafiles na: &cSkrzynke &7(1szt)");
                ChatUtil.giveItems(k, d);
            }
        } else if (RandomUtil.getChance(15.0)) {
            ItemStack d = new ItemBuilder(Material.CHEST, 1).setTitle(ChatUtil.color("&c&lSkrzynia " + Config.IP)).build();
            Bukkit.broadcastMessage("&6Gracz &c" + k.getName() + " &6wydropil &cSkrzynie " + Config.IP);
            Bukkit.broadcastMessage("&6Do konca eventu pozostalo &c" + DataUtil.secondsToString(Config.EVENTS_KILL) + " &c/event");
            e.sendMessage("&6Trafiles na: &cSkrzynke &7(1szt)");
            ChatUtil.giveItems(k, d);
        }
    }
    if (g != null && g2 != null && !g.equals(g2) && Config.EVENTS_BEACON > System.currentTimeMillis() && RandomUtil.getChance(40.0)) {
        ItemStack d = new ItemBuilder(Material.BEACON, 1).build();
        Bukkit.broadcastMessage("&6Gracz &c" + k.getName() + " &6wydropil &6&lBeacona!"));
        Bukkit.broadcastMessage("&6Do konca eventu pozostalo &c" + DataUtil.secondsToString(Config.EVENTS_BEACON) + " &c/event"));
        e.sendMessage("&6Trafiles na: &eBeacona &7(1szt)");
        ChatUtil.giveItems(k, d);
        }
    }*/
}//TODO jak dodasz gildie xD
