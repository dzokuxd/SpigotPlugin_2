package pl.spigotplugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.spigotplugin.api.BossBarApi;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.CheckUtil;
import pl.spigotplugin.utils.LocationUtil;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void move(PlayerMoveEvent e) {
        if (!LocationUtil.shouldContinueEvent(e)) {
            return;
        }
        Player p = e.getPlayer();
        if (CheckUtil.checkedPlayers.contains(p)) {
            e.setTo(e.getFrom());
            p.sendMessage("&c&lJestes aktualnie sprawdzany! Nie wylogowywuj sie z gry! Wejdz na TS3: &4&lts." + (Config.IP));
            return;
        }
        User u = UserManager.getUser(p);
        if (GuildManager.getGuild(p.getLocation()) == null) {
            if (u.isOnCuboid()) {
                u.setOnCuboid(false);
                p.sendMessage("&aOpusciles teren wrogiej gildii");
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

            p.sendMessage("&cWkroczyles na teren wrogiej gildii "+ guild.getTag());
            BossBarApi.setBar(e.getPlayer(), ChatColor.RED + "Znajdujesz sie na terenie wrogiej gildii!", 100);
            u.setOnCuboid(true);

            if (p.hasPermission("spigotplugin.hide")) {
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
    }
}
