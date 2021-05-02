package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class EventMenu {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lEventy"));
        User u = UserManager.getUser(p);
        ItemBuilder air = new ItemBuilder(Material.getMaterial(160), 1, (short) 15).setTitle("&8\u2022");
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, air.build());
        }
        ItemStack stone = new ItemBuilder(
                Material.STONE,1)
                .setTitle("&7&lDrop skrzynek z stone")
                .addLore("&6Aktywny: " + ((Config.EVENTS_CASE > System.currentTimeMillis()) ? ("&a" + DataUtil.secondsToString(Config.EVENTS_CASE)) : "&cNie"))
                .setGlow(Config.EVENTS_CASE > System.currentTimeMillis())
                .build();
        ItemStack kill = new  ItemBuilder(
                Material.DIAMOND_SWORD)
                .setTitle("&7&lDrop skrzynek z graczy")
                .addLore("&6Aktywny: " + ((Config.EVENTS_KILL > System.currentTimeMillis()) ? ("&a" + DataUtil.secondsToString(Config.EVENTS_KILL)) : "&cNie"))
                .addLore("")
                .addLore("&4* &cSkrzynki dropia za zabicie gracza z gildii!")
                .addLore("&4* &cZabicie lidera albo zastepcy daje duzo wieksze szanse!")
                .addLore("&4* &cTy i przeciwnik musicie posiadac inne gildie!")
                .setGlow(Config.EVENTS_KILL > System.currentTimeMillis())
                .build();
        ItemStack beacon = new ItemBuilder(
                Material.BEACON)
                .setTitle("&7&lDrop beacona")
                .addLore("&6Aktywny: " + ((Config.EVENTS_BEACON > System.currentTimeMillis()) ? ("&a" + DataUtil.secondsToString(Config.EVENTS_BEACON)) : "&cNie"))
                .addLore("")
                .addLore("&4* &cBeacon dropi za zabicie lidera gildii!")
                .addLore("&4* &cTy i przeciwnik musicie posiadac inne gildie!")
                .setGlow(Config.EVENTS_BEACON > System.currentTimeMillis())
                .build();
        ItemStack turbo = new ItemBuilder(
                Material.DIAMOND_PICKAXE).setTitle("&7&lTurbo drop")
                .addLore("&6Serwer: &c" + (Config.EVENTS_TURBO > System.currentTimeMillis() ? "&a" + DataUtil.secondsToString(Config.EVENTS_TURBO) : "&cBrak"))
                .addLore("&6Gracz: &c" + (u != null && u.getTurboDrop() > System.currentTimeMillis() ? "&a" + DataUtil.secondsToString(u.getTurboDrop()) : "&cBrak"))
                .setGlow(Config.EVENTS_TURBO > System.currentTimeMillis() || (u != null && u.getTurboDrop() > System.currentTimeMillis()))
                .build();
        inv.setItem(10, stone);
        inv.setItem(12, kill);
        inv.setItem(14, beacon);
        inv.setItem(16, turbo);
        p.openInventory(inv);
    }
}