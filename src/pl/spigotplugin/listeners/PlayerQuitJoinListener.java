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
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.holder.LocationHolder;
import pl.spigotplugin.managers.*;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.Ban;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.*;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Arrays;
import java.util.List;

public class PlayerQuitJoinListener implements Listener {
    private final List<String> joinmsg = Arrays.asList(
            "&7\u00bb --------------------------",
            "&7\u00bb &fWitaj &d%nick% &fna serwerze &dMediumHC",
            "&7\u00bb &fLista komend: &d/pomoc",
            "&7\u00bb &fTwoja ranga: &d%grupa%",
            "&7\u00bb &fTwoja gildia: &d%gildia%",
            "&7\u00bb &fTwoj ranking: &d%rankinggracza%",
            "&7\u00bb &fTwoja pozycja: &d%pozycia%",
            "&7\u00bb &fTeamSpeak: &dts.easyage.pl",
            "&7\u00bb &fDiscord: &ddiscord.gg/Pemxu7eK95",
            "&7\u00bb &fStrona: &dwww.easyage.pl",
            "&dZyczymy milej gry :)",
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
            int x = RandomUtil.getRandInt(-statues.BORDER_WORLD -20, statues.BORDER_WORLD -20);
            int z = RandomUtil.getRandInt(-statues.BORDER_WORLD -20, statues.BORDER_WORLD -20);
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
        if (p.getWorld().getName().equals("gtp")) {
            p.teleport(LocationHolder.SPAWN);
            return;
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
            p.sendMessage("&cTwoja gildia wygasa za: " + DataUtil.secondsToString(g.getProlong()));
            g.message("&aCzlonek twojej gildii: " + p.getName() + " dolaczyl na serwer. (" + g.getOnlineMembers().size() + "/" + g.getMembers().size() + ")");
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
                    "\n"+ statues.IP+"/sklep";
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
            Bukkit.broadcastMessage("&cGracz " + (g == null ? "" : "[" + g.getTag() + "] ") + p.getName() + "-50 &6wylogowal sie podczas walki!");
        }
    }
}
