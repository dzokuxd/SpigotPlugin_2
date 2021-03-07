package pl.spigotplugin.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ItemUtil {
    private static Random random = new Random();

    public static ItemStack getDefaultCobbleXItem() {
        ItemStack item = new ItemStack(Material.COBBLESTONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.color("&5cobblex"));
        meta.setLore(Arrays.asList("poloz na ziemi", "abyt otrzymac item"));
        item.setItemMeta(meta);
        return item;
    }

    public static void giveDrop(Location location, List<ItemStack> dropList) {
        ItemStack item = dropList.get(random.nextInt(dropList.size()));
        location.getWorld().dropItemNaturally(location, item);
    }

    public static int getamount(Material material, Player player, short durability) {
        int amount = 0;
        ItemStack[] contents;
        int length = (contents = player.getInventory().getContents()).length, i = 0;
        while (i < length) {
            ItemStack itemStack = contents[i];
            if (itemStack != null && itemStack.getType().equals(material) && itemStack.getDurability() == durability) {
                amount += itemStack.getAmount();
            }
            ++i;
        }
        return amount;
    }
}

