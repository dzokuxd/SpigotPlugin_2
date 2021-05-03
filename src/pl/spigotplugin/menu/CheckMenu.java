package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class CheckMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lSprawdzanie"));
        ItemBuilder air = new ItemBuilder(Material.getMaterial(160), 1, (short) 15).setTitle("&8\u2022");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, air.build());
        }
        ItemStack tak = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 13).setTitle("&2&lPrzyznaje sie").addLore("&7Kliknij, aby dostac bana").addLore("&cJesli sie przyznasz dostaniesz bana na 1 dzien").build();
        ItemStack nie = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setTitle("&2&lNie przyznaje sie").addLore("&7Kliknij, aby pozostac na serwerze").addLore("&7Jesli sie nie przyznasz sprawdzanie bedzie trwalo dalej").build();
        inv.setItem(11, tak);
        inv.setItem(15, nie);
        p.openInventory(inv);
    }
}
