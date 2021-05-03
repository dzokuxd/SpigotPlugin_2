package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class TopkiMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatUtil.color("&7&lTopki"));
        ItemBuilder air = new ItemBuilder(Material.getMaterial(160), 1, (short) 15).setTitle("&8\u2022");
        for (int j = 0; j < 9; j++) {
            inv.setItem(j, air.build());
        }
        ItemStack stone = new ItemBuilder(Material.STONE).setTitle("top wykopanego kamienia").build();
        inv.setItem(0, stone);
        p.openInventory(inv);
    }//TODO dodac all
}
