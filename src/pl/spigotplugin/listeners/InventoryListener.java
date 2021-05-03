package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import pl.spigotplugin.commands.StoneCommand;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.menu.ChatMenu;
import pl.spigotplugin.menu.KitMenu;
import pl.spigotplugin.menu.SchowekMenu;
import pl.spigotplugin.menu.StoneMenu;
import pl.spigotplugin.objects.drop.Drop;
import pl.spigotplugin.objects.drop.RandomDropData;
import pl.spigotplugin.objects.user.Backup;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.settings.Settings;
import pl.spigotplugin.utils.*;

import java.sql.SQLException;
import java.util.List;

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
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCraftingi"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                CraftingUtil.openEndStone(p);
                return;
            }
            if (slot == 1) {
                CraftingUtil.openBoy(p);
                return;
            }
            if (slot == 2) {
                CraftingUtil.openEnderchest(p);
                return;
            }
            if (slot == 3) {
                CraftingUtil.openAntyNogi(p);
                return;
            }
            if (slot == 4) {
                CraftingUtil.openPotka(p);
                return;
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 1/5"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "4:0-6:Cobblestone;152:0-2:Redstone";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    ItemUtil.getItem(p, cost, 1);
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ChatUtil.giveItems(p, new ItemBuilder(Material.ENDER_STONE, 1).setTitle(ChatUtil.color("&c&lStoniarka")).addEnchantment(Enchantment.THORNS, 10).build());
                p.sendMessage("&c&lGratulacje! &7Utworzyles stoniarke!");
            } else {
                CraftingUtil.openMenu(p);
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 2/5"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "49:0-6:Obsidian;41:0-2:Gold Block;152:0-1:redstone block;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    ItemUtil.getItem(p, cost, 1);
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ChatUtil.giveItems(p, new ItemBuilder(Material.ENDER_PORTAL_FRAME, 4).setTitle(ChatUtil.color("&a&lBoyFarmer")).addEnchantment(Enchantment.THORNS, 10).build());
                p.sendMessage("&c&lGratulacje! &7Utworzyles boyfarmer!");
            } else {
                CraftingUtil.openMenu(p);
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 3/5"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "49:0-8:Obsidian;368:0-1:Ender Pearl;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    ItemUtil.getItem(p, cost, 1);
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ChatUtil.giveItems(p, new ItemBuilder(Material.ENDER_CHEST, 1).build());
                p.sendMessage("&c&lGratulacje! &7Utworzyles enderchest!");
            } else {
                CraftingUtil.openMenu(p);
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 4/5"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "41:0-8:Gold Block;397:3-1:Glwoa Gracz;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    ItemUtil.getItem(p, cost, 1);
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ChatUtil.giveItems(p, new ItemBuilder(Material.NAME_TAG, 1).setTitle(ChatUtil.color("&6&lAnty Nogi")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8\u00bb &2Kliknij PPM, aby uratowac czlonka gildii!")).addEnchantment(Enchantment.DURABILITY, 2).build());
                p.sendMessage("&c&lGratulacje! &7Utworzyles Anty Nogi");
            } else {
                CraftingUtil.openMenu(p);
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 5/5"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "20:0-8:Szklo;397:3-1:Glwoa Gracz;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    ItemUtil.getItem(p, cost, 1);
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ChatUtil.giveItems(p, new ItemBuilder(Material.POTION, 1, (short) 8227).setTitle(ChatUtil.color("&c&lPotka Fire")).addLore(ChatUtil.color("")).build());
                p.sendMessage("&c&lGratulacje! &7Utworzyles Potke Fire");
            } else {
                CraftingUtil.openMenu(p);
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
                KitMenu.show(p);
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
                KitMenu.show(p);
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
                KitMenu.show(p);
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
                KitMenu.show(p);
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lMenu Dropow"))) {
            e.setCancelled(true);
            if (e.getSlot() == 10) {
                StoneMenu.stone((Player) e.getWhoClicked());
            } else if (e.getSlot() == 12) {
                StoneMenu.ez633((Player) e.getWhoClicked());
            } else if (e.getSlot() == 14) {
                StoneMenu.easycase((Player) e.getWhoClicked());
            } else if (e.getSlot() == 16) {
                StoneMenu.inventory((Player) e.getWhoClicked());
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lDrop z Case"))) {
            e.setCancelled(true);
            if (e.getSlot() == 53) {
                StoneMenu.menu((Player) e.getWhoClicked());
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lGamePlay"))) {
            e.setCancelled(true);
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lDrop z Ez6/3/3"))) {
            e.setCancelled(true);
            if (e.getSlot() == 8) {
                StoneMenu.menu((Player) e.getWhoClicked());
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color(Settings.inventoryName))) {
            e.setCancelled(true);
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lSchowek"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int schowek_koxy;
            int schowek_refile;
            int schowek_perly;
            int schowek_strzaly;
            int k1 = ItemUtil.getItemAmount(Material.GOLDEN_APPLE, p, (short) 1);
            int k2 = ItemUtil.getItemAmount(Material.GOLDEN_APPLE, p, (short) 0);
            int k3 = ItemUtil.getItemAmount(Material.ENDER_PEARL, p, (short) 0);
            int k4 = ItemUtil.getItemAmount(Material.ARROW, p, (short) 0);
            int slot = e.getSlot();
            if (slot == 15) {
                wyplac.koxy(u, k1);
                wyplac.ref(u, k2);
                wyplac.pearl(u, k3);
                wyplac.strzalyw(u, k4);
                return;
            }

            if (slot == 10) {
                if (u.getkoxy() <= 0) {
                    p.sendMessage("&8\u00bb &cNie posiadasz koxow do wyplacenia!");
                    return;
                }
                if (k1 >= Config.LIMIT_KOX) {
                    p.sendMessage("&8\u00bb &cOsiagneles juz limit koxow!");
                    return;
                }
                if (u.getkoxy() <= Config.LIMIT_KOX) {
                    schowek_koxy = u.getkoxy();
                    u.removeKoxy(schowek_koxy);
                    ChatUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_koxy, (short) 1).build());
                    p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_koxy + " koxy");
                    SchowekMenu.show(p);
                    return;
                }
                if (k1 < Config.LIMIT_KOX) {
                    int koxy= k1 - Config.LIMIT_KOX;
                    schowek_koxy = koxy * -1;
                    u.removeKoxy(schowek_koxy);
                    ChatUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_koxy, (short) 1).build());
                    p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_koxy + " koxy");
                    SchowekMenu.show(p);
                    return;
                }
            }
            if (slot == 11) {
                if (u.getRefile() <= 0) {
                    p.sendMessage("&8\u00bb &cNie posiadasz refow do wyplacenia!");
                    return;
                }
                if (k2 >= Config.LIMIT_REFILE) {
                    p.sendMessage("&8\u00bb &cOsiagneles juz limit refili!");
                    return;
                }
                if (u.getRefile() <= Config.LIMIT_REFILE) {
                    schowek_refile = u.getRefile();
                    u.removeRefile(schowek_refile);
                    ChatUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_refile, (short) 0).build());
                    p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_refile + " refile");
                    SchowekMenu.show(p);
                    return;
                }
                if (k2 < Config.LIMIT_REFILE) {
                    int ref = k2 - Config.LIMIT_REFILE;
                    schowek_refile = ref * -1;
                    u.removeRefile(schowek_refile);
                    ChatUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_refile, (short) 0).build());
                    p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_refile + " refile");
                    SchowekMenu.show(p);
                    return;
                }
            }
            if (slot == 12) {
                if (u.getPerly() <= 0) {
                    p.sendMessage("&8\u00bb &cNie posiadasz perel do wyplacenia!");
                    return;
                }
                if (k3 >= Config.LIMIT_PEARL) {
                    p.sendMessage("&8\u00bb &cOsiagneles juz limit perel!");
                    return;
                }
                if (u.getPerly() <= Config.LIMIT_PEARL) {
                    schowek_perly = u.getPerly();
                    u.removePerly(schowek_perly);
                    ChatUtil.giveItems(p, new ItemBuilder(Material.ENDER_PEARL, schowek_perly, (short) 0).build());
                    p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_perly + " perly");
                    SchowekMenu.show(p);
                    return;
                }
                if (k3 < Config.LIMIT_PEARL) {
                    int ref = k3 - Config.LIMIT_PEARL;
                    schowek_perly = ref * -1;
                    u.removePerly(schowek_perly);
                    ChatUtil.giveItems(p, new ItemBuilder(Material.ENDER_PEARL, schowek_perly, (short) 0).build());
                    p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_perly + " perly");
                    SchowekMenu.show(p);
                    return;
                }
            }
            if (slot == 13) {
                if (u.getStrzaly() <= 0) {
                    p.sendMessage("&8\u00bb &cNie posiadasz strzal do wyplacenia!");
                    return;
                }
                if (k4 >= Config.LIMIT_STRZAL) {
                    p.sendMessage("&8\u00bb &cOsiagneles juz limit strzal!");
                    return;
                }
                if (u.getStrzaly() <= Config.LIMIT_STRZAL) {
                    schowek_strzaly = u.getStrzaly();
                    u.removeStrzaly(schowek_strzaly);
                    ChatUtil.giveItems(p, new ItemBuilder(Material.ARROW, schowek_strzaly, (short) 0).build());
                    p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_strzaly + " strzal");
                    SchowekMenu.show(p);
                    return;
                }
                if (k4 < Config.LIMIT_STRZAL) {
                    int strzaly = k4 - Config.LIMIT_STRZAL;
                    schowek_strzaly = strzaly * -1;
                    u.removeStrzaly(schowek_strzaly);
                    ChatUtil.giveItems(p, new ItemBuilder(Material.ARROW, schowek_strzaly, (short) 0).build());
                    p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_strzaly + " strzal");
                    SchowekMenu.show(p);
                    return;
                }
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lDrop z Stone"))) {
            e.setCancelled(true);
            ItemStack item1 = e.getCurrentItem();
            if (item1 != null) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    Drop d = RandomDropData.getDropByName(ChatColor.stripColor(ChatUtil.color(meta.getDisplayName())));
                    if (d != null) {
                        d.changeStatus(e.getWhoClicked().getUniqueId());
                        List<String> string = meta.getLore();
                        string.set(4, ChatUtil.color(" &8\u00bb &7Drop: " + (d.isDisabled(e.getWhoClicked().getUniqueId()) ? "&cNie" : "&aTak")));
                        meta.setLore(string);
                        item.setItemMeta(meta);
                        StoneMenu.stone((Player) e.getWhoClicked());
                    }
                    if (meta.getDisplayName() != null && meta.getDisplayName().equals(ChatUtil.color("&4Wroc do poprzedniej strony!"))) {
                        p.closeInventory();
                        StoneMenu.menu(p);
                    } else if (meta.getDisplayName() != null && meta.getDisplayName().equals(ChatUtil.color("&aWlacz Wszystkie Dropy"))) {
                        for (Drop drop : RandomDropData.getDrops()) {
                            drop.setStatus(e.getWhoClicked().getUniqueId(), true);
                        }
                        StoneMenu.stone(p);
                    } else if (meta.getDisplayName() != null && meta.getDisplayName().equals(ChatUtil.color("&cWylacz Wszystkie Dropy"))) {
                        for (Drop drop : RandomDropData.getDrops()) {
                            drop.setStatus(e.getWhoClicked().getUniqueId(), false);
                        }
                        StoneMenu.stone(p);
                    }
                    if (meta.getDisplayName() != null && meta.getDisplayName().equals(ChatUtil.color("&7&lCobblestone"))) {
                        RandomDropData.changeNoCobble(e.getWhoClicked().getUniqueId());
                        List<String> string = meta.getLore();
                        string.set(0, ChatUtil.color(" &8\u00bb &7Drop: &" + (RandomDropData.isNoCobble(e.getWhoClicked().getUniqueId()) ? "cNie" : "aTak")));
                        meta.setLore(string);
                        item.setItemMeta(meta);
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
    private static class wyplac {
        public static void koxy(User u, int k1) {
            Player p = u.getPlayer();
            int schowek_koxy;
            if (u.getkoxy() <= 0) {
                p.sendMessage("&8\u00bb &cNie posiadasz koxow do wyplacenia!");
                return;
            }
            if (k1 >= Config.LIMIT_KOX) {
                p.sendMessage("&8\u00bb &cOsiagneles juz limit koxow!");
                return;
            }
            if (u.getkoxy() <= Config.LIMIT_KOX) {
                schowek_koxy = u.getkoxy();
                u.removeKoxy(schowek_koxy);
                ChatUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_koxy, (short) 1).build());
                p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_koxy + " koxy");
                SchowekMenu.show(p);
                return;
            }
            if (k1 < Config.LIMIT_KOX) {
                int ref = k1 - Config.LIMIT_KOX;
                schowek_koxy = ref * -1;
                u.removeKoxy(schowek_koxy);
                ChatUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_koxy, (short) 1).build());
                p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_koxy + " koxy");
                SchowekMenu.show(p);
            }
        }
        public static void ref(User u, int k2) {
            Player p = u.getPlayer();
            int schowek_strzaly;
            if (u.getRefile() <= 0) {
                p.sendMessage("&8\u00bb &cNie posiadasz refow do wyplacenia!");
                return;
            }
            if (k2 >= Config.LIMIT_REFILE) {
                p.sendMessage("&8\u00bb &cOsiagneles juz limit refili!");
                return;
            }
            if (u.getRefile() <= Config.LIMIT_REFILE) {
                schowek_strzaly = u.getRefile();
                u.removeRefile(schowek_strzaly);
                ChatUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_strzaly, (short) 0).build());
                p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_strzaly + " refile");
                SchowekMenu.show(p);
                return;
            }
            if (k2 < Config.LIMIT_REFILE) {
                int ref = k2 - Config.LIMIT_REFILE;
                schowek_strzaly = ref * -1;
                u.removeRefile(schowek_strzaly);
                ChatUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_strzaly, (short) 0).build());
                p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_strzaly + " refile");
                SchowekMenu.show(p);
            }
        }

        public static void pearl(User u, int k3) {
            Player p = u.getPlayer();
            int schowek_perly;
            if (u.getPerly() <= 0) {
                p.sendMessage("&8\u00bb &cNie posiadasz perel do wyplacenia!");
                return;
            }
            if (k3 >= Config.LIMIT_PEARL) {
                p.sendMessage("&8\u00bb &cOsiagneles juz limit perel!");
                return;
            }
            if (u.getPerly() <= Config.LIMIT_PEARL) {
                schowek_perly = u.getPerly();
                u.removePerly(schowek_perly);
                ChatUtil.giveItems(p, new ItemBuilder(Material.ENDER_PEARL, schowek_perly, (short) 0).build());
                p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_perly + " perly");
                SchowekMenu.show(p);
                return;
            }
            if (k3 < Config.LIMIT_PEARL) {
                int ref = k3 - Config.LIMIT_PEARL;
                schowek_perly = ref * -1;
                u.removePerly(schowek_perly);
                ChatUtil.giveItems(p, new ItemBuilder(Material.ENDER_PEARL, schowek_perly, (short) 0).build());
                p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_perly + " perly");
                SchowekMenu.show(p);
            }
        }

        public static void strzalyw(User u, int k4) {
            Player p = u.getPlayer();
            int schowek_strzaly;
            if (u.getStrzaly() <= 0) {
                p.sendMessage("&8\u00bb &cNie posiadasz strzal do wyplacenia!");
                return;
            }
            if (k4 >= Config.LIMIT_STRZAL) {
                p.sendMessage("&8\u00bb &cOsiagneles juz limit strzal!");
                return;
            }
            if (u.getStrzaly() <= Config.LIMIT_STRZAL) {
                schowek_strzaly = u.getStrzaly();
                u.removeStrzaly(schowek_strzaly);
                ChatUtil.giveItems(p, new ItemBuilder(Material.ARROW, schowek_strzaly, (short) 0).build());
                p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_strzaly + " perly");
                SchowekMenu.show(p);
                return;
            }
            if (k4 < Config.LIMIT_STRZAL) {
                int ref = k4 - Config.LIMIT_STRZAL;
                schowek_strzaly = ref * -1;
                u.removeStrzaly(schowek_strzaly);
                ChatUtil.giveItems(p, new ItemBuilder(Material.ARROW, schowek_strzaly, (short) 0).build());
                p.sendMessage("&8\u00bb &7Wyplaciles &6" + schowek_strzaly + " strzal");
                SchowekMenu.show(p);
            }
        }
    }
}
