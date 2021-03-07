package pl.spigotplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.utils.CheckUtil;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void move(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (CheckUtil.checkedPlayers.contains(p)) {
            e.setTo(e.getFrom());
            p.sendMessage("&c&lJestes aktualnie sprawdzany! Nie wylogowywuj sie z gry! Wejdz na TS3: &4&lts." + (Config.IP));
        }
    }
}
