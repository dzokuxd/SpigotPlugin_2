package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.BossBarApi;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.Settings;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.CheckUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.LocationUtil;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void move(PlayerMoveEvent e) {
        if (!LocationUtil.shouldContinueEvent(e)) {
            return;
        }
        Player p = e.getPlayer();
        if (CheckUtil.checkedPlayers.contains(p)) {
            e.setTo(e.getFrom());
            p.sendMessage("&c&lJestes aktualnie sprawdzany! Nie wylogowywuj sie z gry! Wejdz na TS3: &4&lts." + (statues.IP));
            return;
        }
        User u = UserManager.getUser(p);
        if (GuildManager.getGuild(p.getLocation()) == null) {
            if (u.isOnCuboid()) {
                u.setOnCuboid(false);
                p.sendMessage("&cOpusciles teren gildii!");
                BossBarApi.removeBar(e.getPlayer());
            }
        } else {
            Guild guild = GuildManager.getGuild(p.getLocation());

            if (guild == null) {
                return;
            }

            if (u.isOnCuboid()) {
                return;
            }

            String text = ChatColor.RED + "Znajdujesz sie na terenie wrogiej gildii!";

            if (!u.getGuild().isEmpty()) {
                if (u.getGuild().equals(guild.getTag())) {
                    text = ChatColor.GREEN + "Znajdujesz sie na terenie swojej gildii!";
                } else {
                    Guild guild1 = GuildManager.getGuild(u.getGuild());

                    if (guild1 != null && guild1.getAlly().contains(guild.getTag())) {
                        text = ChatColor.BLUE + "Znajdujesz sie na terenie sojuszniczej gildii!";
                    }
                }

            }

            BossBarApi.setBar(e.getPlayer(), text, 100);
            p.sendMessage(text);
            if (guild.isProtected()){
                p.sendMessage("&fTa gildia posiada ochrone jeszcze przez &d" + DataUtil.secondsToString(guild.getprottime()));
            }
            u.setOnCuboid(true);
            if (p.hasPermission("spigot.admin")) {
                return;
            }
            if (guild.getMembers().contains(p.getName())) {
                return;
            }
            for (Player onlineMember : guild.getOnlineMembers()) {
                if (onlineMember == null) {
                    continue;
                }
                onlineMember.sendMessage("&4Wrog wkroczyl na teren twojej gildii "+p.getName());
            }
        }
        Location to = e.getTo();
        Location from = e.getFrom();
        if(p.isBlocking())
            if ((to.getBlockX() != from.getBlockX() || to.getBlockY() != from.getBlockY() || to.getBlockZ() != from.getBlockZ())) {
                final ItemStack s = p.getItemInHand();
                p.setItemInHand(s);
            }
    }
    @EventHandler
    public static void isCheck(Player player, Location from, Location to){
        Guild g = GuildManager.getGuild(player);
        if (g == null) {
            return;
        }
        if(to.getBlock().isEmpty()||to.clone().add(0.0, 1.0, 0.0).getBlock().isEmpty()){
            return;
        }
        if(!Settings.isContains(to.getBlock().getType())&&
                !Settings.isContains(to.clone().add(0.0, 1.0, 0.0).getBlock().getType())){
            return;
        }
        Location teleport=new Location(Bukkit.getWorld("world"), from.getBlockX(), from.getBlockY(), from.getBlockZ());
        teleport.setYaw(player.getLocation().getYaw());
        teleport.setPitch(player.getLocation().getPitch());
        teleport.subtract(-0.5, 0.0, -0.5);
        player.teleport(teleport);
        for (Player admins : Bukkit.getOnlinePlayers()) {
            if (admins.hasPermission("spigot.admin")) {
                admins.sendMessage("shadowblock" + player);
            }
        }
    }
}
