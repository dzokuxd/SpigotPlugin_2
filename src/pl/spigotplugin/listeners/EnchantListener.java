package pl.spigotplugin.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public class EnchantListener implements Listener {
    @EventHandler
    public void onEnchant(EnchantItemEvent e) {
        e.getEnchanter().setLevel(e.getEnchanter().getLevel() - e.getExpLevelCost() + (e.whichButton() + 1));
        e.setExpLevelCost(0);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory().getType().equals(InventoryType.ENCHANTING)) {
            EnchantingInventory en = (EnchantingInventory)e.getInventory();
            en.setSecondary(new ItemStack(Material.INK_SACK, 64, (short)4));
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getType().equals(InventoryType.ENCHANTING)) {
            event.getInventory().setItem(1, null);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getType().equals(InventoryType.ENCHANTING) && event.getRawSlot() == 1) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        ItemStack item = event.getCurrentItem();
        if (item.getType() == Material.AIR) {
            return;
        }
        if (item.getItemMeta() == null) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (!(itemMeta instanceof Repairable)) {
            return;
        }
        Repairable repairable = (Repairable)itemMeta;
        if (!repairable.hasRepairCost()) {
            return;
        }
        int cap = 33;
        if (repairable.getRepairCost() < cap) {
            return;
        }
        repairable.setRepairCost(cap - 1);
        item.setItemMeta(itemMeta);
    }
}
