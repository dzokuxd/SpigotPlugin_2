package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.holder.ItemHolder;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class ShopMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lSklep"));
        User u = UserManager.getUser(p);
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        ItemBuilder coinsy = new ItemBuilder(Material.SKULL_ITEM).setTitle("&7&lTwoje coinsy: ").addLore("&6Stan konta: &c" + u.getCoins());
        inv.setItem(10, ItemHolder.get("gui.shop.main.buy"));
        inv.setItem(13, coinsy.build());
        inv.setItem(16, ItemHolder.get("gui.shop.main.sell"));
        p.openInventory(inv);
    }
    public static void show1(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lSprzedaz itemow za coinsy!"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        inv.setItem(0, ItemHolder.get("gui.shop.sell.diamond"));
        inv.setItem(1, ItemHolder.get("gui.shop.sell.emerald"));
        inv.setItem(2, ItemHolder.get("gui.shop.sell.iron"));
        inv.setItem(3, ItemHolder.get("gui.shop.sell.gold"));
        inv.setItem(4, ItemHolder.get("gui.shop.sell.gunpowder"));
        inv.setItem(5, ItemHolder.get("gui.shop.sell.obsidian"));
        inv.setItem(6, ItemHolder.get("gui.shop.sell.cobble"));
        inv.setItem(8, ItemHolder.get("gui.back"));
        p.openInventory(inv);
    }
    public static void show2(Player p) {
        User u = UserManager.getUser(p);
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lWymiana za coinsy!"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        ItemBuilder helm = new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).setTitle("&cHELM 3/3").addLore(" ").addLore("&6Cena przedmiotu: &c500 coinsow").addLore(" ").addLore("&cKliknij, aby kupic!");
        ItemBuilder klata = new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.DURABILITY, 3).setTitle("&cKLATA 3/3").addLore(" ").addLore("&6Cena przedmiotu: &c500 coinsow").addLore(" ").addLore("&cKliknij, aby kupic!");
        ItemBuilder informacje = new ItemBuilder(Material.BOOK_AND_QUILL).setTitle("&7&lINFORMACJE").addLore("&7\u00bb &6Stan konta: &c" + u.getCoins()).addLore("&7\u00bb &6Coinsy mozesz otrzymac za:").addLore(" &7- &6Zabojstwo: &c200").addLore(" &7- &6Asysta: &c100").addLore(" &7- &6Uratowanie kolegi przy pomocy antynog: &c100");
        ItemBuilder wymianac = new ItemBuilder(Material.DOUBLE_PLANT).setTitle("&cWymien coins").addLore("&7\u00bb &6Kliknij aby przejsc do okna wymiany coins");
        inv.setItem(0, helm.build());
        inv.setItem(1, klata.build());
        inv.setItem(24, wymianac.build());
        inv.setItem(25, informacje.build());
        inv.setItem(26, ItemHolder.get("gui.back"));
        p.openInventory(inv);//TODO dodac itemy do sklepu
    }

    public static void show3(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lBoosty za level!"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        ItemBuilder gitemy = new ItemBuilder(
                Material.BOOK_AND_QUILL)
                .setTitle("&7\u00bb &c&lZAKLADANIE GILDI ZA POLOWE CENY")
                .addLore("&7\u00bb&6Koszt&7: &c50 Lvl")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby kupic boosta!");
        ItemBuilder boosty = new ItemBuilder(
                Material.PAPER)
                .setTitle("&7\u00bb &c&lWIEKSZY DROP (1.25)")
                .addLore("&7\u00bb &6Koszt&7: &c70 Lvl")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby kupic boosta!");
        ItemBuilder wroc = new ItemBuilder(
                Material.BARRIER)
                .setTitle("&4Wroc do poprzedniej strony!");
        ItemBuilder repair = new ItemBuilder(
                Material.ANVIL)
                .setTitle("&7\u00bb &c&lDostep do komendy Repair")
                .addLore("&7\u00bb &6Koszt&7: &c90 Lvl")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby kupic boosta!");
        inv.setItem(0, gitemy.build());
        inv.setItem(1, boosty.build());
        inv.setItem(2, repair.build());
        inv.setItem(26, wroc.build());
        p.openInventory(inv);
    }
}
