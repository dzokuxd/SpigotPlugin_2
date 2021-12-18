package pl.spigotplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.managers.DataManager;

public class ToggleListener implements Listener
{
    @EventHandler
    public void onToggle(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            if (DataManager.getShiftArmor().containsKey(player.getName())) {
                ItemStack[] armor = DataManager.getShiftArmor().get(player.getName());
                DataManager.getShiftArmor().remove(player.getName());
                player.getInventory().setArmorContents(armor);
                player.updateInventory();
            }
        }
        else if (DataManager.getDisco().containsKey(player.getName())) {
            DataManager.getShiftArmor().put(player.getName(), player.getInventory().getArmorContents());
            player.updateInventory();
        }
    }
}
