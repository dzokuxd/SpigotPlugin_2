package pl.spigotplugin.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.spigotplugin.utils.CheckUtil;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace3(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType() == Material.BREWING_STAND) {
            Player p = e.getPlayer();
            p.sendMessage("&cAlchemia zostala zablokowana!");
            e.setCancelled(true);
        }
        if (CheckUtil.checkedPlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
