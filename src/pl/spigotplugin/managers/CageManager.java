package pl.spigotplugin.managers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class CageManager {
    private static List<Player> list = new ArrayList<>();

    public static List<Player> getList() {
        return CageManager.list;
    }

    public static void prepareInventory(type type, PlayerInventory inventory) {
        switch (type) {
            case DIAMOND: {
                inventory.clear();
                inventory.setArmorContents(null);
                ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                ItemMeta helmetMeta = helmet.getItemMeta();
                helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                helmet.setItemMeta(helmetMeta);
                inventory.setHelmet(helmet);
                ItemStack helmeta = new ItemStack(Material.DIAMOND_CHESTPLATE);
                ItemMeta helmetaMeta = helmeta.getItemMeta();
                helmetaMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                helmeta.setItemMeta(helmetaMeta);
                inventory.setChestplate(helmeta);
                ItemStack helmetaa = new ItemStack(Material.DIAMOND_LEGGINGS);
                ItemMeta helmetaaMeta = helmetaa.getItemMeta();
                helmetaaMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                helmetaa.setItemMeta(helmetaaMeta);
                inventory.setLeggings(helmetaa);
                ItemStack helmetaaa = new ItemStack(Material.DIAMOND_BOOTS);
                ItemMeta helmetaaaMeta = helmetaaa.getItemMeta();
                helmetaaaMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                helmetaaa.setItemMeta(helmetaaaMeta);
                inventory.setBoots(helmetaaa);
                inventory.setHeldItemSlot(0);
                ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta swordMeta = sword.getItemMeta();
                swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
                swordMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
                sword.setItemMeta(swordMeta);
                inventory.addItem(sword, new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1), new ItemStack(Material.GOLDEN_APPLE, 10), new ItemStack(Material.COOKED_BEEF, 64));
                break;
            }
            case IRON: {
                inventory.clear();
                inventory.setArmorContents(null);
                ItemStack helmet = new ItemStack(Material.IRON_HELMET);
                ItemMeta helmetMeta = helmet.getItemMeta();
                helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                helmet.setItemMeta(helmetMeta);
                inventory.setHelmet(helmet);
                ItemStack helmeta = new ItemStack(Material.IRON_CHESTPLATE);
                ItemMeta helmetaMeta = helmeta.getItemMeta();
                helmetaMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                helmeta.setItemMeta(helmetaMeta);
                inventory.setChestplate(helmeta);
                ItemStack helmetaa = new ItemStack(Material.IRON_LEGGINGS);
                ItemMeta helmetaaMeta = helmetaa.getItemMeta();
                helmetaaMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                helmetaa.setItemMeta(helmetaaMeta);
                inventory.setLeggings(helmetaa);
                ItemStack helmetaaa = new ItemStack(Material.IRON_BOOTS);
                ItemMeta helmetaaaMeta = helmetaaa.getItemMeta();
                helmetaaaMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                helmetaaa.setItemMeta(helmetaaaMeta);
                inventory.setBoots(helmetaaa);
                inventory.setHeldItemSlot(0);
                ItemStack sword = new ItemStack(Material.IRON_SWORD);
                ItemMeta swordMeta = sword.getItemMeta();
                swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
                swordMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
                sword.setItemMeta(swordMeta);
                inventory.addItem(sword, new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1), new ItemStack(Material.GOLDEN_APPLE, 10), new ItemStack(Material.COOKED_BEEF, 64));
                break;
            }
        }
    }

    public static void setup(Player player) {
        player.setHealth(20.0);
        player.setFoodLevel(20);
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public enum type {
        DIAMOND,
        IRON
    }
}
