package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class IsMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lItemShop"));
        User u = UserManager.getUser(p);
        ItemBuilder air = new ItemBuilder(Material.getMaterial(160), 1, (short) 15).setTitle("&8\u2022");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, air.build());
        }
        ItemStack easycase = new ItemBuilder(
                Material.CHEST,1)
                .setTitle("&7&lEasyCase")
                .addLore("&6Posiadasz: ")
                .addLore("&cKliknij, aby odebrac!")
                .build();
        ItemStack ez633 = new ItemBuilder(
                Material.CHEST,1)
                .setTitle("&7&lEz6/3/3")
                .addLore("&6Posiadasz: ")
                .addLore("&cKliknij, aby odebrac!")
                .build();
        inv.setItem(10, easycase);
        inv.setItem(16, ez633);
        p.openInventory(inv);
    }
}
