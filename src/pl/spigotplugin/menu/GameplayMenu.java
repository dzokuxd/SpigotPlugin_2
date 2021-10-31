package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.holder.ItemHolder;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class GameplayMenu {
    public static void show(Player p){
        Inventory inv = Bukkit.createInventory(p, 54, ChatUtil.color("&7&lGamePlay"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 54; j++) {
            inv.setItem(j, itemStack);
        }
        ItemStack strzaly = new ItemBuilder(Material.ARROW, statues.LIMIT_STRZAL).setTitle("&7Limit: &c"+ statues.LIMIT_STRZAL).addLore("").build();
        ItemStack border = new ItemBuilder(Material.BARRIER).setTitle("&cBorder:").addLore("&7Swiat: &c"+ statues.BORDER_WORLD).addLore("&7GrupoweTP: &c"+ statues.BORDER_GTP).build();
        ItemStack gapple = new ItemBuilder(Material.GOLDEN_APPLE, statues.LIMIT_KOX, (short) 1).setTitle("&7Limit: &c"+ statues.LIMIT_KOX).build();
        ItemStack refil = new ItemBuilder(Material.GOLDEN_APPLE, statues.LIMIT_REFILE, (short) 0).setTitle("&7Limit: &c"+ statues.LIMIT_REFILE).build();
        ItemStack perly = new ItemBuilder(Material.ENDER_PEARL, statues.LIMIT_PEARL).setTitle("&7Limit: &c"+ statues.LIMIT_PEARL).build();
        inv.setItem(10, ItemHolder.get("gui.gameplay.tnt"));
        inv.setItem(16, strzaly);
        inv.setItem(19, border);
        inv.setItem(21, ItemHolder.get("gui.gameplay.knock"));
        inv.setItem(23, ItemHolder.get("gui.gameplay.miecz"));
        inv.setItem(25, gapple);
        inv.setItem(30, ItemHolder.get("gui.gameplay.luk"));
        inv.setItem(34, refil);
        inv.setItem(43, perly);
        inv.setItem(13, ItemHolder.get("gui.gameplay.helm"));
        inv.setItem(22, ItemHolder.get("gui.gameplay.klata"));
        inv.setItem(31, ItemHolder.get("gui.gameplay.spodnie"));
        inv.setItem(40, ItemHolder.get("gui.gameplay.buty"));
        p.openInventory(inv);
    }
}
