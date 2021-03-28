package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.DropManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.drop.Drop;
import pl.spigotplugin.objects.drop.RandomDropData;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

import java.util.Map;

public class StoneMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 36, ChatUtil.color("&7&lDrop z Stone"));
        User u = UserManager.getUser(p);
        for (Drop d : RandomDropData.getDrops()) {
            double chance = d.getChance();
            if (p.hasPermission("core.drop.svip")) {
                chance *= 1.5;
            } else if (p.hasPermission("core.drop.vip")) {
                chance *= 1.25;
            }
            if (Config.EVENTS_TURBO > System.currentTimeMillis() || (u != null && u.getTurboDrop() > System.currentTimeMillis())) {
                chance *= 2;
            }
            double bonus;
            if (u == null) {
                bonus = 0.0;
            } else {
                bonus = d.getChance() / 100.0 * (100.0 + u.getLvl() * 1.2) - d.getChance();
            }
            ItemBuilder b = new ItemBuilder(d.getWhat().getType(), 1);
            b.setTitle("&7&l" + d.getName());
            b.addLore(" &7\u00bb &6Szansa na drop: &c" + ChatUtil.round(chance, 3));
            b.addLore(" &7\u00bb &6Bonus: &c" + ChatUtil.round(bonus, 3));
            b.addLore(" &7\u00bb &6Wypada pomiedzy: &cY:" + d.getMinHeight() + " &6a &c" + d.getMaxHeight() + " &6poziomem");
            b.addLore(" &7\u00bb &6Fortune: " + (d.isFortune() ? "&aTak" : "&cNie"));
            b.addLore(" &7\u00bb &6Drop: " + (d.isDisabled(p.getUniqueId()) ? "&cNie" : "&aTak"));
            b.addLore(" &7\u00bb &6Punkty: &c" + d.getExp() + " &6pkt.");
            b.addLore(" &7\u00bb &6Wykopane: &c" + u.getAmountByMaterial(d.getWhat().getType()) + " &6szt.");
            b.addLore("")
                    .setGlow(!d.isDisabled(p.getUniqueId()))
                    .build();
            inv.addItem(b.build());
        }
        ItemBuilder wroc = new ItemBuilder(Material.FENCE_GATE, 1, (short) 14).setTitle("&4Wroc do poprzedniej strony!");
        ItemBuilder cbl = new ItemBuilder(Material.COBBLESTONE).setTitle("&7&lCobblestone").addLore(" &7\u00bb &7Drop: &" + (RandomDropData.isNoCobble(p.getUniqueId()) ? "cNie" : "aTak"));
        ItemBuilder on = new ItemBuilder(Material.WOOL, (short) 5).setTitle("&aWlacz Wszystkie Dropy");
        ItemBuilder off = new ItemBuilder(Material.WOOL, (short) 14).setTitle("&cWylacz Wszystkie Dropy");
        ItemBuilder itemBuilder = new ItemBuilder(Material.EXP_BOTTLE).setTitle("&7&lDoswiadczenie");
        for (Map.Entry<Material, Integer> en : DropManager.getExps().entrySet()) {
            int exp = en.getValue();
            if (Config.EVENTS_TURBO > System.currentTimeMillis()) {
                exp *= 2;
            }
            itemBuilder.addLore("&7\u00bb &7" + en.getKey() + ": &c" + exp);
        }
        inv.setItem(28, off.build());
        inv.setItem(27, on.build());
        inv.setItem(34, cbl.build());
        inv.setItem(29, itemBuilder.build());
        inv.setItem(35, wroc.build());

        p.openInventory(inv);
    }

    public static void show1(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lMenu Dropow"));
        ItemBuilder air = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setTitle("&7\u2022");
        ItemBuilder stone = new ItemBuilder(Material.STONE).setTitle("&7Drop z &7&lStone").addLore("").addLore(" &7\u00bb &7Kliknij, aby przejsc dalej!");
        ItemBuilder cx = new ItemBuilder(Material.MOSSY_COBBLESTONE).setTitle("&7Drop z &7&lCobbleX").addLore("").addLore(" &7\u00bb &7Kliknij, aby przejsc dalej!");
        ItemBuilder kilof = new ItemBuilder(Material.CHEST).setTitle("&7Drop z &7&lEzz6/3/3").addLore("").addLore(" &7\u00bb &7Kliknij, aby przejsc dalej!");
        ItemBuilder featcase = new ItemBuilder(Material.CHEST).setTitle("&7Drop z &7&lFeatCase").addLore("").addLore(" &7\u00bb &7Kliknij, aby przejsc dalej!");
        inv.setItem(10, stone.build());
        inv.setItem(16, cx.build());
        inv.setItem(12, kilof.build());
        inv.setItem(14, featcase.build());
        for (int j = 0; j < 27; j++) {
            if (j == 10 || j == 12 || j == 14 || j == 16) continue;

            inv.setItem(j, air.build());
        }
        p.openInventory(inv);
    }

    public static void kilof633(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatUtil.color("&7&lDrop z Ez6/3/3"));
        ItemBuilder air = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setTitle("&7\u2022");
        ItemBuilder kilof = new ItemBuilder(Material.DIAMOND_PICKAXE).setTitle("&7Kilof &c6/3/3");
        ItemBuilder lose = new ItemBuilder(Material.DIRT).setTitle("&7Przegrana :(");
        ItemBuilder wroc = new ItemBuilder(Material.FENCE_GATE, 1, (short) 14).setTitle("&4Wroc do poprzedniej strony!");
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, air.build());
        }
        inv.setItem(0, kilof.build());
        inv.setItem(1, lose.build());
        inv.setItem(8, wroc.build());

        p.openInventory(inv);
    }

    public static void featcase(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatUtil.color("&7&lDrop z Case"));
        ItemBuilder air = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setTitle("&7\u2022");
        ItemBuilder beacon = new ItemBuilder(Material.BEACON).setTitle("&7Beacon").addLore("").addLore("&7Szansa: &c1%");
        ItemBuilder tnt = new ItemBuilder(Material.TNT).setTitle("&7TNT").addLore("").addLore("&7Szansa: &c5%");
        ItemBuilder perly = new ItemBuilder(Material.ENDER_PEARL).setTitle("&7Perly").addLore("").addLore("&7Szansa: &c10%");
        ItemBuilder gold = new ItemBuilder(Material.GOLD_BLOCK).setTitle("&7Bloki Zlota").addLore("").addLore("&7Szansa: &c10%");
        ItemBuilder diamond = new ItemBuilder(Material.DIAMOND_BLOCK).setTitle("&7Bloki Diaxow").addLore("").addLore("&7Szansa: &c5%");
        ItemBuilder koxy = new ItemBuilder(Material.GOLDEN_APPLE).setTitle("&7Koxy").addLore("").addLore("&7Szansa: &c10%");
        ItemBuilder dirt = new ItemBuilder(Material.DIRT).setTitle("&7Dirt").addLore("").addLore("&7Szansa: &c10%");
        ItemBuilder refy = new ItemBuilder(Material.GOLDEN_APPLE, 1, (short) 1).setTitle("&7Refy").addLore("").addLore("&7Szansa: &c15%");
        ItemBuilder wroc = new ItemBuilder(Material.FENCE_GATE, 1, (short) 14).setTitle("&4Wroc do poprzedniej strony!");
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, air.build());
        }
        inv.setItem(0, beacon.build());
        inv.setItem(1, tnt.build());
        inv.setItem(2, refy.build());
        inv.setItem(3, koxy.build());
        inv.setItem(4, perly.build());
        inv.setItem(5, gold.build());
        inv.setItem(6, diamond.build());
        inv.setItem(7, dirt.build());
        inv.setItem(8, wroc.build());

        p.openInventory(inv);
    }

    public boolean onCommand(Player sender, String[] args) {
        show1(sender);
        return true;
    }
}
