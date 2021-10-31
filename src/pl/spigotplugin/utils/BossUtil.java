package pl.spigotplugin.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class BossUtil {
    public static void spawnBoss(Location location, String name, double health) {
        EnderDragon dragon = location.getWorld().spawn(location, EnderDragon.class);
        dragon.setCustomNameVisible(true);
        dragon.setCustomName(ChatUtil.color(name));
        dragon.setMaxHealth(health);
        dragon.setHealth(health);
    }
    public static List<ItemStack> easycase = Arrays.asList(new ItemStack(Material.DIRT, 64),
            new ItemStack(Material.ANVIL, 8),
            new ItemStack(Material.GOLD_INGOT, 16),
            new ItemStack(Material.GOLDEN_APPLE, 16, (short) 1),
            new ItemStack(Material.GOLDEN_APPLE, 16, (short) 0),
            new ItemStack(Material.GOLD_INGOT, 64),
            new ItemStack(Material.BOOKSHELF, 16),
            new ItemStack(Material.TNT, 16),
            new ItemStack(Material.ENDER_PEARL, 8),
            new ItemBuilder(Material.DIAMOND_PICKAXE,1).addEnchantment(Enchantment.DIG_SPEED,3).addEnchantment(Enchantment.DURABILITY,2).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,2).build(),
            new ItemBuilder(Material.DIAMOND_SWORD,1).addEnchantment(Enchantment.DAMAGE_ALL,4).build(),
            new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).addEnchantment(Enchantment.DURABILITY,2).build(),
            new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).addEnchantment(Enchantment.DURABILITY,2).build(),
            new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).addEnchantment(Enchantment.DURABILITY,2).build(),
            new ItemBuilder(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).addEnchantment(Enchantment.DURABILITY,2).build(),
            new ItemBuilder(Material.DIAMOND_PICKAXE,1).addEnchantment(Enchantment.DIG_SPEED,5).addEnchantment(Enchantment.DURABILITY,3).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,3).build(),
            new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.KNOCKBACK,2).build(),
            new ItemStack(Material.BEACON, 1));

    public static List<ItemStack> drops = Arrays.asList(new ItemStack(Material.GOLDEN_APPLE, 2, (short) 1),
            new ItemStack(Material.ARROW, 8),
            new ItemStack(Material.TNT, 20),
            new ItemStack(Material.ANVIL, 4),
            new ItemStack(Material.GOLD_BLOCK, 4),
            new ItemStack(Material.GOLDEN_APPLE, 6, (short) 0),
            new ItemStack(Material.ENDER_PEARL, 2));
}
