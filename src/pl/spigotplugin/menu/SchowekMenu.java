package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.ItemBuilder;
import pl.spigotplugin.utils.ItemUtil;

public class SchowekMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lSchowek"));
        User u = UserManager.getUser(p);
        int schowek_koxy = 0;
        int schowek_refile = 0;
        int schowek_perly = 0;
        int schowek_strzaly = 0;
        int k1 = ItemUtil.getItemAmount(Material.GOLDEN_APPLE, p, (short) 1);
        if (k1 < statues.LIMIT_KOX) {
            int kox = k1 - statues.LIMIT_KOX;
            schowek_koxy = kox * -1;
        }
        if (u.getkoxy() < statues.LIMIT_KOX) {
            schowek_koxy = u.getkoxy();
        }
        int k2 = ItemUtil.getItemAmount(Material.GOLDEN_APPLE, p, (short) 0);
        if (k2 < statues.LIMIT_REFILE) {
            int ref = k2 - statues.LIMIT_REFILE;
            schowek_refile = ref * -1;
        }
        if (u.getRefile() < statues.LIMIT_REFILE) {
            schowek_refile = u.getRefile();
        }
        int k3 = ItemUtil.getItemAmount(Material.ENDER_PEARL, p, (short) 0);
        if (k3 < statues.LIMIT_PEARL) {
            int perla = k3 - statues.LIMIT_PEARL;
            schowek_perly = perla * -1;
        }
        if (u.getPerly() < statues.LIMIT_PEARL) {
            schowek_perly = u.getPerly();
        }
        int k4 = ItemUtil.getItemAmount(Material.ARROW, p, (short) 0);
        if (k4 < statues.LIMIT_STRZAL) {
            int dwa = k4 - statues.LIMIT_STRZAL;
            schowek_strzaly = dwa * -1;
        }
        if (u.getStrzaly() < statues.LIMIT_STRZAL) {
            schowek_strzaly = u.getStrzaly();
        }
        ItemBuilder air = new ItemBuilder(Material.getMaterial(160), 1, (short) 15).setTitle("&8\u2022");
        for (int j = 0; j < 9; j++) {
            inv.setItem(j, air.build());
        }
        ItemStack koxy = new ItemBuilder(
                Material.GOLDEN_APPLE, 1, (short) 1)
                .setTitle("&c&lKoxy")
                .addLore("&6\u00bb &6Posiadasz: &c" + u.getkoxy())
                .addLore("&6\u00bb &6Do wyplacenia: &c" + schowek_koxy)
                .addLore(" ")
                .addLore("&7\u00bb &6Limit tego przedmiutu w eq: &c" + statues.LIMIT_KOX)
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby wyplacic itemy!").build();
        ItemStack refile = new ItemBuilder(
                Material.GOLDEN_APPLE, 1, (short) 0)
                .setTitle("&c&lRefile")
                .addLore("&6\u00bb &6Posiadasz:&c " + u.getRefile())
                .addLore("&6\u00bb &6Do wyplacenia: &c" + schowek_refile)
                .addLore(" ")
                .addLore("&7\u00bb &6Limit tego przedmiutu w eq: &c" + statues.LIMIT_REFILE)
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby wyplacic itemy!").build();
        ItemStack perly = new ItemBuilder(
                Material.ENDER_PEARL)
                .setTitle("&c&lPerly")
                .addLore("&6\u00bb &6Posiadasz:&c " + u.getPerly())
                .addLore("&6\u00bb &6Do wyplacenia: &c" + schowek_perly)
                .addLore(" ")
                .addLore("&7\u00bb &6Limit tego przedmiutu w eq: &c" + statues.LIMIT_PEARL)
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby wyplacic itemy!").build();
        ItemStack strzaly = new ItemBuilder(
                Material.ARROW)
                .setTitle("&c&lStrzaly")
                .addLore("&6\u00bb &6Posiadasz:&c " + u.getStrzaly())
                .addLore("&6\u00bb &6Do wyplacenia: &c" + schowek_strzaly)
                .addLore(" ")
                .addLore("&7\u00bb &6Limit tego przedmiutu w eq: &c" + statues.LIMIT_STRZAL)
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby wyplacic itemy!").build();
        ItemStack limit = new ItemBuilder(
                Material.HOPPER)
                .setTitle("&c&lWyplac do limitu")
                .addLore("&6\u00bb &6Do wyplacenia: &c" + schowek_perly + " perel")
                .addLore("&6\u00bb &6Do wyplacenia: &c" + schowek_koxy + " koxow")
                .addLore("&7\u00bb &6Do wyplacenia: &c" + schowek_refile + " refow")
                .addLore("&7\u00bb &6Do wyplacenia: &c" + schowek_strzaly + " strzal")
                .addLore(" ")
                .addLore("&7\u00bb &2Kliknij na przedmiot, aby wyplacic itemy!").build();
        inv.setItem(10, koxy);
        inv.setItem(11, refile);
        inv.setItem(12, perly);
        inv.setItem(13, strzaly);
        inv.setItem(15, limit);
        p.openInventory(inv);
    }
}

