package pl.spigotplugin.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.settings.Settings;
import pl.spigotplugin.utils.CheckUtil;
import pl.spigotplugin.utils.ItemUtil;

import java.util.List;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace3(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (e.getBlockPlaced().getType() == Material.BREWING_STAND) {
            p.sendMessage("&cAlchemia zostala zablokowana!");
            e.setCancelled(true);
        }
        if (CheckUtil.checkedPlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
        if (p.getItemInHand().isSimilar(Settings.cobblexItem)) {
            e.setCancelled(true);
            e.getBlockPlaced().setType(Material.AIR);
            List<ItemStack> dropList = Settings.normalDropList;
            if (p.hasPermission("cobblex.premiumDrop")) {
                dropList = Settings.premiumDropList;
            }
            ItemUtil.giveDrop(e.getBlockPlaced().getLocation(), dropList);
            p.getInventory().removeItem(Settings.cobblexItem);
            UserManager.getUser(p).save();
        }
    }
}
