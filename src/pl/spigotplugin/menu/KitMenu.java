package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class KitMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatUtil.color("&7&lKity"));
        User u = UserManager.getUser(p);
        ItemBuilder air = new ItemBuilder(Material.getMaterial(160), 1, (short) 15).setTitle("&8\u2022");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, air.build());
        }
        ItemStack kitvip = new ItemBuilder(
                Material.GOLD_PICKAXE)
                .setTitle("&c&lKit VIP")
                .addLore("&6Status: "
                        + (u.isKitVip() ? "&cNiedostepny &7(&c"
                        + DataUtil.secondsToString(u.getKit_vip()) + "&7)" : "&aDostepny"))
                .addLore("")
                .addLore("&7\u00bb &6Kliknij na przedmiot, aby odebrac itemy!")
                .setGlow(u.isKitVip())
                .build();
        ItemStack kitsvip = new ItemBuilder(
                Material.DIAMOND_PICKAXE)
                .setTitle("&c&lKit SVIP")
                .addLore("&6Status: "
                        + (u.isKitSvip() ? "&cNiedostepny &7(&c"
                        + DataUtil.secondsToString(u.getKit_svip()) + "&7)" : "&aDostepny"))
                .addLore("")
                .addLore("&7\u00bb &6Kliknij na przedmiot, aby odebrac itemy!")
                .setGlow(u.isKitSvip())
                .build();
        ItemStack kitstart = new ItemBuilder(
                Material.WOOD_PICKAXE)
                .setTitle("&c&lKit Start")
                .addLore("&6Status: "
                        + (u.isKitStart() ? "&cNiedostepny &7(&c"
                        + DataUtil.secondsToString(u.getKit_start()) + "&7)" : "&aDostepny"))
                .addLore("")
                .addLore("&7\u00bb &6Kliknij na przedmiot, aby odebrac itemy!")
                .setGlow(u.isKitStart())
                .build();
        ItemStack kitmieso = new ItemBuilder(
                Material.WOOD_PICKAXE)
                .setTitle("&c&lKit Mieso")
                .addLore("&6Status: "
                        + (u.isKitMieso() ? "&cNiedostepny &7(&c"
                        + DataUtil.secondsToString(u.getKit_mieso()) + "&7)" : "&aDostepny"))
                .addLore("")
                .addLore("&7\u00bb &6Kliknij na przedmiot, aby odebrac itemy!")
                .setGlow(u.isKitMieso())
                .build();
        inv.setItem(2, kitvip);
        inv.setItem(1, kitstart);
        inv.setItem(3, kitsvip);
        inv.setItem(0, kitmieso);
        p.openInventory(inv);
    }
}
