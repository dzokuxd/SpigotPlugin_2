package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.holder.ItemHolder;
import pl.spigotplugin.utils.ChatUtil;

public class CheckMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lSprawdzanie"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        inv.setItem(11, ItemHolder.get("gui.check.yes"));
        inv.setItem(15, ItemHolder.get("gui.check.no"));
        p.openInventory(inv);
    }
}
