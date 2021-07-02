package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.holder.ItemHolder;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class OdbierzMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lItemShop"));
        User u = UserManager.getUser(p);
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        ItemStack easycase = new ItemBuilder(
                Material.CHEST,1)
                .setTitle("&7&lEasyCase")
                .addLore("&6Posiadasz: &c"+u.getEasycase())
                .addLore("&cKliknij, aby odebrac!")
                .build();
        ItemStack easy611 = new ItemBuilder(
                Material.CHEST,1)
                .setTitle("&7&lCase6/3/3")
                .addLore("&6Posiadasz: &c"+u.getCase611())
                .addLore("&cKliknij, aby odebrac!")
                .build();
        inv.setItem(10, easycase);
        inv.setItem(16, easy611);
        p.openInventory(inv);
    }
}
