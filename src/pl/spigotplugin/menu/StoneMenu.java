package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.DropManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.drop.Drop;
import pl.spigotplugin.objects.drop.RandomDropData;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.settings.Settings;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.ItemBuilder;

import java.util.List;
import java.util.Map;

public class StoneMenu {
    public static void stone(Player p) {
        Inventory inv = Bukkit.createInventory(p, 36, ChatUtil.color("&7&lDrop z Stone"));
        User u = UserManager.getUser(p);
        if (u == null) return;
        for (Drop d : RandomDropData.getDrops()) {
            double chance = d.getChance();
            if (p.hasPermission("core.drop.svip")) {
                chance += 1.0;
            } else if (p.hasPermission("core.drop.vip")) {
                chance += 0.50;
            }
            if (Config.EVENTS_TURBO > System.currentTimeMillis() || u.getTurboDrop() > System.currentTimeMillis()) {
                chance += 1;
            }
            double bonus = d.getChance() / 100.0 * (100.0 + u.getLvl() * 1.2) - d.getChance();
            ItemBuilder b = new ItemBuilder(d.getWhat().getType(), 1);
            b.setTitle("&7&l" + d.getName());
            b.addLore(" &7\u00bb &6Szansa na drop: &c" + ChatUtil.round(chance, 3));
            b.addLore(" &7\u00bb &6Bonus: &c" + ChatUtil.round(bonus, 3));
            b.addLore(" &7\u00bb &6Wypada ponizej: &c" + d.getMaxHeight() + " &6poziomu");
            b.addLore(" &7\u00bb &6Fortune: " + (d.isFortune() ? "&aTak" : "&cNie"));
            b.addLore(" &7\u00bb &6Drop: " + (d.isDisabled(p.getUniqueId()) ? "&cWylaczony" : "&aWlaczony"));
            b.addLore(" &7\u00bb &6Wykopane: &c" + u.getDrops().getOrDefault(d.getWhat().getType(), 0) + " &6szt.").setGlow(!d.isDisabled(p.getUniqueId())).build();
            inv.addItem(b.build());
        }
        ItemBuilder wroc = new ItemBuilder(Material.FENCE_GATE, 1, (short) 14).setTitle("&4Wroc do poprzedniej strony!");
        ItemBuilder cbl = new ItemBuilder(Material.COBBLESTONE,1).setTitle("&7&lCobblestone").addLore(" &7\u00bb &7Drop: &"+(RandomDropData.isNoCobble(p.getUniqueId()) ? "cNie" : "aTak"));
        ItemBuilder on = new ItemBuilder(Material.WOOL, (short) 5).setTitle("&aWlacz Wszystkie Dropy");
        ItemBuilder off = new ItemBuilder(Material.WOOL, (short) 14).setTitle("&cWylacz Wszystkie Dropy");
        ItemBuilder itemBuilder = new ItemBuilder(Material.EXP_BOTTLE).setTitle("&7&lDoswiadczenie");
        for (Map.Entry<Material, Integer> en : DropManager.getExps().entrySet()) {
            int exp = en.getValue();
            itemBuilder.addLore("&7\u00bb &7" + en.getKey() + ": &c" + exp);
        }
        inv.setItem(28, off.build());
        inv.setItem(27, on.build());
        inv.setItem(34, cbl.build());
        inv.setItem(29, itemBuilder.build());
        inv.setItem(35, wroc.build());

        p.openInventory(inv);
    }
    public static void inventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatUtil.color(Settings.inventoryName));
        List<ItemStack> drops = Settings.normalDropList;
        if (player.hasPermission("cobblex.premiumDrop")) {
            drops = Settings.premiumDropList;
        }
        for (ItemStack list : drops) {
            inventory.addItem(list);
        }
        player.openInventory(inventory);
    }

    public static void menu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lMenu Dropow"));
        ItemBuilder air = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setTitle("&7\u2022");
        ItemBuilder stone = new ItemBuilder(Material.STONE).setTitle("&7Drop z &c&lStone").addLore("").addLore(" &7\u00bb &6Kliknij, aby przejsc dalej!");
        ItemBuilder cx = new ItemBuilder(Material.MOSSY_COBBLESTONE).setTitle("&7Drop z &c&lCobbleX").addLore("").addLore(" &7\u00bb &6Kliknij, aby przejsc dalej!");
        ItemBuilder ez633 = new ItemBuilder(Material.CHEST).setTitle("&7Drop z &c&lEz6/1/1").addLore("").addLore(" &7\u00bb &6Kliknij, aby przejsc dalej!");
        ItemBuilder easycase = new ItemBuilder(Material.CHEST).setTitle("&7Drop z &c&lEasyCase").addLore("").addLore(" &7\u00bb &6Kliknij, aby przejsc dalej!");
        inv.setItem(10, stone.build());
        inv.setItem(16, cx.build());
        inv.setItem(12, ez633.build());
        inv.setItem(14, easycase.build());
        for (int j = 0; j < 27; j++) {
            if (j == 10 || j == 12 || j == 14 || j == 16) continue;

            inv.setItem(j, air.build());
        }
        p.openInventory(inv);
    }

    public static void ez633(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatUtil.color("&7&lDrop z Ez6/3/3"));
        ItemBuilder air = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setTitle("&7\u2022");
        ItemBuilder kilof = new ItemBuilder(Material.DIAMOND_PICKAXE).setTitle("&4&lKilof 6/1/1").addLore("&7\u00bb &6Szansa: &c1.0").addEnchantment(Enchantment.DIG_SPEED,5).addEnchantment(Enchantment.DURABILITY,1).addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,1);
        ItemBuilder lose = new ItemBuilder(Material.GOLD_INGOT).setTitle("&e&lNagroda pocieszenia").addLore("&7\u00bb &6Szansa: &c99.0");
        ItemBuilder wroc = new ItemBuilder(Material.FENCE_GATE, 1, (short) 14).setTitle("&4Wroc do poprzedniej strony!");
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, air.build());
        }
        inv.setItem(0, kilof.build());
        inv.setItem(1, lose.build());
        inv.setItem(8, wroc.build());
        p.openInventory(inv);
    }

    public static void easycase(Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, ChatUtil.color("&7&lDrop z Case"));
        ItemBuilder air = new ItemBuilder(Material.getMaterial(160), 1, (short) 15).setTitle("&8\u2022");
        for (int j = 0; j < 54; j++) {
            inv.setItem(j, air.build());
        }
        ItemBuilder legendarne = new ItemBuilder(Material.getMaterial(160), 1, (short) 5).setTitle("&7&lLegendarne");
        ItemBuilder srednie = new ItemBuilder(Material.getMaterial(160), 1, (short) 4).setTitle("&7&lSrednie");
        ItemBuilder slabe = new ItemBuilder(Material.getMaterial(160), 1, (short) 14).setTitle("&7&lSlabe");
        ItemBuilder najgorsze = new ItemBuilder(Material.getMaterial(160), 1, (short) 8).setTitle("&7&lNajgorsze");
        ItemBuilder wroc = new ItemBuilder(Material.FENCE_GATE, 1, (short) 14).setTitle("&4Wroc do poprzedniej strony!");
        ItemStack beacon = new ItemBuilder(
                Material.BEACON ,1)
                .setTitle("&e&lBeacon")
                .addLore("&7\u00bb &6Szansa: &c1.0")
                .setGlow(true)
                .build();
        ItemStack ez6 = new ItemBuilder(
                Material.ENDER_CHEST ,1)
                .setTitle("&e&lEz6/1/1")
                .addLore("&7\u00bb &6Szansa: &c1.0")
                .build();
        ItemStack golden = new ItemBuilder(
                Material.SKULL_ITEM, 1, (short) 3)
                .setTitle("&e&lGoldenHead")
                .addLore("&7\u00bb &6Szansa: &c1.0")
                .build();
        ItemStack knock = new ItemBuilder(
                Material.DIAMOND_SWORD ,1)
                .setTitle("&e&lMiecz knock 2")
                .addEnchantment(Enchantment.KNOCKBACK,2)
                .addLore("&7\u00bb &6Szansa: &c1.0")
                .build();
        ItemStack perly = new ItemBuilder(
                Material.ENDER_PEARL ,8)
                .setTitle("&e&lPerly")
                .addLore("&7\u00bb &6Szansa: &c1.0")
                .setGlow(true)
                .build();
        ItemStack tnt = new ItemBuilder(
                Material.TNT ,16)
                .setTitle("&e&lTNT")
                .addLore("&6Szansa: &c5.0")
                .build();
        ItemStack biblio = new ItemBuilder(
                Material.BOOKSHELF ,16)
                .setTitle("&e&lBookshelf")
                .addLore("&6Szansa: &c5.0")
                .build();
        ItemStack gold64 = new ItemBuilder(
                Material.GOLD_INGOT ,64)
                .setTitle("&e&lZloto")
                .addLore("&6Szansa: &c5.0")
                .build();
        ItemStack ref = new ItemBuilder(
                Material.GOLDEN_APPLE ,16,(short) 0)
                .setTitle("&e&lRefile")
                .addLore("&6Szansa: &c5.0")
                .build();
        ItemStack kox = new ItemBuilder(
                Material.GOLDEN_APPLE ,16,(short) 1)
                .setTitle("&e&lKox")
                .addLore("&6Szansa: &c5.0")
                .build();
        ItemStack helm = new ItemBuilder(
                Material.DIAMOND_HELMET ,1)
                .setTitle("&e&lDiamentowy Helm")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3)
                .addEnchantment(Enchantment.DURABILITY,2)
                .addLore("&6Szansa: &c10.0")
                .build();
        ItemStack klata = new ItemBuilder(
                Material.DIAMOND_CHESTPLATE ,1)
                .setTitle("&e&lDiamentowa Klata")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3)
                .addEnchantment(Enchantment.DURABILITY,2)
                .addLore("&6Szansa: &c10.0")
                .build();
        ItemStack spodnie = new ItemBuilder(
                Material.DIAMOND_LEGGINGS ,1)
                .setTitle("&e&lDiamentowe Spodnie")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3)
                .addEnchantment(Enchantment.DURABILITY,2)
                .addLore("&6Szansa: &c10.0")
                .build();
        ItemStack buty = new ItemBuilder(
                Material.DIAMOND_BOOTS ,1)
                .setTitle("&e&lDiamentowe Buty")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3)
                .addEnchantment(Enchantment.DURABILITY,2)
                .addLore("&6Szansa: &c10.0")
                .build();
        ItemStack kilof5 = new ItemBuilder(
                Material.DIAMOND_PICKAXE ,1)
                .setTitle("&e&lDiamentowy Kilof")
                .addEnchantment(Enchantment.DIG_SPEED,5)
                .addEnchantment(Enchantment.DURABILITY,3)
                .addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,3)
                .addLore("&6Szansa: &c10.0")
                .build();
        ItemStack miecz = new ItemBuilder(
                Material.DIAMOND_SWORD)
                .setTitle("&e&lMiecz Sharp")
                .addEnchantment(Enchantment.DAMAGE_ALL,4)
                .addLore("&6Szansa: &c20.0")
                .build();
        ItemStack kilof3 = new ItemBuilder(
                Material.DIAMOND_PICKAXE ,1)
                .setTitle("&e&lDiamentowy Kilof")
                .addEnchantment(Enchantment.DIG_SPEED,3)
                .addEnchantment(Enchantment.DURABILITY,2)
                .addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,2)
                .addLore("&6Szansa: &c20.0")
                .build();
        ItemStack gold16 = new ItemBuilder(
                Material.GOLD_INGOT ,16).
                setTitle("&e&lZloto")
                .addLore("&6Szansa: &c20.0")
                .build();
        ItemStack anvil = new ItemBuilder(
                Material.ANVIL ,8)
                .setTitle("&e&lKowadla")
                .addLore("&6Szansa: &c20.0")
                .build();
        ItemStack dirt = new ItemBuilder(
                Material.DIRT ,64)
                .setTitle("&e&lZiemia")
                .addLore("&6Szansa: &c20.0")
                .setGlow(true)
                .build();
        inv.setItem(1,legendarne.build());
        inv.setItem(3,srednie.build());
        inv.setItem(5,slabe.build());
        inv.setItem(7,najgorsze.build());
        inv.setItem(10,beacon);
        inv.setItem(12,tnt);
        inv.setItem(14,helm);
        inv.setItem(16,miecz);
        inv.setItem(19,ez6);
        inv.setItem(21,biblio);
        inv.setItem(23,klata);
        inv.setItem(25,kilof3);
        inv.setItem(28,golden);
        inv.setItem(30,gold64);
        inv.setItem(32,spodnie);
        inv.setItem(34,gold16);
        inv.setItem(37,knock);
        inv.setItem(39,ref);
        inv.setItem(41,buty);
        inv.setItem(43,anvil);
        inv.setItem(46,perly);
        inv.setItem(48,kox);
        inv.setItem(50,kilof5);
        inv.setItem(52,dirt);
        inv.setItem(53,wroc.build());
        p.openInventory(inv);
    }

    public boolean onCommand(Player sender, String[] args) {
        menu(sender);
        return true;
    }
}
