package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.holder.ItemHolder;
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
        inv.setItem(35, ItemHolder.get("gui.back"));

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
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, itemStack);
        }
        inv.setItem(10, ItemHolder.get("gui.drop.main.stone"));
        inv.setItem(16, ItemHolder.get("gui.drop.main.cx"));
        inv.setItem(12, ItemHolder.get("gui.drop.main.611"));
        inv.setItem(14, ItemHolder.get("gui.drop.main.easycase"));
        p.openInventory(inv);
    }

    public static void easy611(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatUtil.color("&7&lDrop z Easy6/1/1"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 9; j++) {
            inv.setItem(j, itemStack);
        }
        inv.setItem(0, ItemHolder.get("gui.drop.easy611.611"));
        inv.setItem(1, ItemHolder.get("gui.drop.easy611.gold"));
        inv.setItem(2, ItemHolder.get("gui.drop.easy611.dirt"));
        inv.setItem(8, ItemHolder.get("gui.back"));
        p.openInventory(inv);
    }

    public static void easycase(Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, ChatUtil.color("&7&lDrop z Case"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 54; j++) {
            inv.setItem(j, itemStack);
        }
        inv.setItem(1,ItemHolder.get("gui.drop.easycase.legendarne"));
        inv.setItem(3,ItemHolder.get("gui.drop.easycase.srednie"));
        inv.setItem(5,ItemHolder.get("gui.drop.easycase.slabe"));
        inv.setItem(7,ItemHolder.get("gui.drop.easycase.najgorsze"));
        inv.setItem(10,ItemHolder.get("gui.drop.easycase.beacon"));
        inv.setItem(12,ItemHolder.get("gui.drop.easycase.tnt"));
        inv.setItem(14,ItemHolder.get("gui.drop.easycase.helm"));
        inv.setItem(16,ItemHolder.get("gui.drop.easycase.miecz"));
        inv.setItem(19,ItemHolder.get("gui.drop.easycase.easy611"));
        inv.setItem(21,ItemHolder.get("gui.drop.easycase.biblioteczki"));
        inv.setItem(23,ItemHolder.get("gui.drop.easycase.klata"));
        inv.setItem(25,ItemHolder.get("gui.drop.easycase.kilof3"));
        inv.setItem(28,ItemHolder.get("gui.drop.easycase.goldenhead"));
        inv.setItem(30,ItemHolder.get("gui.drop.easycase.gold64"));
        inv.setItem(32,ItemHolder.get("gui.drop.easycase.spodnie"));
        inv.setItem(34,ItemHolder.get("gui.drop.easycase.gold16"));
        inv.setItem(37,ItemHolder.get("gui.drop.easycase.knock"));
        inv.setItem(39,ItemHolder.get("gui.drop.easycase.ref"));
        inv.setItem(41,ItemHolder.get("gui.drop.easycase.buty"));
        inv.setItem(43,ItemHolder.get("gui.drop.easycase.anvil"));
        inv.setItem(46,ItemHolder.get("gui.drop.easycase.perly"));
        inv.setItem(48,ItemHolder.get("gui.drop.easycase.kox"));
        inv.setItem(50,ItemHolder.get("gui.drop.easycase.kilof5"));
        inv.setItem(52,ItemHolder.get("gui.drop.easycase.dirt"));
        inv.setItem(53,ItemHolder.get("gui.back"));
        p.openInventory(inv);
    }
    public boolean onCommand(Player sender, String[] args) {
        menu(sender);
        return true;
    }
}
