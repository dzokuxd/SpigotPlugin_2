package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.managers.TradeManager;
import pl.spigotplugin.objects.user.DataTrade;

public class TradeListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        abortTrade(player);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        abortTrade(player);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = Bukkit.getPlayerExact(event.getPlayer().getName());
        abortTrade(player);
    }

    @EventHandler
    public void onPlayerIteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && event.getRightClicked().getType().equals(EntityType.PLAYER)) {
            if (player.getLocation().getBlockX() > 100 || player.getLocation().getBlockX() < -100 || player.getLocation().getBlockZ() > 100 || player.getLocation().getBlockZ() < -100) {
                return;
            }
            Player trader = (Player) event.getRightClicked();
            if (TradeManager.offers.get(player) != null && TradeManager.offers.get(player).equals(trader)) {
                TradeManager.openTrade(player, trader);
                return;
            }
            TradeManager.sendTrade(player, trader);
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = Bukkit.getPlayerExact(event.getWhoClicked().getName());
        DataTrade trade = TradeManager.trades.get(player);
        if (trade != null) {
            if (event.getAction().equals(InventoryAction.COLLECT_TO_CURSOR)) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (event.getRawSlot() > 53 && !event.isShiftClick()) {
                return;
            }
            if (event.getRawSlot() < 9 || event.getRawSlot() % 9 > 3 || event.isShiftClick()) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
            }
            if (event.getRawSlot() == 2) {
                TradeManager.abortTrade(trade);
                return;
            }
            if (event.getRawSlot() == 3) {
                TradeManager.readyTrade(trade, player);
                return;
            }
            if (trade.playerReady1 || trade.playerReady2) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "" + ChatColor.BOLD + "x")) {
                event.setCancelled(true);
                return;
            }
            Bukkit.getScheduler().runTaskLater(SpigotPlugin.getPlugin(), () -> TradeManager.updateTrade(trade), 1L);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player player = Bukkit.getPlayerExact(event.getWhoClicked().getName());
        DataTrade trade = TradeManager.trades.get(player);
        if (trade != null) {
            for (int slot : event.getInventorySlots()) {
                if (trade.playerReady1 || trade.playerReady2) {
                    event.setResult(Event.Result.DENY);
                    event.setCancelled(true);
                    return;
                }
                if (slot < 9 || slot % 9 > 3) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                    return;
                }
            }
            Bukkit.getScheduler().runTaskLater(SpigotPlugin.getPlugin(), () -> TradeManager.updateTrade(trade), 1L);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        DataTrade trade = TradeManager.trades.get(player);
        if (trade != null) {
            event.setCancelled(true);
        }
    }

    private void abortTrade(Player player) {
        DataTrade trade = TradeManager.trades.get(player);
        if (trade != null) {
            TradeManager.abortTrade(trade);
        }
    }
}
