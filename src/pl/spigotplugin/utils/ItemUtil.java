package pl.spigotplugin.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
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
    public static boolean checkItems(Player p, String it, int mod) {
        List<ItemStack> items = ItemUtil.getItems(it, mod);
        for (ItemStack is : items) {
            ItemStack item = new ItemStack(Material.getMaterial(is.getType().getId()), is.getAmount(), is.getData().getData());
            if (!p.getInventory().containsAtLeast(item, item.getAmount())) {
                return false;
            }
        }
        return true;
    }
    public static String getItem(Player p, String it, int mod) {
        List<ItemStack> items = ItemUtil.getItems(it, mod);
        p.sendMessage("&6Brakuje ci:");
        for (ItemStack is : items) {
            int id = is.getType().getId();
            int data = is.getData().getData();
            int amount = is.getAmount();
            int ii = ItemUtil.getItemAmount(Material.getMaterial(id), p, (short) data);
            p.sendMessage((color(ii, amount) + "\u00bb" + is.getItemMeta().getDisplayName() + " " + ii + "/" + amount + " - " + ((double) ii / amount * 100.0 + "%") + "\n"));
        }
        return null;
    }
    private static String color(int i, int i2) {
        if (i >= i2) {
            return ChatUtil.color("&c");
        } else {
            return ChatUtil.color("&a");
        }
    }
    public static List<ItemStack> getItems(String string, int modifier) {
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (String s : string.split(";")) {
            String[] split = s.split("-");
            int id = Integer.parseInt(split[0].split(":")[0]);
            int data = Integer.parseInt(split[0].split(":")[1]);
            int amount = Integer.parseInt(split[1].split(":")[0]) * modifier;
            String name = split[1].split(":")[1];
            ItemStack is = new ItemStack(Material.getMaterial(id), amount, (short) data);
            ItemMeta meta = is.getItemMeta();
            meta.setDisplayName(name);
            is.setItemMeta(meta);
            items.add(is);
        }
        return items;
    }
    public static void removeItems(Player p, String it, int mod) {
        List<ItemStack> items = getItems(it, mod);
        for (ItemStack is : items) {
            ItemStack item = new ItemStack(Material.getMaterial(is.getType().getId()), is.getAmount(), is.getData().getData());
            if (p.getInventory().containsAtLeast(item, item.getAmount())) {
                p.getInventory().removeItem(item);
            }
        }
    }
    public static int getItemAmount(Material material, Player player, short durability) {
        int amount = 0;
        ItemStack[] contents;
        for (int length = (contents = player.getInventory().getContents()).length, i = 0; i < length; ++i) {
            ItemStack itemStack = contents[i];
            if (itemStack != null && itemStack.getType().equals(material) && itemStack.getDurability() == durability) {
                amount += itemStack.getAmount();
            }
        }
        return amount;
    }
}

