package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.menu.ChatMenu;
import pl.spigotplugin.objects.user.Backup;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;

import java.sql.SQLException;

public class InventoryListener implements Listener {
    @EventHandler
    public void onClickBackup(InventoryClickEvent e) {
        e.getWhoClicked().sendMessage(String.valueOf(e.getSlot()));
        if (e.getInventory().getName().contains(ChatUtil.color("&7&lBackup'y gracza"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            Inventory inventory = e.getInventory();
            ItemStack is = e.getCurrentItem();
            if (inventory != null) {
                if (is == null || !is.hasItemMeta() || is.getItemMeta().getDisplayName() == null) {
                    return;
                }
                Player p = (Player) e.getWhoClicked();
                String name = is.getItemMeta().getLore().get(0).substring(11);
                long time = Long.parseLong(is.getItemMeta().getDisplayName().substring(4));
                Player o = Bukkit.getPlayer(name);
                if (o == null) {
                    p.sendMessage("&cGracz offline!");
                    return;
                }
                try {
                    Backup.restore(o, time, click(e), p);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
        User u = UserManager.getUser(p);
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lEventy"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lZarzadzanie chatem"))) {
            e.setCancelled(true);
            if (item !=null) {
                ItemMeta meta = item.getItemMeta();
                if (meta !=null) {
                    if (meta.getDisplayName() != null && meta.getDisplayName().equals(ChatUtil.color("&7&lAutomatyczne wiadomosci"))) {
                        u.setAutoMessages(!u.isAutoMessages());
                        ChatMenu.show(p);
                    }
                }
            }
        }
    }
    private int click(InventoryClickEvent e) {
        if (e.getClick() == ClickType.LEFT) {
            return 0;
        }
        if (e.getClick() == ClickType.RIGHT) {
            return 1;
        }
        if (e.getClick() == ClickType.SHIFT_LEFT) {
            return 2;
        }
        return 0;
    }
}
