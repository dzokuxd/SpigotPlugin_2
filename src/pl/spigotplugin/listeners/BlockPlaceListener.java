package pl.spigotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.settings.Settings;
import pl.spigotplugin.utils.*;

import java.util.List;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (e.getBlockPlaced().getType() == Material.BREWING_STAND) {
            p.sendMessage("&cAlchemia zostala zablokowana!");
            e.setCancelled(true);
            return;
        }
        if (CheckUtil.checkedPlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
            return;
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
            return;
        }
        if (p.hasPermission("regionplugin.bypass")) {
            return;
        }
        if (CuboidUtil.isOutsideSpawn(b.getLocation())) {
            e.setBuild(false);
            e.setCancelled(true);
            p.sendMessage("&cTa interakcja jest zablokowana!");
        }
    }
    @EventHandler
    private void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlockPlaced();
        if (p.getItemInHand().isSimilar(getCaseItem())) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            p.getInventory().removeItem(getCaseItem());
            if (Math.random() * 100.0 < 2.0) {
                ItemStack kilof = new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta meta = kilof.getItemMeta();
                meta.addEnchant(Enchantment.DIG_SPEED, 6, true);
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
                kilof.setItemMeta(meta);
                ChatUtil.giveItems(p,kilof);
                Bukkit.broadcastMessage("&6Gracz &c" + p.getName() + " &6otworzyl skrzynie &cEasy6/1/1 &6i wylosowal &c&lKilof 6/1/1");
            } else if (Math.random() * 100.0 < 49.0) {
                ItemStack dirt = new ItemStack(Material.DIRT);
                ChatUtil.giveItems(p,dirt);
            } else if (Math.random() * 100.0 < 49.0) {
                ItemStack gold = new ItemStack(Material.GOLD_INGOT);
                ChatUtil.giveItems(p,gold);
            }
        }
        if (p.getItemInHand().isSimilar(getCaseItem1())) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            p.getInventory().removeItem(getCaseItem1());
            if (Math.random() * 100.0 < 1.0) {
                ItemStack beacon = new ItemStack(Material.BEACON);
                ChatUtil.giveItems(p,beacon);
                Bukkit.broadcastMessage("&6Gracz &c" +p.getName()+ " &6otworzyl skrzynie &c&lSkrzynia "+ Config.IP+ " &6i wylosowal &c&lBeacona");
            } else if (Math.random() * 100.0 < 1.0) {
                ItemStack skull = new ItemStack(Material.SKULL_ITEM,1, (short) 3);
                ChatUtil.giveItems(p,skull);
                Bukkit.broadcastMessage("&6Gracz &c" +p.getName()+ " &6otworzyl skrzynie &c&lSkrzynia "+ Config.IP+ " &6i wylosowal &c&lZlota glowe");
            } else if (Math.random() * 100.0 < 1.0) {
                ItemStack knock = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta meta = knock.getItemMeta();
                meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
                knock.setItemMeta(meta);
                ChatUtil.giveItems(p,knock);
                Bukkit.broadcastMessage("&6Gracz &c" +p.getName()+ " &6otworzyl skrzynie &c&lSkrzynia "+ Config.IP+ " &6i wylosowal &c&lMiecz knock 2");
            } else if (Math.random() * 100.0 < 1.0) {
                ItemStack perly = new ItemStack(Material.ENDER_PEARL, 8);
                ChatUtil.giveItems(p,perly);
                Bukkit.broadcastMessage("&6Gracz &c" +p.getName()+ " &6otworzyl skrzynie &c&lSkrzynia "+ Config.IP+ " &6i wylosowal &c&lx8 perel");
            } else if (Math.random() * 100.0 < 5.0) {
                ItemStack tnt = new ItemStack(Material.TNT,16);
                ChatUtil.giveItems(p,tnt);
            } else if (Math.random() * 100.0 < 5.0) {
                ItemStack bookshelf = new ItemStack(Material.BOOKSHELF,16);
                ChatUtil.giveItems(p,bookshelf);
            } else if (Math.random() * 100.0 < 5.0) {
                ItemStack gold64 = new ItemStack(Material.GOLD_INGOT,64);
                ChatUtil.giveItems(p,gold64);
            } else if (Math.random() * 100.0 < 5.0) {
                ItemStack ref = new ItemStack(Material.GOLDEN_APPLE,16,(short) 0);
                ChatUtil.giveItems(p,ref);
            } else if (Math.random() * 100.0 < 5.0) {
                ItemStack kox = new ItemStack(Material.GOLDEN_APPLE,16,(short) 1);
                ChatUtil.giveItems(p,kox);
            } else if (Math.random() * 100.0 < 10.0) {
                ItemStack helm = new ItemStack(Material.DIAMOND_HELMET);
                ItemMeta meta = helm.getItemMeta();
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3, true);
                meta.addEnchant(Enchantment.DURABILITY,2, true);
                helm.setItemMeta(meta);
                ChatUtil.giveItems(p,helm);
            } else if (Math.random() * 100.0 < 10.0) {
                ItemStack klata = new ItemStack(Material.DIAMOND_CHESTPLATE);
                ItemMeta meta = klata.getItemMeta();
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3, true);
                meta.addEnchant(Enchantment.DURABILITY,2, true);
                klata.setItemMeta(meta);
                ChatUtil.giveItems(p,klata);
            } else if (Math.random() * 100.0 < 10.0) {
                ItemStack spodnie = new ItemStack(Material.DIAMOND_LEGGINGS);
                ItemMeta meta = spodnie.getItemMeta();
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3, true);
                meta.addEnchant(Enchantment.DURABILITY,2, true);
                spodnie.setItemMeta(meta);
                ChatUtil.giveItems(p,spodnie);
            } else if (Math.random() * 100.0 < 10.0) {
                ItemStack buty = new ItemStack(Material.DIAMOND_BOOTS);
                ItemMeta meta = buty.getItemMeta();
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3, true);
                meta.addEnchant(Enchantment.DURABILITY,2, true);
                buty.setItemMeta(meta);
                ChatUtil.giveItems(p,buty);
            } else if (Math.random() * 100.0 < 10.0) {
                ItemStack kilof5 = new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta meta = kilof5.getItemMeta();
                meta.addEnchant(Enchantment.DIG_SPEED,5, true);
                meta.addEnchant(Enchantment.DURABILITY,3, true);
                meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS,3, true);
                kilof5.setItemMeta(meta);
                ChatUtil.giveItems(p,kilof5);
            } else if (Math.random() * 100.0 < 20.0) {
                ItemStack kilof3 = new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta meta = kilof3.getItemMeta();
                meta.addEnchant(Enchantment.DIG_SPEED,3, true);
                meta.addEnchant(Enchantment.DURABILITY,2, true);
                meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS,2, true);
                kilof3.setItemMeta(meta);
                ChatUtil.giveItems(p,kilof3);
            } else if (Math.random() * 100.0 < 10.0) {
                ItemStack miecz = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta meta = miecz.getItemMeta();
                meta.addEnchant(Enchantment.DAMAGE_ALL,4, true);
                miecz.setItemMeta(meta);
                ChatUtil.giveItems(p,miecz);
            } else if (Math.random() * 100.0 < 20.0) {
                ItemStack gold16= new ItemStack(Material.GOLD_INGOT,16);
                ChatUtil.giveItems(p,gold16);
            } else if (Math.random() * 100.0 < 20.0) {
                ItemStack anvil = new ItemStack(Material.ANVIL,8);
                ChatUtil.giveItems(p,anvil);
            } else if (Math.random() * 100.0 < 20.0) {
                ItemStack dirt = new ItemStack(Material.DIRT,64);
                ChatUtil.giveItems(p,dirt);
            }
        }
    }
    private ItemStack getCaseItem() {
        ItemStack item = new ItemStack(Material.CHEST, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.color("&c&lSkrzynia Easy6/1/1"));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack getCaseItem1() {
        ItemStack item = new ItemStack(Material.CHEST, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.color("&c&lSkrzynia "+ Config.IP));
        item.setItemMeta(meta);
        return item;
    }
}
