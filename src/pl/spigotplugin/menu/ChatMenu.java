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

public class ChatMenu {
    public static void show(Player p) {
        User u = UserManager.getUser(p);
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lZarzadzanie chatem"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        ItemStack autoMessages = new ItemBuilder(
                Material.BOOK_AND_QUILL)
                .setTitle("&7&lAutomatyczne wiadomosci")
                .addLore("&6Status: "+ (u.isAutoMessages() ? "&aTak" : "&cNie"))
                .addLore("")
                .addLore("&6Kliknij na przedmiot, aby zmienic status!")
                .setGlow(u.isAutoMessages())
                .build();
        inv.setItem(1, autoMessages);
        p.openInventory(inv);
    }
}