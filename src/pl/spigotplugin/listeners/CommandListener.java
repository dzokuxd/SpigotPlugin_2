package pl.spigotplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.CheckUtil;
import pl.spigotplugin.utils.GroupUtil;

public class CommandListener implements Listener {
    @EventHandler
    public void cmd(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        Guild g = GuildManager.getGuild(p.getLocation());
        String pcmd = e.getMessage();
        if (CheckUtil.checkedPlayers.contains(p)) {
            String blocked = "ec;tpa;spawn;";
            for (String s : blocked.split(";")) {
                if (e.getMessage().startsWith(s.toLowerCase())) {
                    e.setCancelled(true);
                    p.sendMessage(ChatUtil.color("&cPodczas sprawdzania nie mozesz uzywac komend!"));
                }
            }
        }
        if (!GroupUtil.have(p, RankType.ADMIN) && CombatManager.isFighting(p)) {
            for (String cmd : guild.BLOCKED_INCOMBAT) {
                if (pcmd.toLowerCase().contains("/" + cmd)) {
                    e.setCancelled(true);
                    p.sendMessage(ChatUtil.color("&cTa komenda jest zablokowana podczas walki!"));
                    return;
                }
            }
        }
        if (g != null && !g.isMember(p.getName()) && !GroupUtil.have(p, RankType.ADMIN)) {
            for (String cmd1 : guild.BLOCKED_INGUILD) {
                if (pcmd.toLowerCase().contains("/" + cmd1)) {
                    e.setCancelled(true);
                    p.sendMessage(ChatUtil.color("&cTa komenda jest zablokowana na terenie gildii!"));
                }
            }
        }
    }
}