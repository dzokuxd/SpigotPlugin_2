package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import pl.spigotplugin.managers.*;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.Ban;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.*;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlayerQuitJoinListener implements Listener {
    private final List<String> joinmsg = Arrays.asList(
            "&7\u00bb --------------------------",
            "&7\u00bb &6Witaj &c%nick% &6na serwerze &cMediumHC",
            "&7\u00bb &6Lista komend: &c/pomoc",
            "&7\u00bb &6Twoja ranga: &c%grupa%",
            "&7\u00bb &6Twoja gildia: &c%gildia%",
            "&7\u00bb &6Twoj ranking: &c%rankinggracza%",
            "&7\u00bb &6Twoja pozycja: &c%pozycia%",
            "&7\u00bb &6TeamSpeak: &cts.easyage.pl",
            "&7\u00bb &6Discord: &cdiscord.gg/Pemxu7eK95",
            "&7\u00bb &6Strona: &cwww.easyage.pl",
            "&cZyczymy milej gry :)",
            "&7\u00bb --------------------------");

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);
        if (u == null) {
            u = UserManager.createrUser(p);
            ItemUtil.giveItems(p, new ItemStack(Material.STONE_PICKAXE));
            ItemUtil.giveItems(p, new ItemStack(Material.ENDER_CHEST));
            ItemUtil.giveItems(p, new ItemStack(Material.COOKED_BEEF, 64));
            ItemUtil.giveItems(p, new ItemStack(Material.WOOD, 48));
            int x = RandomUtil.getRandInt(-Config.BORDER_WORLD -20, Config.BORDER_WORLD -20);
            int z = RandomUtil.getRandInt(-Config.BORDER_WORLD -20, Config.BORDER_WORLD -20);
            double y = p.getWorld().getHighestBlockYAt(x, z) + 1.5f;
            Location location = new Location(p.getWorld(), x, y, z);
            p.teleport(location);
        }
        TagUtil.createBoard(p.getPlayer());
        TagUtil.updateBoard(p.getPlayer());
        TabUtil.update(p);
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
        Guild g = GuildManager.getGuild(p);
        PermissionUser uu = PermissionsEx.getUser(p);
        for (String string : joinmsg) {
            string = string.replace("%nick%", p.getName());
            string = string.replace("%gildia%", (!u.getGuild().isEmpty() ? u.getGuild() : "brak"));
            string = string.replace("%pozycia%", String.valueOf(TopsManager.getPlaceUser(u)));
            string = string.replace("%rankinggracza%", String.valueOf(u.getPoints()));
            string = string.replace("%grupa%", Arrays.toString(uu.getGroupsNames()).replace("]", "").replace("[", ""));
            p.sendMessage(string);
        }
        if (g != null) {
            p.sendMessage("&6Twoja gildia wygasa za: &c" + DataUtil.secondsToString(g.getProlong()));
            g.message("&6Czlonek twojej gildii: &a" + p.getName() + " &6dolaczyl na serwer. &7(&c" + g.getOnlineMembers().size() + "&7/&c" + g.getMembers().size() + "&7)");
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
        CombatManager.clear(e.getPlayer());
        TagUtil.removeBoard(e.getPlayer());
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        quitGame(e.getPlayer());
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        TagUtil.removeBoard(p.getPlayer());
        if (CheckUtil.checkedPlayers.contains(p)) {
            CheckUtil.checkedPlayers.remove(p);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " logout podzcas sprawdzadnia");
        }
        if (CombatManager.isFighting(p)) {
            User user = UserManager.getUser(p);
            Guild g = GuildManager.getGuild(p);
            p.setHealth(0.0);
            p.damage(1.0);
            user.setDeaths(user.getDeaths() +1);
            user.setPoints(user.getPoints() -50);
            user.save();
            Bukkit.broadcastMessage("&6Gracz &c" + (g == null ? "" : "&7[&c" + g.getTag() + "&7] &c") + p.getName() + "&c-50 &6wylogowal sie podczas walki!");
            CombatManager.getFightMap().remove(p);
        }
    }
    private void quitGame(Player p) {
        TagUtil.removeBoard(p.getPlayer());
        if (CombatManager.isFighting(p)) {
            Guild g = GuildManager.getGuild(p);
            User user = UserManager.getUser(p);
            p.setHealth(0.0);
            p.damage(1.0);
            user.setDeaths(user.getDeaths() +1);
            user.setPoints(user.getPoints() -50);
            user.save();
            Bukkit.broadcastMessage("&6Gracz &c" + (g == null ? "" : "&7[&c" + g.getTag() + "&7] &c") + p.getName() + "&c-50 &6wylogowal sie podczas walki!");
            CombatManager.getFightMap().remove(p);
        }
    }
}
