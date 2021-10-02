package pl.spigotplugin.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemUtil {
    private static final Random random = new Random();

    public static ItemStack getDefaultCobbleXItem() {
        ItemStack item = new ItemStack(Material.MOSSY_COBBLESTONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.color("&7&lCobble&2&lX"));
        meta.setLore(Collections.singletonList(ChatUtil.color("&cPoloz na ziemi, aby otrzymac item")));
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack getGoldenHead() {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner("StackedGold");
        meta.setDisplayName(ChatUtil.color("&6Golden Head"));
        itemStack.setItemMeta(meta);
        return itemStack;
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

    public static int remove(ItemStack base, Player player, int amount) {
        int actual = 0;
        int remaining = amount;
        ItemStack[] contents;
        for (int length = (contents = player.getInventory().getContents()).length, i = 0; i < length; ++i) {
            ItemStack itemStack = contents[i];
            if (actual == amount) {
                break;
            }
            if (itemStack != null && itemStack.getType().equals(base.getType()) && itemStack.getDurability() == base.getDurability()) {
                if (remaining == 0) {
                    actual += itemStack.getAmount();
                    player.getInventory().remove(itemStack);
                } else if (itemStack.getAmount() >= amount) {
                    actual += itemStack.getAmount() - amount;
                    itemStack.setAmount(amount);
                    remaining = 0;
                } else {
                    int add = itemStack.getAmount();
                    remaining -= add;
                    player.getInventory().remove(itemStack);
                    actual += add;
                }
            }
        }
        return actual;
    }

    public static void giveItems(Player p, ItemStack... items) {
        Inventory i = p.getInventory();
        HashMap<Integer, ItemStack> notStored = i.addItem(items);
        for (Map.Entry<Integer, ItemStack> e : notStored.entrySet()) {
            p.getWorld().dropItemNaturally(p.getLocation(), e.getValue());
        }
    }

    public static void removeItems(Player p, ItemStack... items) {
        Inventory i = p.getInventory();
        HashMap<Integer, ItemStack> notStored = i.removeItem(items);
        for (Map.Entry<Integer, ItemStack> e : notStored.entrySet()) {
        }
    }

    public static ItemStack getItemStackFromString(String itemstack) {
        String[] splits = itemstack.split("@");
        String type = splits[0];
        String data = (splits.length == 2) ? splits[1] : null;
        if (data == null) {
            return new ItemStack(Material.getMaterial(type), 1);
        }
        return new ItemStack(Material.getMaterial(type), 1, (short) Integer.parseInt(data));
    }

    public static ItemStack getPlayerHead(String name) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(name);
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return itemStack;
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

    public static Material getMaterial(String materialName) {
        Material returnMaterial = null;
        if (ChatUtil.isInteger(materialName)) {
            int id = Integer.parseInt(materialName);
            returnMaterial = Material.getMaterial(id);
        } else {
            returnMaterial = Material.matchMaterial(materialName);
        }
        return returnMaterial;
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

