package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.utils.ItemBuilder;

public class GroupMenu {
    public static void show(Player p, String target) {
        Inventory inv = Bukkit.createInventory(p, 18, "Grupa dla: " + target);
        inv.addItem(getSkull(target, "Gracz"));
        inv.addItem(getSkull(target, "VIP"));
        inv.addItem(getSkull(target, "SVIP"));
        inv.addItem(getSkull(target, "EASY"));
        inv.addItem(getSkull(target, "HELPER"));
        inv.addItem(getSkull(target, "MOD"));
        inv.addItem(getSkull(target, "ADMIN"));
        inv.addItem(getSkull(target, "H@"));
        p.openInventory(inv);
    }

    private static ItemStack getSkull(String target, String groupName) {
        return new ItemBuilder(Material.SKULL_ITEM,1, (short)3).setSkullOwner(target).setTitle(groupName).addLore("&eGracz " + target + ", Kliknij aby nadac range " + groupName).build();
    }
}
