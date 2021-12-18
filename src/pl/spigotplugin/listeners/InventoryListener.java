package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.enums.AchievmentType;
import pl.spigotplugin.enums.AchievmentTypeName;
import pl.spigotplugin.enums.ArmorType;
import pl.spigotplugin.managers.DataManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.menu.*;
import pl.spigotplugin.objects.drop.Drop;
import pl.spigotplugin.objects.drop.RandomDropData;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.Backup;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.configs.Settings;
import pl.spigotplugin.tasks.GuildRegenerationTask;
import pl.spigotplugin.utils.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class InventoryListener implements Listener {
    @EventHandler
    public void onClickBackup(InventoryClickEvent e) {
        e.getWhoClicked().sendMessage(String.valueOf(e.getSlot()));
        if (e.getInventory().getName().contains(ChatUtil.color("&7Backup'y gracza"))) {
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
        if (("§7Gracze do uratowania").equalsIgnoreCase(e.getInventory().getName())) {
            e.setCancelled(true);
            if (item != null) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    if (item.getType().equals(Material.SKULL_ITEM)) {
                        Player pl = Bukkit.getPlayer(meta.getDisplayName().replace("§6", ""));
                        pl.sendMessage("&6Zostales uratowany z nozek przez &c" + p.getName() + "&6 dzieki &c&lAnty Nogi");
                        p.sendMessage("&6Uratowales gracza &c" + pl.getName());
                        pl.teleport(p.getLocation());
                        ChatUtil.sendTitleMessage(pl, ChatUtil.color("&7&lAnty Nogi"), ChatUtil.color("&cZostales uratowany z nozek!"), 2, 2, 2);
                        Bukkit.broadcastMessage("&6Gracz &c" + pl.getName() + " &6zostal uratowany z nozek przez &c" + p.getName() + "&7!");
                        ItemBuilder anty = new ItemBuilder(Material.NAME_TAG, 1).setTitle(ChatUtil.color("&6&lAnty Nogi")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8\u00bb &2Kliknij PPM, aby uratowac czlonka gildii!")).addEnchantment(Enchantment.DURABILITY, 2);
                        p.getInventory().removeItem(anty.build());
                        p.closeInventory();
                        u.addCoins(100);
                        u.save();
                    }
                }
            }
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
            return;
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
            if (slot == 6) {
                CraftingUtil.openPumpkin(p);
                return;
            }
            if (slot == 7) {
                CraftingUtil.openRefil(p);
                return;
            }
            if (slot == 8) {
                CraftingUtil.openKox(p);
                return;
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 1/8"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "4:0-6:Cobblestone;152:0-2:Redstone";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    p.sendMessage("&cNie masz potrzebnych rzeczy!");
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ItemUtil.giveItems(p, new ItemBuilder(Material.ENDER_STONE, 1).setTitle(ChatUtil.color("&c&lStoniarka")).addEnchantment(Enchantment.THORNS, 10).build());
            } else {
                p.closeInventory();
                CraftingUtil.openMenu(p);
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 2/8"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "49:0-6:Obsidian;41:0-2:Gold Block;152:0-1:redstone block;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    p.sendMessage("&cNie masz potrzebnych rzeczy!");
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ItemUtil.giveItems(p, new ItemBuilder(Material.ENDER_PORTAL_FRAME, 4).setTitle(ChatUtil.color("&a&lBoyFarmer")).addEnchantment(Enchantment.THORNS, 10).build());
            } else {
                p.closeInventory();
                CraftingUtil.openMenu(p);
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 3/8"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "49:0-8:Obsidian;368:0-1:Ender Pearl;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    p.sendMessage("&cNie masz potrzebnych rzeczy!");
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ItemUtil.giveItems(p, new ItemBuilder(Material.ENDER_CHEST, 1).build());
            } else {
                p.closeInventory();
                CraftingUtil.openMenu(p);
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 4/8"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "41:0-8:Gold Block;397:3-1:Glwoa Gracz;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    p.sendMessage("&cNie masz potrzebnych rzeczy!");
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ItemUtil.giveItems(p, new ItemBuilder(Material.NAME_TAG, 1).setTitle(ChatUtil.color("&6&lAnty Nogi")).addLore(ChatUtil.color("")).addLore(ChatUtil.color("&8\u00bb &2Kliknij PPM, aby uratowac czlonka gildii!")).addEnchantment(Enchantment.DURABILITY, 2).build());
            } else {
                p.closeInventory();
                CraftingUtil.openMenu(p);
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 5/8"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "20:0-8:Szklo;397:3-1:Glwoa Gracz;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    p.sendMessage("&cNie masz potrzebnych rzeczy!");
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ItemUtil.giveItems(p, new ItemBuilder(Material.POTION, 1, (short) 8227).setTitle(ChatUtil.color("&c&lPotka Fire")).addLore(ChatUtil.color("")).build());
            } else {
                p.closeInventory();
                CraftingUtil.openMenu(p);
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 6/8"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "266:0-8:Zloto;260:0-1:Jablko;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    p.sendMessage("&cNie masz potrzebnych rzeczy!");
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ItemUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, 1, (short) 0).build());
            } else {
                p.closeInventory();
                CraftingUtil.openMenu(p);
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 7/8"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "41:0-8:Blok Zlota;260:0-1:Jablko;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    p.sendMessage("&cNie masz potrzebnych rzeczy!");
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ItemUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, 1, (short) 1).build());
            } else {
                p.closeInventory();
                CraftingUtil.openMenu(p);
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lCrafting 8/8"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 0) {
                String cost = "265:0-8:Zelazo;260:0-1:Jablko;";
                if (!ItemUtil.checkItems(p, cost, 1)) {
                    p.sendMessage("&cNie masz potrzebnych rzeczy!");
                    return;
                }
                ItemUtil.removeItems(p, cost, 1);
                ItemUtil.giveItems(p, new ItemBuilder(Material.PUMPKIN_PIE).setGlow(true).build());
            } else {
                p.closeInventory();
                CraftingUtil.openMenu(p);
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("Osiagniecia:"))) {
            e.setCancelled(true);
            AchievmentTypeName type = null;
            switch (e.getRawSlot()) {

                case 9:
                    type = AchievmentTypeName.STONE;
                    break;
                case 10:
                    type = AchievmentTypeName.OBSIDIAN;
                    break;
                case 11:
                    type = AchievmentTypeName.KILLS;
                    break;
                case 12:
                    type = AchievmentTypeName.ASYSTY;
                    break;
                case 13:
                    type = AchievmentTypeName.KOX;
                    break;
                case 14:
                    type = AchievmentTypeName.REF;
                    break;
                case 15:
                    type = AchievmentTypeName.TIME;
                    break;
                default:
                    break;
            }
            if(type == null) {
                System.out.println("&cCos poszlo nie tak!");
                return;
            }
            AchievmentMenu.openSub(p, type);
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lOsiagniecia - &cStone"))) {
            e.setCancelled(true);
            for (AchievmentType value : AchievmentType.values()) {
                AchievmentTypeName name = AchievmentTypeName.STONE;
                if(value.getType() == name) {
                    if(value.getGuiSlot() == e.getRawSlot()) {
                        int lvl = e.getRawSlot() - 8;
                        int get = u.getAchLvl(name);
                        if(get >= lvl) {
                            p.sendMessage("&cOdebrales juz nagrode za to osiagniecie!");
                            AchievmentMenu.open(p);
                            return;
                        }
                        if(u.getWykStone() >= value.getNeededAmount()) {
                            HashMap<Integer, ItemStack> notAdded = p.getInventory().addItem(value.getReward());
                            for (ItemStack itemStack : notAdded.values()) {
                                p.getLocation().getWorld().dropItemNaturally(p.getLocation(),itemStack);
                            }
                            u.setAchLvl(name,get+1);
                            p.sendMessage("&aPomyslnie odblokowales osiagniecie");
                            AchievmentMenu.open(p);
                            u.save();
                        } else {
                            p.sendMessage("&cNie spelniasz wymagan!");
                            AchievmentMenu.open(p);
                        }
                    }
                }
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lOsiagniecia - &cZabojstwa"))) {
            e.setCancelled(true);
            for (AchievmentType value : AchievmentType.values()) {
                AchievmentTypeName name = AchievmentTypeName.KILLS;
                if(value.getType() == name) {
                    if(value.getGuiSlot() == e.getRawSlot()) {
                        int lvl = e.getRawSlot() - 8;
                        int get = u.getAchLvl(name);
                        if(get >= lvl) {
                            p.sendMessage("&cOdebrales juz nagrode za to osiagniecie!");
                            AchievmentMenu.open(p);
                            return;
                        }
                        if(u.getKills() >= value.getNeededAmount()) {
                            HashMap<Integer, ItemStack> notAdded = p.getInventory().addItem(value.getReward());
                            for (ItemStack itemStack : notAdded.values()) {
                                p.getLocation().getWorld().dropItemNaturally(p.getLocation(),itemStack);
                            }
                            u.setAchLvl(name,get+1);
                            p.sendMessage("&aPomyslnie odblokowales osiagniecie");
                            AchievmentMenu.open(p);
                        } else {
                            p.sendMessage("&cNie posiadasz wymagan!");
                            AchievmentMenu.open(p);
                        }
                    }
                }
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lOsiagniecia - &cAsysty"))) {
            e.setCancelled(true);
            for (AchievmentType value : AchievmentType.values()) {
                AchievmentTypeName name = AchievmentTypeName.ASYSTY;
                if(value.getType() == name) {
                    if(value.getGuiSlot() == e.getRawSlot()) {
                        int lvl = e.getRawSlot() - 8;
                        int get = u.getAchLvl(name);
                        if(get >= lvl) {
                            p.sendMessage("&cOdebrales juz nagrode za to osiagniecie!");
                            AchievmentMenu.open(p);
                            return;
                        }
                        if(u.getAsysty() >= value.getNeededAmount()) {
                            HashMap<Integer, ItemStack> notAdded = p.getInventory().addItem(value.getReward());
                            for (ItemStack itemStack : notAdded.values()) {
                                p.getLocation().getWorld().dropItemNaturally(p.getLocation(),itemStack);
                            }
                            u.setAchLvl(name,get+1);
                            p.sendMessage("&aPomyslnie odblokowales osiagniecie");
                            AchievmentMenu.open(p);
                        } else {
                            p.sendMessage("&cNie posiadasz wymagan!");
                            AchievmentMenu.open(p);
                        }
                    }
                }
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lOsiagniecia - &cZjedzone refile"))) {
            e.setCancelled(true);
            for (AchievmentType value : AchievmentType.values()) {
                AchievmentTypeName name = AchievmentTypeName.REF;
                if(value.getType() == name) {
                    if(value.getGuiSlot() == e.getRawSlot()) {
                        int lvl = e.getRawSlot() - 8;
                        int get = u.getAchLvl(name);
                        if(get >= lvl) {
                            p.sendMessage("&cOdebrales juz nagrode za to osiagniecie!");
                            AchievmentMenu.open(p);
                            return;
                        }
                        if(u.getRefilEaten() >= value.getNeededAmount()) {
                            HashMap<Integer, ItemStack> notAdded = p.getInventory().addItem(value.getReward());
                            for (ItemStack itemStack : notAdded.values()) {
                                p.getLocation().getWorld().dropItemNaturally(p.getLocation(),itemStack);
                            }
                            u.setAchLvl(name,get+1);
                            p.sendMessage("&aPomyslnie odblokowales osiagniecie");
                            AchievmentMenu.open(p);
                        } else {
                            p.sendMessage("&cNie posiadasz wymagan!");
                            AchievmentMenu.open(p);
                        }
                    }
                }
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lOsiagniecia - &cObsidian"))) {
            e.setCancelled(true);
            for (AchievmentType value : AchievmentType.values()) {
                AchievmentTypeName name = AchievmentTypeName.OBSIDIAN;
                if(value.getType() == name) {
                    if(value.getGuiSlot() == e.getRawSlot()) {
                        int lvl = e.getRawSlot() - 8;
                        int get = u.getAchLvl(name);
                        if(get >= lvl) {
                            p.sendMessage("&cOdebrales juz nagrode za to osiagniecie!");
                            AchievmentMenu.open(p);
                            return;
                        }
                        if(u.getWykObsidian() >= value.getNeededAmount()) {
                            HashMap<Integer, ItemStack> notAdded = p.getInventory().addItem(value.getReward());
                            for (ItemStack itemStack : notAdded.values()) {
                                p.getLocation().getWorld().dropItemNaturally(p.getLocation(),itemStack);
                            }
                            u.setAchLvl(name,get+1);
                            p.sendMessage("&aPomyslnie odblokowales osiagniecie");
                            AchievmentMenu.open(p);
                        } else {
                            p.sendMessage("&cNie posiadasz wymagan!");
                            AchievmentMenu.open(p);
                        }
                    }
                }
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lOsiagniecia - &cZjedzone koxy"))) {
            e.setCancelled(true);
            for (AchievmentType value : AchievmentType.values()) {
                AchievmentTypeName name = AchievmentTypeName.KOX;
                if(value.getType() == name) {
                    if(value.getGuiSlot() == e.getRawSlot()) {
                        int lvl = e.getRawSlot() - 8;
                        int get = u.getAchLvl(name);
                        if(get >= lvl) {
                            p.sendMessage("&cOdebrales juz nagrode za to osiagniecie!");
                            AchievmentMenu.open(p);
                            return;
                        }
                        if(u.getKoxEaten() >= value.getNeededAmount()) {
                            HashMap<Integer, ItemStack> notAdded = p.getInventory().addItem(value.getReward());
                            for (ItemStack itemStack : notAdded.values()) {
                                p.getLocation().getWorld().dropItemNaturally(p.getLocation(),itemStack);
                            }
                            u.setAchLvl(name,get+1);
                            p.sendMessage("&aPomyslnie odblokowales osiagniecie");
                            AchievmentMenu.open(p);
                        } else {
                            p.sendMessage("&cNie posiadasz wymagan!");
                            AchievmentMenu.open(p);
                        }
                    }
                }
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lOsiagniecia - &cCzas gry"))) {
            e.setCancelled(true);
            for (AchievmentType value : AchievmentType.values()) {
                AchievmentTypeName name = AchievmentTypeName.TIME;
                if (value.getType() == name) {
                    if (value.getGuiSlot() == e.getRawSlot()) {
                        int lvl = e.getRawSlot() - 8;
                        int get = u.getAchLvl(name);
                        if (get >= lvl) {
                            p.sendMessage("&cOdebrales juz nagrode za to osiagniecie!");
                            AchievmentMenu.open(p);
                            return;
                        }
                        if (u.getTime() >= value.getNeededAmount()) {

                            HashMap<Integer, ItemStack> notAdded = p.getInventory().addItem(value.getReward());
                            for (ItemStack itemStack : notAdded.values()) {
                                p.getLocation().getWorld().dropItemNaturally(p.getLocation(), itemStack);
                            }
                            u.setAchLvl(name, get + 1);
                            p.sendMessage("&aPomyslnie odblokowales osiagniecie");
                            AchievmentMenu.open(p);
                        } else {
                            p.sendMessage("&cNie posiadasz wymagan!");
                            AchievmentMenu.open(p);
                        }
                    }
                }
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lKity"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            int slot = e.getSlot();
            if (slot == 2) {
                if (!p.hasPermission("spigot.admin") && !statues.MANAGE_KIT) {
                    p.sendMessage("&cKity sa tymczasowo wylaczone!");
                    return;
                }
                if (!p.hasPermission("spigot.admin") && !p.hasPermission("spigot.vip")) {
                    p.sendMessage("&cNie posiadasz dostepu! Zakup range premium!");
                    return;
                }
                if (u.isKitVip() && !p.hasPermission("spigot.admin")) {
                    p.sendMessage("&7Kit vip mozesz uzyc dopiero za &c&l" + DataUtil.secondsToString(u.getKit_vip()));
                    return;
                }
                u.setKit_vip(System.currentTimeMillis() + TimeUtil.HOUR.getTime(12));
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.KNOCKBACK, 2).build());
                ItemUtil.giveItems(p, new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));
                ItemUtil.giveItems(p, new ItemStack(Material.GOLDEN_APPLE, 12));
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 4).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.ARROW_FIRE, 1).build());
                ItemUtil.giveItems(p, new ItemStack(Material.ENDER_PEARL, 4));
                ItemUtil.giveItems(p, new ItemStack(Material.ARROW, 30));
                p.sendMessage("&aOtrzymales kit vip!");
                KitMenu.show(p);
                return;
            }
            if (slot == 3) {
                if (!p.hasPermission("spigot.admin") && !statues.MANAGE_KIT) {
                    p.sendMessage("&8\u00bb &cKity sa tymczasowo wylaczone!");
                    return;
                }
                if (!p.hasPermission("spigot.admin") && !p.hasPermission("spigot.svip")) {
                    p.sendMessage("&8\u00bb &cAby posiadac dostepu! Zakup range!");
                    return;
                }
                if (u.isKitSvip() && !p.hasPermission("spigotplugin.admin")) {
                    p.sendMessage("&8\u00bb &7Kit svip mozesz uzyc dopiero za &c&l" + DataUtil.secondsToString(u.getKit_svip()));
                    return;
                }
                u.setKit_svip(System.currentTimeMillis() + TimeUtil.HOUR.getTime(12));
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.KNOCKBACK, 2).build());
                ItemUtil.giveItems(p, new ItemStack(Material.GOLDEN_APPLE, 2, (short) 1));
                ItemUtil.giveItems(p, new ItemStack(Material.GOLDEN_APPLE, 16));
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 4).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.ARROW_FIRE, 1).build());
                ItemUtil.giveItems(p, new ItemStack(Material.ENDER_PEARL, 4));
                ItemUtil.giveItems(p, new ItemStack(Material.ARROW, 40));
                p.sendMessage("&aOtrzymales kit svip!");
                KitMenu.show(p);
                return;
            }
            if (slot == 1) {
                if (u.isKitStart() && !p.hasPermission("spigot.admin")) {
                    p.sendMessage("&8\u00bb &7Kit start mozesz uzyc dopiero za &c&l" + DataUtil.secondsToString(u.getKit_start()));
                    return;
                }
                u.setKit_start(System.currentTimeMillis() + TimeUtil.HOUR.getTime(1));
                ItemUtil.giveItems(p, new ItemStack(Material.STONE_PICKAXE));
                ItemUtil.giveItems(p, new ItemStack(Material.ENDER_CHEST));
                ItemUtil.giveItems(p, new ItemStack(Material.COOKED_BEEF, 64));
                ItemUtil.giveItems(p, new ItemStack(Material.WOOD, 48));
                p.sendMessage("&aOtrzymales kit start!");
                KitMenu.show(p);
                return;
            }
            if (slot == 0) {
                if (u.isKitMieso() && !p.hasPermission("spigot.admin")) {
                    p.sendMessage("&7Kit mieso mozesz uzyc dopiero za &c&l" + DataUtil.secondsToString(u.getKit_mieso()));
                    return;
                }
                u.setKit_mieso(System.currentTimeMillis() + TimeUtil.SECOND.getTime(60));
                ItemUtil.giveItems(p, new ItemStack(Material.COOKED_BEEF, 128));
                p.sendMessage("&aOtrzymales kit mieso!");
                KitMenu.show(p);
            }
            if (slot == 4) {
                if (u.isKitTest() && !p.hasPermission("spigot.admin")) {
                    p.sendMessage("&7Kit test mozesz uzyc dopiero za &c&l" + DataUtil.secondsToString(u.getKit_mieso()));
                    return;
                }
                u.setKit_test(System.currentTimeMillis() + TimeUtil.SECOND.getTime(30));
                ItemUtil.giveItems(p, new ItemStack(Material.COOKED_BEEF, 64));
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.KNOCKBACK, 2).build());
                ItemUtil.giveItems(p, new ItemBuilder(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 4).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.ARROW_FIRE, 1).build());
                ItemUtil.giveItems(p, new ItemStack(Material.ENDER_PEARL, 4));
                ItemUtil.giveItems(p, new ItemStack(Material.ARROW, 40));
                ItemUtil.giveItems(p, new ItemStack(Material.PUMPKIN_PIE, 8));
                ItemUtil.giveItems(p, new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));
                ItemUtil.giveItems(p, new ItemStack(Material.GOLDEN_APPLE, 12));
                ItemUtil.giveItems(p, new ItemStack(Material.WATER_BUCKET, 1));
                p.sendMessage("&aOtrzymales kit test!");
                KitMenu.show(p);
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lMenu Dropow"))) {
            e.setCancelled(true);
            if (e.getSlot() == 10) {
                StoneMenu.stone((Player) e.getWhoClicked());
            } else if (e.getSlot() == 12) {
                StoneMenu.easy611((Player) e.getWhoClicked());
            } else if (e.getSlot() == 14) {
                StoneMenu.easycase((Player) e.getWhoClicked());
            } else if (e.getSlot() == 16) {
                StoneMenu.inventory((Player) e.getWhoClicked());
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lItemShop"))) {
            e.setCancelled(true);
            if (e.getSlot() == 10) {
                if (u.getEasycase() <= 0) {
                    p.closeInventory();
                    p.sendMessage("&8\u00bb &cNie posiadasz zadnych easycase'ow!");
                    return;
                }
                p.sendMessage("&aOdebrales: "+u.getEasycase()+" &aEasyCase'ow");
                DajUtil.giveWithAmount("easycase", u.getEasycase(), p);
                u.setEasycase(0);
                u.save();
                OdbierzMenu.show(p);
                return;
            }
            if (e.getSlot() == 16) {
                if (u.getCase611() <= 0) {
                    p.closeInventory();
                    p.sendMessage("&8\u00bb &cNie posiadasz zadnych case'ow 6/1/1!");
                    return;
                }
                p.sendMessage("&aOdebrales :"+u.getCase611()+" Case'ow 6/1/1");
                DajUtil.giveWithAmount("case611", u.getCase611(), p);
                u.setCase611(0);
                u.save();
                OdbierzMenu.show(p);
                return;
            }
            return;
        }
        if (e.getInventory().getName().contains("Profil: ")) {
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                User x = UserManager.getUser(p);
                String profilclear = "";
                switch (e.getRawSlot()) {
                    case 0:
                        profilclear = "statystyki";
                        x.setPoints(1000);
                        x.setKills(0);
                        x.setDeaths(0);
                        x.setAsysty(0);
                        x.setCoins(0);
                        ProfilMenu.show(p);
                        break;
                    case 1:
                        profilclear = "turbo";
                        x.setTurboDrop(0);
                        ProfilMenu.show(p);
                        break;
                    case 2:
                        profilclear = "schowek";
                        x.setKoxy(0);
                        x.setStrzaly(0);
                        x.setPerly(0);
                        x.setRefile(0);
                        ProfilMenu.show(p);
                        break;
                    default:
                        break;
                }
                String name = e.getInventory().getName().replace("Profil: ", "");
                TagUtil.updateBoard(p);
                p.sendMessage("&fWyczysciles &d"+profilclear+ " &fgracz &d"+name);
            }
            return;
        }
        if (e.getInventory().getName().contains("Grupa dla: ")) {
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                String groupToGive = "Gracz";
                switch (e.getRawSlot()) {
                    case 1:
                        groupToGive = "VIP";
                        break;
                    case 2:
                        groupToGive = "SVIP";
                        break;
                    case 3:
                        groupToGive = "EASY";
                        break;
                    case 4:
                        groupToGive = "HELPER";
                        break;
                    case 5:
                        groupToGive = "MOD";
                        break;
                    case 6:
                        groupToGive = "ADMIN";
                        break;
                    case 7:
                        groupToGive = "H@";
                        break;
                    default:
                        break;
                }

                String name = e.getInventory().getName().replace("Grupa dla: ", "");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + name + " group set " + groupToGive);
                p.sendMessage("&fNadales grupe &d"+groupToGive+ " &fdla gracz &d"+name);
                TagUtil.updateBoard(p);
            }
            return;
        }
        if (p.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lSprawdzanie"))) {
            e.setCancelled(true);
            if (e.getSlot() == 11) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"ban "+p.getName() + " 1d");
                return;
            }
            if (e.getSlot() == 15) {
                u.setInBeingChecked(false);
                p.closeInventory();
                return;
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lDisco"))) {
            e.setCancelled(true);
            if (e.getSlot() == 10) {
                DataManager.getDisco().put(p.getName(), ArmorType.GRAY);
                DataManager.getLastColor().put(p.getName(), Color.fromRGB(255, 0, 0));
                p.sendMessage("&6Zmieniono tryb disco zbroi na GRAY");
                p.closeInventory();
                return;
            }
            if (e.getSlot() == 12) {
                DataManager.getDisco().put(p.getName(), ArmorType.RANDOM);
                p.sendMessage("&6Zmieniono tryb disco zbroi na RANDOM");
                p.closeInventory();
                return;
            }
            if (e.getSlot() == 14) {
                DataManager.getDisco().put(p.getName(), ArmorType.SMOOTH);
                DataManager.getLastColor().put(p.getName(), Color.fromRGB(255, 0, 0));
                p.sendMessage("&6Zmieniono tryb disco zbroi na SMOOTH");
                p.closeInventory();
                return;
            }
            if (e.getSlot() == 16) {
                DataManager.getDisco().put(p.getName(), ArmorType.ULTRA);
                p.sendMessage("&6Zmieniono tryb disco zbroi na ULTRA");
                p.closeInventory();
                return;
            }
            if (e.getSlot() == 22) {
                DataManager.getDisco().remove(p.getName());
                DataManager.getLastColor().remove(p.getName());
                DataManager.getShiftArmor().remove(p.getName());
                p.sendMessage("&6Wylaczono disco zbroje");
                p.closeInventory();
                return;
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lPanel"))) {
            e.setCancelled(true);
            Guild g = GuildManager.getGuild(p);
            if (e.getSlot() == 10) {
                if (!g.isLeader(p.getName())) {
                    p.sendMessage("&cNie jestes zastepca gildii!");
                    return;
                }
                if (g.getProlong() + TimeUtil.DAY.getTime(guild.CUBOID_PROLONG_ADD) > System.currentTimeMillis() + TimeUtil.DAY.getTime(guild.CUBOID_PROLONG_MAX)) {
                    p.sendMessage("&cGildia jest przedluzona na maksymalny okres");
                    return;
                }
                ItemStack costRenew = guild.RENEW_COST;
                if(!p.getInventory().containsAtLeast(costRenew, costRenew.getAmount())){
                    p.sendMessage("&cNie posiadasz " + costRenew.getType() + "x" + costRenew.getAmount());
                    return;
                }
                p.getInventory().removeItem(guild.RENEW_COST);
                g.setProlong(g.getProlong() + TimeUtil.DAY.getTime(guild.CUBOID_PROLONG_ADD));
                p.sendMessage("&fPrzedluzylesz waznosc gildii o &d" + guild.CUBOID_PROLONG_ADD + " &fdni!");
                PanelMenu.show(p,g);
                return;
            }
            if (e.getSlot() == 11) {
                if (!g.isLeader(p.getName())) {
                    p.sendMessage("&cNie jestes liderem gildii!");
                    return;
                }
                if (g.getRegion().getSize() >= guild.CUBOID_SIZE_MAX) {
                    p.sendMessage("&cGildia posiada maksymalny rozmiar!");
                    return;
                }
                int mod = (g.getRegion().getSize() - guild.CUBOID_SIZE_START) / 5 + 1;
                int amount = guild.COST_POWIEKSZ.getAmount() * mod;
                if(!p.getInventory().containsAtLeast(guild.COST_POWIEKSZ,amount)) {
                    p.sendMessage("&cNie Posiadasz x" + guild.COST_POWIEKSZ.getAmount()+"x"+ guild.COST_POWIEKSZ.getAmount());
                    return;
                }
                p.getInventory().removeItem(new ItemStack(guild.COST_POWIEKSZ.getType(),amount));
                g.addSize(guild.CUBOID_SIZE_ADD);
                int size = g.getRegion().getSize() * 2 + 1;
                p.sendMessage("&fPowiekszyles rozmiar gildii do &d" + size + "&7x&d" + size);
                PanelMenu.show(p,g);
                return;
            }
            if (e.getSlot() == 12) {
                if (!g.getRegen().contains("!")) {
                    p.sendMessage("&cGildia nie posiada zadnych blokow do regeneracji!");
                    return;
                }
                if (TNTUtil.isBetween()) {
                    p.sendMessage("&cGildie mozesz regenerowac gdy TNT jest wylaczone");
                    return;
                }
                if (g.getRegen().isEmpty()) {
                    p.sendMessage("&cTwoja gildia jest wlasnie regenerowana");
                    return;
                }
                g.setBlocksToRegen(g.getGold() * 10);
                GuildRegenerationTask.regen(g);
                PanelMenu.show(p,g);
                return;
            }
            if (e.getSlot() == 31) {
                int goldBlocks = 0;
                for (ItemStack content : p.getInventory().getContents()) {
                    if(content != null && content.getType() != Material.AIR) {
                        if(content.getType() == Material.GOLD_BLOCK) {
                            goldBlocks += content.getAmount();
                        }
                    }
                }

                if(goldBlocks == 0) {
                    p.sendMessage("&ccos poszlo nie tak");
                    return;
                }

                g.setGold(g.getGold() + goldBlocks);
                g.saveGold(g.getGold());
                p.getInventory().remove(Material.GOLD_BLOCK);
                p.sendMessage("&aWplaciles " + goldBlocks + " blokow zlota");
                PanelMenu.show(p,g);
                return;
            }
            if (e.getSlot() == 13){
                ItemStack costWither = guild.COST_WITHER;
                if (!p.getInventory().containsAtLeast(costWither, costWither.getAmount())) {
                    p.sendMessage("&cNie posiadasz "+costWither.getType()+"x"+costWither.getAmount());
                    return;
                }
                Guild gl = GuildManager.getGuild(p.getLocation());
                if (gl == null) {
                    p.sendMessage("&cWithera mozesz zrespic tylko na terenie gildii!");
                    return;
                }
                if (gl != gl) {
                    p.sendMessage("&cWithera mozesz zrepisc na terenie gildii swojej!");
                    return;
                }
                p.getInventory().removeItem(guild.COST_WITHER);
                BossUtil.spawnWither(p.getLocation(), "&e~ WITHER ~", 1200);
                Bukkit.broadcastMessage("&fGildia &d"+g.getTag()+ "&f- &d"+g.getName()+ " &fprzywolala witchera! Gratulacje");
                return;
            }
            if (e.getSlot() == 14){
                if (!statues.MANAGE_PANEL) {
                    p.sendMessage("&cAktualnie kupowanie hp jest wylaczone!");
                    return;
                }
                if (g == null) {
                    p.sendMessage("&cNie posiadasz gidlii!");
                    return;
                }
                if (g.getHp() > 99) {
                    p.sendMessage("&cGildia posiada maksymalna ilosc hp!");
                    return;
                }
                ItemStack costHp = guild.COST_HP;
                if(!p.getInventory().containsAtLeast(costHp, costHp.getAmount())){
                    p.sendMessage("&cNie posiadasz " + costHp.getType() + "x" + costHp.getAmount());
                    return;
                }
                p.getInventory().removeItem(guild.COST_HP);
                g.setHp(50);
                Bukkit.broadcastMessage("&7[&c" + g.getTag() + "&7] &c" + g.getName() + " &6zakupila odnowienie &chp");
                p.sendMessage("&aOdnowiles hp gildyjne");
                PanelMenu.show(p,g);
                return;
            }
            if (e.getSlot() == 15){
                ItemStack costLimit = guild.COST_LIMIT;
                if(!p.getInventory().containsAtLeast(costLimit, costLimit.getAmount())){
                    p.sendMessage("&cNie posiadasz " + costLimit.getType() + "x" + costLimit.getAmount());
                    return;
                }
                if (!g.isLeader(p.getName())) {
                    p.sendMessage("&cNie jestes liderem!");
                    return;
                }
                if (g.getPlayersLimit() > 30) {
                    p.sendMessage("&cGildia posiada maksymalny limit czlonkow!");
                    return;
                }
                g.addPlayersLimit();
                p.getInventory().removeItem(guild.COST_LIMIT);
                p.sendMessage("&6Zwiekszono limit czlonkow do &c" + g.getPlayersLimit());
                Bukkit.broadcastMessage("&7[&c" + g.getTag() + "&7] &c" + g.getName() + " &6zwiekszyla rozmiar czlonkow do &c" + g.getPlayersLimit());
                PanelMenu.show(p,g);
                return;
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lDrop z Case"))) {
            e.setCancelled(true);
            if (e.getSlot() == 53) {
                StoneMenu.menu((Player) e.getWhoClicked());
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lGamePlay"))) {
            e.setCancelled(true);
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lDrop z Easy6/1/1"))) {
            e.setCancelled(true);
            if (e.getSlot() == 8) {
                StoneMenu.menu((Player) e.getWhoClicked());
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color(Settings.inventoryName))) {
            e.setCancelled(true);
            return;
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
                    p.sendMessage("&cNie posiadasz koxow do wyplacenia!");
                    return;
                }
                if (k1 >= statues.LIMIT_KOX) {
                    p.sendMessage("&cOsiagneles juz limit koxow!");
                    return;
                }
                if (u.getkoxy() <= statues.LIMIT_KOX) {
                    schowek_koxy = u.getkoxy();
                    u.removeKoxy(schowek_koxy);
                    ItemUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_koxy, (short) 1).build());
                    p.sendMessage("&7Wyplaciles &6" + schowek_koxy + " koxy");
                    SchowekMenu.show(p);
                    return;
                }
                if (k1 < statues.LIMIT_KOX) {
                    int koxy= k1 - statues.LIMIT_KOX;
                    schowek_koxy = koxy * -1;
                    u.removeKoxy(schowek_koxy);
                    ItemUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_koxy, (short) 1).build());
                    p.sendMessage("&7Wyplaciles &6" + schowek_koxy + " koxy");
                    SchowekMenu.show(p);
                    return;
                }
            }
            if (slot == 11) {
                if (u.getRefile() <= 0) {
                    p.sendMessage("&cNie posiadasz refow do wyplacenia!");
                    return;
                }
                if (k2 >= statues.LIMIT_REFILE) {
                    p.sendMessage("&cOsiagneles juz limit refili!");
                    return;
                }
                if (u.getRefile() <= statues.LIMIT_REFILE) {
                    schowek_refile = u.getRefile();
                    u.removeRefile(schowek_refile);
                    ItemUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_refile, (short) 0).build());
                    p.sendMessage("&7Wyplaciles &6" + schowek_refile + " refile");
                    SchowekMenu.show(p);
                    return;
                }
                if (k2 < statues.LIMIT_REFILE) {
                    int ref = k2 - statues.LIMIT_REFILE;
                    schowek_refile = ref * -1;
                    u.removeRefile(schowek_refile);
                    ItemUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_refile, (short) 0).build());
                    p.sendMessage("&7Wyplaciles &6" + schowek_refile + " refile");
                    SchowekMenu.show(p);
                    return;
                }
            }
            if (slot == 12) {
                if (u.getPerly() <= 0) {
                    p.sendMessage("&cNie posiadasz perel do wyplacenia!");
                    return;
                }
                if (k3 >= statues.LIMIT_PEARL) {
                    p.sendMessage("&cOsiagneles juz limit perel!");
                    return;
                }
                if (u.getPerly() <= statues.LIMIT_PEARL) {
                    schowek_perly = u.getPerly();
                    u.removePerly(schowek_perly);
                    ItemUtil.giveItems(p, new ItemBuilder(Material.ENDER_PEARL, schowek_perly, (short) 0).build());
                    p.sendMessage("&7Wyplaciles &6" + schowek_perly + " perly");
                    SchowekMenu.show(p);
                    return;
                }
                if (k3 < statues.LIMIT_PEARL) {
                    int ref = k3 - statues.LIMIT_PEARL;
                    schowek_perly = ref * -1;
                    u.removePerly(schowek_perly);
                    ItemUtil.giveItems(p, new ItemBuilder(Material.ENDER_PEARL, schowek_perly, (short) 0).build());
                    p.sendMessage("&7Wyplaciles &6" + schowek_perly + " perly");
                    SchowekMenu.show(p);
                    return;
                }
                return;
            }
            if (slot == 13) {
                if (u.getStrzaly() <= 0) {
                    p.sendMessage("&cNie posiadasz strzal do wyplacenia!");
                    return;
                }
                if (k4 >= statues.LIMIT_STRZAL) {
                    p.sendMessage("&cOsiagneles juz limit strzal!");
                    return;
                }
                if (u.getStrzaly() <= statues.LIMIT_STRZAL) {
                    schowek_strzaly = u.getStrzaly();
                    u.removeStrzaly(schowek_strzaly);
                    ItemUtil.giveItems(p, new ItemBuilder(Material.ARROW, schowek_strzaly, (short) 0).build());
                    p.sendMessage("&7Wyplaciles &6" + schowek_strzaly + " strzal");
                    SchowekMenu.show(p);
                    return;
                }
                if (k4 < statues.LIMIT_STRZAL) {
                    int strzaly = k4 - statues.LIMIT_STRZAL;
                    schowek_strzaly = strzaly * -1;
                    u.removeStrzaly(schowek_strzaly);
                    ItemUtil.giveItems(p, new ItemBuilder(Material.ARROW, schowek_strzaly, (short) 0).build());
                    p.sendMessage("&7Wyplaciles &6" + schowek_strzaly + " strzal");
                    SchowekMenu.show(p);
                    return;
                }
            }
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatUtil.color("&7&lManage"))) {
            e.setCancelled(true);
            if(e.getSlot()==0){
                statues.MANAGE_GUILDCREATE = !statues.MANAGE_GUILDCREATE;
                statues.saveLang();
                ManageMenu.openMenu(p);
                Bukkit.broadcastMessage("&6Gildie zostaly "+(statues.MANAGE_GUILDCREATE ? "&aWlaczone":"&cWylaczone"));
                return;
            }
            if(e.getSlot()==1){
                statues.MANAGE_KIT = !statues.MANAGE_KIT;
                statues.saveLang();
                ManageMenu.openMenu(p);
                Bukkit.broadcastMessage("&6Kity zostaly "+(statues.MANAGE_KIT ? "&aWlaczone":"&cWylaczone"));
                return;
            }
            if(e.getSlot()==2){
                statues.MANAGE_DIAMOND = !statues.MANAGE_DIAMOND;
                statues.saveLang();
                ManageMenu.openMenu(p);
                Bukkit.broadcastMessage("&6Diamentowe Itemy zostaly "+(statues.MANAGE_DIAMOND ? "&aWlaczone":"&cWylaczone"));
                return;
            }
            if(e.getSlot()==3){
                statues.MANAGE_PANEL = !statues.MANAGE_PANEL;
                statues.saveLang();
                ManageMenu.openMenu(p);
                Bukkit.broadcastMessage("&6Panel Gildyjny zostal "+(statues.MANAGE_PANEL ? "&aWlaczony":"&cWylaczony"));
                return;
            }
            if(e.getSlot()==4){
                statues.MANAGE_SHOP = !statues.MANAGE_SHOP;
                statues.saveLang();
                ManageMenu.openMenu(p);
                Bukkit.broadcastMessage("&6Sklep zostal "+(statues.MANAGE_SHOP ? "&aWlaczony":"&cWylaczony"));
                return;
            }
            if(e.getSlot()==5){
                statues.MANAGE_BEACON = !statues.MANAGE_BEACON;
                statues.saveLang();
                ManageMenu.openMenu(p);
                Bukkit.broadcastMessage("&6Beacony zostaly "+(statues.MANAGE_BEACON ? "&aWlaczone":"&cWylaczone"));
                return;
            }
            if(e.getSlot()==6){
                statues.MANAGE_DROPHEAD = !statues.MANAGE_DROPHEAD;
                statues.saveLang();
                ManageMenu.openMenu(p);
                Bukkit.broadcastMessage("&6Drop glow z graczy zostal "+(statues.MANAGE_DROPHEAD ? "&aWlaczony":"&cWylaczony"));
                return;
            }
            if(e.getSlot()==7){
                statues.MANAGE_TPA = !statues.MANAGE_TPA;
                statues.saveLang();
                ManageMenu.openMenu(p);
                Bukkit.broadcastMessage("&6Tpa zostalo "+(statues.MANAGE_TPA ? "&aWlaczone":"&cWylaczone"));
                return;
            }
            if(e.getSlot()==8){
                statues.MANAGE_SPAWN = !statues.MANAGE_SPAWN;
                statues.saveLang();
                ManageMenu.openMenu(p);
                Bukkit.broadcastMessage("&6Teleportowanie na spawna zostalo "+(statues.MANAGE_SPAWN ? "&aWlaczone":"&cWylaczone"));
                return;
            }
            if(e.getSlot()==9){
                statues.MANAGE_ENCHANT = !statues.MANAGE_ENCHANT;
                statues.saveLang();
                ManageMenu.openMenu(p);
                Bukkit.broadcastMessage("&6Enchantowanie przedmiotow zostalo "+(statues.MANAGE_ENCHANT ? "&aWlaczone":"&cWylaczone"));
                return;
            }
            return;
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
                p.sendMessage("&cNie posiadasz koxow do wyplacenia!");
                return;
            }
            if (k1 >= statues.LIMIT_KOX) {
                p.sendMessage("&cOsiagneles juz limit koxow!");
                return;
            }
            if (u.getkoxy() <= statues.LIMIT_KOX) {
                schowek_koxy = u.getkoxy();
                u.removeKoxy(schowek_koxy);
                ItemUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_koxy, (short) 1).build());
                p.sendMessage("&7Wyplaciles &6" + schowek_koxy + " koxy");
                SchowekMenu.show(p);
                return;
            }
            if (k1 < statues.LIMIT_KOX) {
                int ref = k1 - statues.LIMIT_KOX;
                schowek_koxy = ref * -1;
                u.removeKoxy(schowek_koxy);
                ItemUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_koxy, (short) 1).build());
                p.sendMessage("&7Wyplaciles &6" + schowek_koxy + " koxy");
                SchowekMenu.show(p);
            }
        }
        public static void ref(User u, int k2) {
            Player p = u.getPlayer();
            int schowek_strzaly;
            if (u.getRefile() <= 0) {
                p.sendMessage("&cNie posiadasz refow do wyplacenia!");
                return;
            }
            if (k2 >= statues.LIMIT_REFILE) {
                p.sendMessage("&cOsiagneles juz limit refili!");
                return;
            }
            if (u.getRefile() <= statues.LIMIT_REFILE) {
                schowek_strzaly = u.getRefile();
                u.removeRefile(schowek_strzaly);
                ItemUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_strzaly, (short) 0).build());
                p.sendMessage("&7Wyplaciles &6" + schowek_strzaly + " refile");
                SchowekMenu.show(p);
                return;
            }
            if (k2 < statues.LIMIT_REFILE) {
                int ref = k2 - statues.LIMIT_REFILE;
                schowek_strzaly = ref * -1;
                u.removeRefile(schowek_strzaly);
                ItemUtil.giveItems(p, new ItemBuilder(Material.GOLDEN_APPLE, schowek_strzaly, (short) 0).build());
                p.sendMessage("&7Wyplaciles &6" + schowek_strzaly + " refile");
                SchowekMenu.show(p);
            }
        }
        public static void pearl(User u, int k3) {
            Player p = u.getPlayer();
            int schowek_perly;
            if (u.getPerly() <= 0) {
                p.sendMessage("&cNie posiadasz perel do wyplacenia!");
                return;
            }
            if (k3 >= statues.LIMIT_PEARL) {
                p.sendMessage("&cOsiagneles juz limit perel!");
                return;
            }
            if (u.getPerly() <= statues.LIMIT_PEARL) {
                schowek_perly = u.getPerly();
                u.removePerly(schowek_perly);
                ItemUtil.giveItems(p, new ItemBuilder(Material.ENDER_PEARL, schowek_perly, (short) 0).build());
                p.sendMessage("&7Wyplaciles &6" + schowek_perly + " perly");
                SchowekMenu.show(p);
                return;
            }
            if (k3 < statues.LIMIT_PEARL) {
                int ref = k3 - statues.LIMIT_PEARL;
                schowek_perly = ref * -1;
                u.removePerly(schowek_perly);
                ItemUtil.giveItems(p, new ItemBuilder(Material.ENDER_PEARL, schowek_perly, (short) 0).build());
                p.sendMessage("&7Wyplaciles &6" + schowek_perly + " perly");
                SchowekMenu.show(p);
            }
        }

        public static void strzalyw(User u, int k4) {
            Player p = u.getPlayer();
            int schowek_strzaly;
            if (u.getStrzaly() <= 0) {
                p.sendMessage("&cNie posiadasz strzal do wyplacenia!");
                return;
            }
            if (k4 >= statues.LIMIT_STRZAL) {
                p.sendMessage("&cOsiagneles juz limit strzal!");
                return;
            }
            if (u.getStrzaly() <= statues.LIMIT_STRZAL) {
                schowek_strzaly = u.getStrzaly();
                u.removeStrzaly(schowek_strzaly);
                ItemUtil.giveItems(p, new ItemBuilder(Material.ARROW, schowek_strzaly, (short) 0).build());
                p.sendMessage("&7Wyplaciles &6" + schowek_strzaly + " perly");
                SchowekMenu.show(p);
                return;
            }
            if (k4 < statues.LIMIT_STRZAL) {
                int ref = k4 - statues.LIMIT_STRZAL;
                schowek_strzaly = ref * -1;
                u.removeStrzaly(schowek_strzaly);
                ItemUtil.giveItems(p, new ItemBuilder(Material.ARROW, schowek_strzaly, (short) 0).build());
                p.sendMessage("&7Wyplaciles &6" + schowek_strzaly + " strzal");
                SchowekMenu.show(p);
            }
        }
    }

    @EventHandler
    public void d(InventoryCloseEvent event) {
        if (event.getInventory().getName().equals(ChatUtil.color("&7&lSprawdzanie"))) {
            Player player = (Player) event.getPlayer();
            User user = UserManager.getUser(player);
            if (!user.isInBeingChecked())
                return;
            SpigotPlugin.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(SpigotPlugin.getPlugin(), () -> {
                player.openInventory(event.getInventory());
            }, 2);
        }
    }
}
