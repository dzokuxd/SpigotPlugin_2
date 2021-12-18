package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.holder.ItemHolder;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class DiscoMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lDisco"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        ItemStack gray = new ItemBuilder(Material.CLAY,1,(short) 9).setTitle("&fTryb: &dGRAY").build();
        ItemStack random = new ItemBuilder(Material.CLAY,1,(short) 5).setTitle("&fTryb: &dRANDOM").build();
        ItemStack smooth = new ItemBuilder(Material.CLAY,1,(short) 4).setTitle("&fTryb: &dSMOOTH").build();
        ItemStack ultra = new ItemBuilder(Material.CLAY,1,(short) 1).setTitle("&fTryb: &dULTRA").build();
        ItemStack off = new ItemBuilder(Material.CLAY,1,(short) 14).setTitle("&cWylacz disco zbroje").build();
        inv.setItem(10, gray);
        inv.setItem(12, random);
        inv.setItem(14, smooth);
        inv.setItem(16, ultra);
        inv.setItem(22, off);
        p.openInventory(inv);
    }
}
