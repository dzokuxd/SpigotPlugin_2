package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.menu.ChatMenu;
import pl.spigotplugin.objects.user.Backup;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.ItemBuilder;
import pl.spigotplugin.utils.TimeUtil;

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
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lKity"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 2) {
                if (!p.hasPermission("") && !Config.MANAGE_KIT) {
                    p.sendMessage("&8\u00bb &cKity sa tymczasowo wylaczone!");
                    return;
                }
                if (!p.hasPermission("") && !p.hasPermission("")) {
                    p.sendMessage("&8\u00bb &cAby posiadasz dostepu! Zakup range!");
                    return;
                }
                if (u.isKitVip() && !p.hasPermission("")) {
                    p.sendMessage("&8\u00bb &7Kit vip mozesz uzyc dopiero za &c&l" + DataUtil.secondsToString(u.getKit_vip()));
                    return;
                }
                u.setKit_vip(System.currentTimeMillis() + TimeUtil.DAY.getTime(2));
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.KNOCKBACK, 2).build());
                ChatUtil.giveItems(p, new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));
                ChatUtil.giveItems(p, new ItemStack(Material.GOLDEN_APPLE, 12));
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 4).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.ARROW_FIRE, 1).build());
                ChatUtil.giveItems(p, new ItemStack(Material.ENDER_PEARL, 4));
                ChatUtil.giveItems(p, new ItemStack(Material.ARROW, 30));
                p.sendMessage("&8\u00bb &cOtrzymales kit vip!");
                return;
            }
            if (slot == 3) {
                if (!p.hasPermission("") && !Config.MANAGE_KIT) {
                    p.sendMessage("&8\u00bb &cKity sa tymczasowo wylaczone!");
                    return;
                }
                if (!p.hasPermission("") && !p.hasPermission("")) {
                    p.sendMessage("&8\u00bb &cAby posiadac dostepu! Zakup range!");
                    return;
                }
                if (u.isKitSvip() && !p.hasPermission("spigotplugin.kit")) {
                    p.sendMessage("&8\u00bb &7Kit svip mozesz uzyc dopiero za &c&l" + DataUtil.secondsToString(u.getKit_svip()));
                    return;
                }
                u.setKit_svip(System.currentTimeMillis() + TimeUtil.DAY.getTime(2));
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.KNOCKBACK, 2).build());
                ChatUtil.giveItems(p, new ItemStack(Material.GOLDEN_APPLE, 2, (short) 1));
                ChatUtil.giveItems(p, new ItemStack(Material.GOLDEN_APPLE, 16));
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3).build());
                ChatUtil.giveItems(p, new ItemBuilder(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 4).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.ARROW_FIRE, 1).build());
                ChatUtil.giveItems(p, new ItemStack(Material.ENDER_PEARL, 4));
                ChatUtil.giveItems(p, new ItemStack(Material.ARROW, 40));
                p.sendMessage("&8\u00bb &cOtrzymales kit svip!");
                return;
            }
            if (slot == 1) {
                if (u.isKitStart() && !p.hasPermission("")) {
                    p.sendMessage("&8\u00bb &7Kit start mozesz uzyc dopiero za &c&l" + DataUtil.secondsToString(u.getKit_start()));
                    return;
                }
                u.setKit_start(System.currentTimeMillis() + TimeUtil.HOUR.getTime(1));
                ChatUtil.giveItems(p, new ItemStack(Material.STONE_PICKAXE));
                ChatUtil.giveItems(p, new ItemStack(Material.ENDER_CHEST));
                ChatUtil.giveItems(p, new ItemStack(Material.COOKED_BEEF, 64));
                ChatUtil.giveItems(p, new ItemStack(Material.WOOD, 48));
                p.sendMessage("&8\u00bb &cOtrzymales kit start!");
                return;
            }
            if (slot == 0) {
                if (u.isKitMieso() && !p.hasPermission("")) {
                    p.sendMessage("&8\u00bb &7Kit mieso mozesz uzyc dopiero za &c&l" + DataUtil.secondsToString(u.getKit_mieso()));
                    return;
                }
                u.setKit_mieso(System.currentTimeMillis() + TimeUtil.SECOND.getTime(60));
                ChatUtil.giveItems(p, new ItemStack(Material.COOKED_BEEF, 128));
                p.sendMessage("&8\u00bb &cOtrzymales kit mieso!");
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
