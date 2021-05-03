package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class GameplayMenu {
    public static void show(Player p){
        Inventory inv = Bukkit.createInventory(p, 54, ChatUtil.color("&7&lGamePlay"));
        ItemBuilder air = new ItemBuilder(Material.getMaterial(160), 1, (short) 15).setTitle("&8\u2022");
        for (int j = 0; j < 54; j++) {
            inv.setItem(j, air.build());
        }
        ItemStack tnt = new ItemBuilder(
                Material.TNT)
                .setTitle("&cTNT &7dziala w godzinach &c12 &7- &c22")
                .addLore("&7Od poziomu &c60 &7w dol")
                .build();
        ItemStack strzaly = new ItemBuilder(
                Material.ARROW, Config.LIMIT_STRZAL)
                .setTitle("&7Limit: &c"+Config.LIMIT_STRZAL)
                .addLore("")
                .build();
        ItemStack border = new ItemBuilder(
                Material.BARRIER)
                .setTitle("&cBorder:")
                .addLore("&7Swiat: &c"+ Config.BORDER_WORLD)
                .addLore("&7GrupoweTP: &c"+Config.BORDER_GTP)
                .build();
        ItemStack luk = new ItemBuilder(
                Material.BOW)
                .setTitle("&7LUK &c4/1/1")
                .addEnchantment(Enchantment.ARROW_DAMAGE, 4)
                .addEnchantment(Enchantment.ARROW_FIRE, 1)
                .addEnchantment(Enchantment.DURABILITY, 1)
                .build();
        ItemStack miecz = new ItemBuilder(
                Material.DIAMOND_SWORD)
                .setTitle("&7MIECZ &c4/3/1")
                .addEnchantment(Enchantment.DAMAGE_ALL, 4)
                .addEnchantment(Enchantment.FIRE_ASPECT, 1)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build();
        ItemStack knock = new ItemBuilder(
                Material.DIAMOND_SWORD)
                .setTitle("&7&lKNOCK &c2")
                .addEnchantment(Enchantment.KNOCKBACK,2)
                .build();
        ItemStack gapple = new ItemBuilder(
                Material.GOLDEN_APPLE, Config.LIMIT_KOX, (short) 1)
                .setTitle("&7Limit: &c"+Config.LIMIT_KOX)
                .build();
        ItemStack refil = new ItemBuilder(
                Material.GOLDEN_APPLE, Config.LIMIT_REFILE, (short) 0)
                .setTitle("&7Limit: &c"+Config.LIMIT_REFILE)
                .build();
        ItemStack perly = new ItemBuilder(
                Material.ENDER_PEARL, Config.LIMIT_PEARL)
                .setTitle("&7Limit: &c"+Config.LIMIT_PEARL)
                .build();
        ItemStack helm = new ItemBuilder(
                Material.DIAMOND_HELMET)
                .setTitle("&7HELM &c3/2")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .addEnchantment(Enchantment.DURABILITY, 2)
                .build();
        ItemStack klata = new ItemBuilder(
                Material.DIAMOND_CHESTPLATE)
                .setTitle("&7KLATA &c3/2")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .addEnchantment(Enchantment.DURABILITY, 2)
                .build();
        ItemStack spodnie = new ItemBuilder(
                Material.DIAMOND_LEGGINGS)
                .setTitle("&7SPODNIE &c3/2")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .addEnchantment(Enchantment.DURABILITY, 2)
                .build();
        ItemStack buty = new ItemBuilder(
                Material.DIAMOND_BOOTS)
                .setTitle("&7BUTY &c3/2")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .addEnchantment(Enchantment.PROTECTION_FALL, 2)
                .build();
        inv.setItem(10, tnt);
        inv.setItem(16, strzaly);
        inv.setItem(19, border);
        inv.setItem(21, knock);
        inv.setItem(23, miecz);
        inv.setItem(25, gapple);
        inv.setItem(30, luk);
        inv.setItem(34, refil);
        inv.setItem(43, perly);
        inv.setItem(13, helm);
        inv.setItem(22, klata);
        inv.setItem(31, spodnie);
        inv.setItem(40, buty);
        p.openInventory(inv);
    }
}
