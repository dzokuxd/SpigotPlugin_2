package pl.spigotplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Guild;

public class PlayerBucketFillListener implements Listener {

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("spigot.bypass")) {
            return;
        }
        Guild g = GuildManager.getGuild(p.getLocation());
        if (g == null) {
            return;
        }
        if (g.isMember(p.getName())) {
            return;
        }
        if (g.isMember(p.getName()) && g.getRegion().isInCentrum(event.getBlockClicked().getLocation(), 3, 2, 3)) {
            return;
        }
        p.sendMessage("&cTa interakcja na terenie gidlii jest zablokowana!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("spigot.bypass")) {
            return;
        }
        Guild g = GuildManager.getGuild(p.getLocation());
        if (g == null) {
            return;
        }
        if (g.isMember(p.getName())) {
            return;
        }
        if (g.isMember(p.getName()) && g.getRegion().isInCentrum(event.getBlockClicked().getLocation(), 3, 2, 3)) {
            return;
        }
        p.sendMessage("&cTa interakcja na terenie gildii jest zablokowana!");
        event.setCancelled(true);
    }

}
