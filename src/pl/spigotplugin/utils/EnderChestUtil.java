package pl.spigotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

import java.util.Arrays;

public class EnderChestUtil {
    public static void open(Player player) {
        User u = UserManager.getUser(player);
        ItemStack[] enderchest = u.getEnderchest();
        Inventory inventory = Bukkit.createInventory(player, 45, "Ender Chest");
        if (u.getEnderchest() != null) {
            inventory.setContents(enderchest);
        }
        player.openInventory(inventory);
    }

    public static void openGracz(Player player) {
        User u = UserManager.getUser(player);
        ItemStack[] enderchest = u.getEnderchest();
        Inventory inventory = Bukkit.createInventory(player, 45, "Ender Chest");
        if (u.getEnderchest() != null) {
            inventory.setContents(enderchest);
        }
        for (int i = 36; i < 45; ++i) {
            ItemBuilder szklo = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)14).setTitle(ChatUtil.color("&cDOSTEPNE DLA RANG PREMIUM"));
            inventory.setItem(i, szklo.build());
        }
        player.openInventory(inventory);
    }

    public static void openOther(Player opener, Player player) {
        User u = UserManager.getUser(player);
        ItemStack[] enderchest = u.getEnderchest();
        Inventory inventory = Bukkit.createInventory(player, 45, "Ender Chest: " + player.getName());
        if (u.getEnderchest() != null) {
            inventory.setContents(enderchest);
        }
        if (!player.hasPermission("spigot.powiekszonyec")) {
            for (int i = 36; i < 45; ++i) {
                ItemBuilder szklo = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)14).setTitle(ChatUtil.color("&cDOSTEPNE DLA RANG PREMIUM"));
                inventory.setItem(i, szklo.build());
            }
        }
        opener.openInventory(inventory);
    }

    public static void close(Player player, Inventory inventory) {
        User u = UserManager.getUser(player);
        ItemStack[] current = inventory.getContents();
        ItemStack[] last = u.getEnderchest();
        //if (!Arrays.equals(current, last)) {
            u.setEnderchest(current);
            u.save();
       // }
    }
}
