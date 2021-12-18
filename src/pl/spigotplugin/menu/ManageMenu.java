package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.ItemBuilder;

import java.lang.management.ManagementFactory;

public class ManageMenu {
    public static void openMenu(Player p){
        Inventory inventory = Bukkit.createInventory(null,18, ChatUtil.color("&7&lManage"));
        ItemStack gildie = new ItemBuilder(Material.GRASS).setTitle(ChatUtil.color("&7&lGildie"))
                .addLore(ChatUtil.color("&6Status: "+(statues.MANAGE_GUILDCREATE ? "&aon":"&coff")))
                .addLore(ChatUtil.color("&7Kliknij, aby przelaczyc!")).setGlow(statues.MANAGE_GUILDCREATE).build();
        ItemStack kit = new ItemBuilder(Material.DIAMOND_SWORD).setTitle(ChatUtil.color("&7&lKity"))
                .addLore(ChatUtil.color("&6Status: "+(statues.MANAGE_KIT ? "&aon":"&coff")))
                .addLore(ChatUtil.color("&7Kliknij, aby przelaczyc!")).setGlow(statues.MANAGE_KIT).build();
        ItemStack diamond = new ItemBuilder(Material.DIAMOND).setTitle(ChatUtil.color("&7&lDiax Sety"))
                .addLore(ChatUtil.color("&6Status: "+(statues.MANAGE_DIAMOND ? "&aon":"&coff")))
                .addLore(ChatUtil.color("&7Kliknij, aby przelaczyc!")).setGlow(statues.MANAGE_DIAMOND).build();
        ItemStack panel = new ItemBuilder(Material.DRAGON_EGG).setTitle(ChatUtil.color("&7&lPanel"))
                .addLore(ChatUtil.color("&6Status: "+(statues.MANAGE_PANEL ? "&aon":"&coff")))
                .addLore(ChatUtil.color("&7Kliknij, aby przelaczyc!")).setGlow(statues.MANAGE_PANEL).build();
        ItemStack shop = new ItemBuilder(Material.EMERALD).setTitle(ChatUtil.color("&7&lSklep"))
                .addLore(ChatUtil.color("&6Status: "+(statues.MANAGE_SHOP ? "&aon":"&coff")))
                .addLore(ChatUtil.color("&7Kliknij, aby przelaczyc!")).setGlow(statues.MANAGE_SHOP).build();
        ItemStack beacon = new ItemBuilder(Material.BEACON).setTitle(ChatUtil.color("&7&lBeacony"))
                .addLore(ChatUtil.color("&6Status: "+(statues.MANAGE_BEACON ? "&aon":"&coff")))
                .addLore(ChatUtil.color("&7Kliknij, aby przelaczyc!")).setGlow(statues.MANAGE_BEACON).build();
        ItemStack head = new ItemBuilder(Material.SKULL_ITEM).setTitle(ChatUtil.color("&7&lGlowy"))
                .addLore(ChatUtil.color("&6Status: "+(statues.MANAGE_DROPHEAD ? "&aon":"&coff")))
                .addLore(ChatUtil.color("&7Kliknij, aby przelaczyc!")).setGlow(statues.MANAGE_DROPHEAD).build();
        ItemStack tpa = new ItemBuilder(Material.ENDER_PEARL).setTitle(ChatUtil.color("&7&lTpa"))
                .addLore(ChatUtil.color("&6Status: "+(statues.MANAGE_TPA ? "&aon":"&coff")))
                .addLore(ChatUtil.color("&7Kliknij, aby przelaczyc!")).setGlow(statues.MANAGE_TPA).build();
        ItemStack spawn = new ItemBuilder(Material.EYE_OF_ENDER).setTitle(ChatUtil.color("&7&lSpawn"))
                .addLore(ChatUtil.color("&6Status: "+(statues.MANAGE_SPAWN ? "&aon":"&coff")))
                .addLore(ChatUtil.color("&7Kliknij, aby przelaczyc!")).setGlow(statues.MANAGE_SPAWN).build();
        ItemStack enchant = new ItemBuilder(Material.ENCHANTMENT_TABLE).setTitle(ChatUtil.color("&7&lEnchant"))
                .addLore(ChatUtil.color("&6Status: "+(statues.MANAGE_ENCHANT ? "&aon":"&coff")))
                .addLore(ChatUtil.color("&7Kliknij, aby przelaczyc!")).setGlow(statues.MANAGE_ENCHANT).build();
        ItemStack gc = new ItemBuilder(Material.WATCH).setTitle(ChatUtil.color("&7&lGC"))
                .addLore(ChatUtil.color("&7\u00bb &6Online serwer: &c")+ DataUtil.secondsToString((System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime())))
                .addLore(ChatUtil.color("&7\u00bb &6Max RAM: &c")+ Runtime.getRuntime().maxMemory() / 1024L / 1024L + "MB")
                .addLore(ChatUtil.color("&7\u00bb &6Total RAM: &c")+ Runtime.getRuntime().totalMemory() / 1024L / 1024L + "MB")
                .addLore(ChatUtil.color("&7\u00bb &6Free RAM: &c")+ Runtime.getRuntime().freeMemory() / 1024L / 1024L + "MB")
                .addLore(ChatUtil.color("")).build();
        inventory.addItem(gildie,kit,diamond,panel,shop,beacon,head,tpa,spawn,enchant,gc);
        p.openInventory(inventory);
    }
}
