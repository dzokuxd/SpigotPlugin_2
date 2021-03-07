package pl.spigotplugin.settings;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.utils.ItemUtil;

import java.util.Arrays;
import java.util.List;

public class Settings {
    public static ItemStack cobblexItem = ItemUtil.getDefaultCobbleXItem();
    public static String inventoryName = "&d&lDrop z cobblex";

    public static List<ItemStack> normalDropList = Arrays.asList(
            new ItemStack(Material.DIRT, 2),
            new ItemStack(Material.COBBLESTONE, 4)
    );

    public static List<ItemStack> premiumDropList = Arrays.asList(
            new ItemStack(Material.GOLDEN_APPLE, 3, (short) 1),
            new ItemStack(Material.GOLD_BLOCK, 7)
    );
}

