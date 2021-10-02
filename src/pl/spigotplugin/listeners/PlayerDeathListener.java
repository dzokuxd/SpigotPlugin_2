package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.CageManager;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Fight;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.Backup;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemUtil;
import pl.spigotplugin.utils.RandomUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onDeaths(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        Player p = e.getEntity();
        User u = UserManager.getUser(p);
        new BukkitRunnable() {
            public void run() {
                if (p.isInsideVehicle()) {
                    p.leaveVehicle();
                }
                p.spigot().respawn();
                ItemUtil.giveItems(p, new ItemStack(Material.STONE_PICKAXE));
                ItemUtil.giveItems(p, new ItemStack(Material.ENDER_CHEST));
                ItemUtil.giveItems(p, new ItemStack(Material.COOKED_BEEF, 64));
                ItemUtil.giveItems(p, new ItemStack(Material.WOOD, 48));
                p.updateInventory();
                CombatManager.clear(p);
            }
        }.runTaskLater(SpigotPlugin.getPlugin(),5L);
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
        Fight fight = CombatManager.get(p);
        Player k = p.getKiller();
        if (k == null && CombatManager.isFighting(p)) {
            k = fight.getAttacked();
        }
        if (k != null) {
            if (p == k) {
                return;
            }
            String ip = p.getAddress().getHostString();
            String ipp = k.getAddress().getHostString();
            User uu = UserManager.getUser(k);
            if (ip.equals(ipp)) {
                uu.setPoints(uu.getPoints() - 50);
                ChatUtil.sendTitleMessage(k, "&6Multikonto!", "&7Otrzymales kare w postaci -50 pkt", 30, 70, 40);
                CombatManager.clear(p);
                return;
            }
            Map<User, Long> lastKillers = u.getLastKillers();
            if (lastKillers.containsKey(uu) && lastKillers.get(uu) > System.currentTimeMillis()) {
                CombatManager.clear(p);
                return;
            }

            int plusRank = (int) (155.0 + (uu.getPoints() - u.getPoints()) * -0.15);
            if (plusRank <= 0) {
                plusRank = RandomUtil.getRandInteger(7, 30);
            }

            Guild g = GuildManager.getGuild(p.getPlayer());
            Guild gg = GuildManager.getGuild(p.getKiller());
            int loseRank = plusRank / 7 * 3;

            u.setPoints(u.getPoints() - loseRank);
            u.setDeaths(u.getDeaths() + 1);
            u.setKs(0);
            ChatUtil.sendTitleMessage(p, "&6Smierc!", "&c" + (gg == null ? "" : gg.getTag()) + " " + k.getName() + " &7-" + loseRank, 30, 70, 40);
            u.putForSave();

            uu.setPoints(uu.getPoints() + plusRank);
            uu.setKills(uu.getKills() + 1);
            uu.setKs(uu.getKs() + 1);
            uu.setCoins(uu.getCoins() + 200);
            ChatUtil.sendTitleMessage(k, "&6Zabojstwo!", "&c" + (g == null ? "" : g.getTag()) + " " + p.getName() + " &7+" + plusRank, 30, 70, 40);
            k.playSound(p.getLocation(), Sound.LEVEL_UP, 5.0f, 3.0f);
            k.getWorld().strikeLightningEffect(k.getLocation());

            if (uu.getMaxks() < uu.getKs()) {
                uu.setMaxks(uu.getKs());
            }

            if (uu.getKs() >= 5 && uu.getKs() % 5 == 0) {
                Bukkit.broadcastMessage("&eGracz osiagnal " + uu.getKs() + " killstreak!");
            }
            u.getLastKillers().put(uu, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15L));
            uu.putForSave();
            Bukkit.broadcastMessage("&6Gracz " + (g == null ? "" : "&7[&c" + g.getTag() + "&7] ") + "&c " + p.getName() + " &7(&c-" + loseRank + "&7) &6zostal zabity przez " + (gg == null ? "" : "&7[&c" + gg.getTag() + "&7] ") + "&c" + k.getName() + " &7(&c+" + plusRank + "&7)");

            if (gg != null) {
                gg.setPoints(gg.getPoints() + plusRank / 2);
                gg.setKills(gg.getKills() + 1);
                gg.putForSave();
            }
            if (gg != null) {
                gg.setPoints(gg.getPoints() - loseRank / 2);
                gg.setDeaths(gg.getDeaths() + 1);
                gg.putForSave();
            }
        }
        new Backup(p, desc(p));
        u.putForSave();
        u.save();
        if (Config.MANAGE_DROPHEAD)
            p.getLocation().getWorld().dropItemNaturally(p.getLocation(), ItemUtil.getGoldenHead());
    }
    private String desc(Player p) {
        EntityDamageEvent e = p.getLastDamageCause();
        String cause;
        if (e == null) {
            cause = "logout";
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
