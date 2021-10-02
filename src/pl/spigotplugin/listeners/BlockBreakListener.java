package pl.spigotplugin.listeners;

import io.netty.util.internal.ConcurrentSet;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.managers.DropManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.configs.Settings;
import pl.spigotplugin.utils.*;

import java.util.Set;

public class BlockBreakListener implements Listener {
    public static Set<Player> playerSet = new ConcurrentSet<>();

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        User u = UserManager.getUser(p);
        Guild g = GuildManager.getGuild(b.getLocation());
        if (p.getGameMode().equals(GameMode.SURVIVAL) && p.getWorld().getName().equals("gtp")) {
            e.setCancelled(true);
            p.sendMessage("&cNie mozesz niszczyc na tym swiecie!");
            return;
        }
        if (CuboidUtil.isOutsideSpawn(b.getLocation()) && !p.hasPermission("regionplugin.bypass")) {
            e.setCancelled(true);
            p.sendMessage("&cTa interakcja jest zablokowana!");
            return;
        }
        if (CheckUtil.checkedPlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
            return;
        }
        Guild guild = GuildManager.getGuild(b.getLocation());
        if (guild != null) {
            if (p.hasPermission("regionplugin.bypass"))
                return;
            User user = UserManager.getUser(p);
            if (user == null) {
                e.setCancelled(true);
                return;
            }
            if (guild.isMember(p.getName()) && guild.getRegion().isInCentrum(e.getBlock().getLocation(), 3, 2, 3)) {
                e.setCancelled(true);
                p.sendMessage("&cNie mozesz niszczyc w centrum gildii!");
                return;
            }
            if (!user.getGuild().equals(guild.getTag())) {
                e.setCancelled(true);
                p.sendMessage("&cNie mozesz niszczyc na terenie wrogiej gildii!");
                p.setGameMode(GameMode.ADVENTURE);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (!p.isOnline()) {
                            cancel();
                            return;
                        }
                        p.setGameMode(GameMode.SURVIVAL);
                    }
                }.runTaskLater(SpigotPlugin.getPlugin(), 20 * 5);
                return;
            }
        }
        if (b.getType() == Material.STONE) {
            final Block bb = b.getLocation().subtract(0, 1, 0).getBlock();
            if (bb.getType() == Material.ENDER_STONE) {
                new BukkitRunnable() {
                    public void run() {
                        if (g != null) {
                            if (g.getLastExplodeTime() > System.currentTimeMillis()) {
                                return;
                            }
                            if (CombatManager.isFighting(p)) {
                                return;
                            }
                        }
                        b.setType(Material.STONE);
                    }
                }.runTaskLater(SpigotPlugin.getPlugin(), 25L);
                return;
            }
        }
        if (playerSet.contains(p)) {
            if (e.getBlock().getType() == Material.STONE || e.getBlock().getType() == Material.COBBLESTONE) {
                int amount = ItemUtil.getamount(Material.COBBLESTONE, p, (short) 0);
                if (amount > 64 * 9) {
                    p.getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 64 * 9));
                    p.getInventory().addItem(Settings.cobblexItem);
                    p.sendMessage("&aPosiadasz za duzo cobbla w eq, zamienilem go na CobbleX");
                    return;
                }
            }
        }
        if (!p.getGameMode().equals(GameMode.SURVIVAL)) {
            return;
        }
        if (Config.EVENTS_CASE > System.currentTimeMillis() && RandomUtil.getChance(1.10)) {
            ItemStack d = new ItemBuilder(Material.CHEST, 1).setTitle(ChatUtil.color("&c&lSkrzynia " + Config.IP)).build();
            Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6wydropil &cSkrzynie " + Config.IP);
            Bukkit.broadcastMessage("&6Do konca eventu pozostalo &c" + DataUtil.secondsToString(Config.EVENTS_CASE) + " &c/event");
            p.sendMessage("&6Trafiles na: &cSkrzynie &7(1szt) &c+20");
            u.setExp(u.getExp() + 20);
            ItemUtil.giveItems(p, d);
            return;
        }
        int exp = DropManager.getExp(b.getType(), p);
        p.giveExp(exp);
        DropManager.getDropData(b.getType()).breakBlock(b, p, p.getItemInHand());
        e.setCancelled(true);
    }
}
