package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.BanManager;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.Ban;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.*;

public class PlayerQuitJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if (u == null) {
            u = UserManager.createrUser(p);
            ChatUtil.sendTitleMessage(p, "&c" + Config.IP, "&7Witaj, &c" + p.getName() + "!", 30, 70, 40);
            ItemUtil.giveItems(p, new ItemStack(Material.STONE_PICKAXE));
            ItemUtil.giveItems(p, new ItemStack(Material.ENDER_CHEST));
            ItemUtil.giveItems(p, new ItemStack(Material.COOKED_BEEF, 64));
            ItemUtil.giveItems(p, new ItemStack(Material.SUGAR_CANE, 1));
            ItemUtil.giveItems(p, new ItemStack(Material.WOOD, 48));
            int x = RandomUtil.getRandInt(-Config.BORDER_WORLD -20, Config.BORDER_WORLD -20);
            int z = RandomUtil.getRandInt(-Config.BORDER_WORLD -20, Config.BORDER_WORLD -20);
            double y = p.getWorld().getHighestBlockYAt(x, z) + 1.5f;
            Location location = new Location(p.getWorld(), x, y, z);
            p.teleport(location);
        }
        CombatManager.getCombats().putIfAbsent(p, new CombatUtil(p));
        TagUtil.createBoard(p);
        TagUtil.updateBoard(p);
        if (p.isDead()) {
            new BukkitRunnable() {
                public void run() {
                    p.spigot().respawn();
                    ItemUtil.giveItems(p, new ItemStack(Material.STONE_PICKAXE));
                    ItemUtil.giveItems(p, new ItemStack(Material.ENDER_CHEST));
                    ItemUtil.giveItems(p, new ItemStack(Material.COOKED_BEEF, 64));
                    ItemUtil.giveItems(p, new ItemStack(Material.WOOD, 48));
                }
            }.runTaskLater(SpigotPlugin.getPlugin(), 1L);
        }
    }
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        Ban ban = BanManager.getBan(p);
        if (ban != null) {
            if (ban.getTime() != 0L && ban.getTime() <= System.currentTimeMillis()) {
                BanManager.unban(ban);
                return;
            }
            String reason = "&cTwoje konto zostalo zbanowane!\n" +
                    "" +
                    "\n&cZbanowal: " +ban.getAdmin() +
                    "\n&cPowod: "+ban.getReason() +
                    "\n&cWygasa: "+ ((ban.getTime() == 0L) ? "&cNigdy!" : "&cza " + DataUtil.secondsToString(ban.getTime())) +
                    "\n" +
                    "\nMozesz kupic unbana" +
                    "\n"+ Config.IP+"/sklep";
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatUtil.color(reason));
        }
    }
    @EventHandler
    public void onKick(PlayerKickEvent e) {
        quitGame(e.getPlayer());
        e.setLeaveMessage(null);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        quitGame(player);
        e.setQuitMessage(null);
        if (CheckUtil.checkedPlayers.contains(player)) {
            CheckUtil.checkedPlayers.remove(player);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + player.getName() + " logout podzcas sprasdzadnia");
            TagUtil.removeBoard(e.getPlayer());
        }
    }
    private void quitGame(Player p) {
        User user = UserManager.getUser(p);
        if (user == null) return;
        CombatUtil combat = CombatManager.getCombat(p);
        if (combat != null && combat.hasFight()) {
            p.setHealth(0.0);
            p.sendMessage("zdechles chuju");
        }
        user.save();
    }
}
