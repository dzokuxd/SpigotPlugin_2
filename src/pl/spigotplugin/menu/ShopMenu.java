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
        ItemBuilder kup = new ItemBuilder(
                Material.BEACON)
                .setTitle("&7&lWymiana za coinsy!")
                .addLore("&7\u00bb &6Kliknij aby przejsc dalej!");
        ItemBuilder coinsy = new ItemBuilder(
                Material.SKULL_ITEM)
                .setTitle("&7&lCoinsy:" + u.getName())
                .addLore("&6Stan konta: &c" + u.getCoins());
        ItemBuilder sell = new ItemBuilder(
                Material.HOPPER)
                .setTitle("&7&lSprzedaz itemow za coinsy!")
                .addLore("&7\u00bb &6Kliknij aby przejsc dalej!");
        inv.setItem(10, kup.build());
        inv.setItem(13, coinsy.build());
        inv.setItem(16, sell.build());
        p.openInventory(inv);
    }
    public static void show1(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lSprzedaz itemow za coinsy!"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        ItemBuilder diamond = new ItemBuilder(
                Material.DIAMOND)
                .setTitle("&7&lDIAMENTY")
                .addLore("&7\u00bb &6Sprzedaj za: &c40 coinsow")
                .addLore("&7\u00bb &6Ilosc: &c64")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby sprzedac!");
        ItemBuilder emerald = new ItemBuilder(
                Material.EMERALD)
                .setTitle("&7&lSZMARAGDY")
                .addLore("&7\u00bb &6Sprzedaj za: &c40 coinsow")
                .addLore("&7\u00bb &6Ilosc: &c64")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby sprzedac!");
        ItemBuilder iron = new ItemBuilder(
                Material.IRON_INGOT)
                .setTitle("&7&lSZTABKI ZELAZA")
                .addLore("&7\u00bb &6Sprzedaj za: &c25 coinsow")
                .addLore("&7\u00bb &6Ilosc: &c64")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby sprzedac!");
        ItemBuilder gold = new ItemBuilder(
                Material.GOLD_INGOT)
                .setTitle("&7&lSZTABKI ZLOTA")
                .addLore("&7\u00bb &6Sprzedaj za: &c50 coinsow")
                .addLore("&7\u00bb &6Ilosc: &c64")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby sprzedac!");
        ItemBuilder pearl = new ItemBuilder(
                Material.ENDER_PEARL)
                .setTitle("&7&lPERLY")
                .addLore("&7\u00bb &6Sprzedaj za: &c100 coinsow")
                .addLore("&7\u00bb &6Ilosc: &c16")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby sprzedac!");
        ItemBuilder gunpowder = new ItemBuilder(
                Material.getMaterial(289))
                .setTitle("&7&lPROCH")
                .addLore("&7\u00bb &6Sprzedaj za: &c80 coinsow")
                .addLore("&7\u00bb &6Ilosc: &c64")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby sprzedac!");
        ItemBuilder obs = new ItemBuilder(
                Material.OBSIDIAN)
                .setTitle("&7&lOBSYDIAN")
                .addLore("&7\u00bb &6Sprzedaj za: &c80 coinsow")
                .addLore("&7\u00bb &6Ilosc: &c64")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby sprzedac!");
        ItemBuilder cobble = new ItemBuilder(
                Material.COBBLESTONE)
                .setTitle("&7&lBRUK")
                .addLore("&7\u00bb &6Sprzedaj za: &c20 coinsow")
                .addLore("&7\u00bb &6Ilosc: &c64")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby sprzedac!");
        ItemBuilder wroc = new ItemBuilder(
                Material.BARRIER)
                .setTitle("&4Wroc do poprzedniej strony!");
        inv.setItem(0, diamond.build());
        inv.setItem(1, emerald.build());
        inv.setItem(2, iron.build());
        inv.setItem(3, gold.build());
        inv.setItem(4, gunpowder.build());
        inv.setItem(5, obs.build());
        inv.setItem(6, cobble.build());
        inv.setItem(8, wroc.build());
        p.openInventory(inv);
    }
    public static void show2(Player p) {
        User u = UserManager.getUser(p);
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lWymiana za coinsy!"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        ItemBuilder helm = new ItemBuilder(
                Material.DIAMOND_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .setTitle("&cHELM 3/3")
                .addLore(" ")
                .addLore("&6Cena przedmiotu: &c500 coinsow")
                .addLore(" ").addLore("&cKliknij, aby kupic!");
        ItemBuilder klata = new ItemBuilder(
                Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .setTitle("&cKLATA 3/3")
                .addLore(" ")
                .addLore("&6Cena przedmiotu: &c500 coinsow")
                .addLore(" ").addLore("&cKliknij, aby kupic!");
        ItemBuilder informacje = new ItemBuilder(
                Material.BOOK_AND_QUILL)
                .setTitle("&7&lINFORMACJE")
                .addLore("&7\u00bb &6Stan konta: &c" + u.getCoins())
                .addLore("&7\u00bb &6Coinsy mozesz otrzymac za:")
                .addLore(" &7- &6Zabojstwo: &c200")
                .addLore(" &7- &6Asysta: &c100")
                .addLore(" &7- &6Uratowanie kolegi przy pomocy antynog: &c100");
        ItemBuilder wroc = new ItemBuilder(
                Material.BARRIER)
                .setTitle("&4Wroc do poprzedniej strony!");
        ItemBuilder wymianac = new ItemBuilder(
                Material.DOUBLE_PLANT).setTitle("&cWymien coins")
                .addLore("&7\u00bb &6Kliknij aby przejsc do okna wymiany coins");
        inv.setItem(0, helm.build());
        inv.setItem(1, klata.build());
        inv.setItem(24, wymianac.build());
        inv.setItem(25, informacje.build());
        inv.setItem(26, wroc.build());
        p.openInventory(inv);
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
