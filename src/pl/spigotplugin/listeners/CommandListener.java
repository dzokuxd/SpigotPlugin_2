package pl.spigotplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.spigotplugin.utils.CheckUtil;

public class CommandListener implements Listener {
    @EventHandler
    public void cmd(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        if (CheckUtil.checkedPlayers.contains(p)) {
            String blocked = "ec;tpa;spawn;";
            for (String s : blocked.split(";")) {
                if (event.getMessage().startsWith(s.toLowerCase())) {
                    event.setCancelled(true);
                    p.sendMessage("&cPodczas sprawdzania nie mozesz uzywac komend!");
                }
            }
        }
    }
}