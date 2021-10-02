package pl.spigotplugin.configs;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.utils.ItemUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Settings {
    public static ItemStack cobblexItem = ItemUtil.getDefaultCobbleXItem();
    public static String inventoryName = "&7&lDrop z CobbleX";

    public static List<Material> materialList=new ArrayList<>();
    public static boolean isContains(Material material){
        if(materialList.isEmpty()){
            return false;
        }
        return materialList.contains(material);
    }
    public static void loadMaterials(){
        SpigotPlugin.getPlugin().getConfig().getStringList("shadow-block.blocks").forEach(value->{
            try {
                materialList.add(Material.matchMaterial(value));
            }catch (Exception ex){
                System.out.println("Problem z materialem :="+value);
            }
        });
    }

    public static List<ItemStack> normalDropList = Arrays.asList(
            new ItemStack(Material.ENCHANTMENT_TABLE, 1),
            new ItemStack(Material.ENDER_CHEST, 1),
            new ItemStack(Material.HAY_BLOCK, 6),
            new ItemStack(Material.APPLE, 16),
            new ItemStack(Material.GOLDEN_APPLE, 1,(short) 0),
            new ItemStack(Material.STRING, 4),
            new ItemStack(Material.FEATHER, 8),
            new ItemStack(Material.IRON_INGOT, 16),
            new ItemStack(Material.EXP_BOTTLE, 32),
            new ItemStack(Material.CARROT_ITEM, 16),
            new ItemStack(Material.QUARTZ, 16),
            new ItemStack(Material.EMERALD, 16),
            new ItemStack(Material.ENDER_PEARL, 2),
            new ItemStack(Material.RAW_FISH, 10),
            new ItemStack(Material.SUGAR_CANE, 10),
            new ItemStack(Material.LEATHER, 16),
            new ItemStack(Material.SLIME_BALL, 4),
            new ItemStack(Material.ANVIL, 2),
            new ItemStack(Material.ARROW, 8),
            new ItemStack(Material.FLINT_AND_STEEL, 1),
            new ItemStack(Material.FLINT, 4),
            new ItemStack(Material.LAVA_BUCKET, 1),
            new ItemStack(Material.WATER_BUCKET, 1)
    );

    public static List<ItemStack> premiumDropList = Arrays.asList(
            new ItemStack(Material.ENCHANTMENT_TABLE, 1),
            new ItemStack(Material.ENDER_CHEST, 1),
            new ItemStack(Material.HAY_BLOCK, 6),
            new ItemStack(Material.APPLE, 16),
            new ItemStack(Material.GOLDEN_APPLE, 1,(short) 0),
            new ItemStack(Material.STRING, 4),
            new ItemStack(Material.FEATHER, 8),
            new ItemStack(Material.IRON_INGOT, 16),
            new ItemStack(Material.EXP_BOTTLE, 32),
            new ItemStack(Material.CARROT_ITEM, 16),
            new ItemStack(Material.QUARTZ, 16),
            new ItemStack(Material.EMERALD, 16),
            new ItemStack(Material.ENDER_PEARL, 2),
            new ItemStack(Material.RAW_FISH, 10),
            new ItemStack(Material.SUGAR_CANE, 10),
            new ItemStack(Material.LEATHER, 16),
            new ItemStack(Material.SLIME_BALL, 4),
            new ItemStack(Material.ANVIL, 2),
            new ItemStack(Material.ARROW, 8),
            new ItemStack(Material.FLINT_AND_STEEL, 1),
            new ItemStack(Material.FLINT, 4),
            new ItemStack(Material.LAVA_BUCKET, 1),
            new ItemStack(Material.WATER_BUCKET, 1),
            new ItemStack(Material.GOLDEN_APPLE, 1,(short) 1),
            new ItemStack(Material.PUMPKIN_PIE, 4),
            new ItemStack(Material.BOOK, 4)
    );
}

