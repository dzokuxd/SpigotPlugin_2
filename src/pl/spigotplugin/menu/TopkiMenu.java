package pl.spigotplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.holder.ItemHolder;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.managers.TopsManager;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.ItemBuilder;

public class TopkiMenu {
    public static void show(Player p) {
        User u = UserManager.getUser(p);
        Inventory inv = Bukkit.createInventory(p, 45, ChatUtil.color("&7&lTopki"));
        ItemStack itemStack = ItemHolder.get("gui.black");
        for (int j = 0; j < 45; j++) {
            inv.setItem(j, itemStack);
        }
        ItemBuilder stone = new ItemBuilder(Material.STONE);
        stone.setTitle("&7&lTOP WYKOPANEGO KAMIENIA");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.stone.size() >= i2) {
                User user4 = TopsManager.stone.get(i2 - 1);
                stone.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + user4.getWykStone());
            } else {
                stone.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder obsidian = new ItemBuilder(Material.OBSIDIAN);
        obsidian.setTitle("&7&lTOP WYKOPANEGO OBSYDIANU");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.obsidian.size() >= i2) {
                User user4 = TopsManager.obsidian.get(i2 - 1);
                obsidian.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + user4.getWykObsidian());
            } else {
                obsidian.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder gapple = new ItemBuilder(Material.GOLDEN_APPLE,(short)1);
        gapple.setTitle("&7&lTOP ZJEDZONYCH KOXOW");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.gapple.size() >= i2) {
                User user4 = TopsManager.gapple.get(i2 - 1);
                gapple.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + user4.getKoxEaten());
            } else {
                gapple.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder apple = new ItemBuilder(Material.GOLDEN_APPLE,(short)0);
        apple.setTitle("&7&lTOP ZJEDZONYCH REFILI");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.apple.size() >= i2) {
                User user4 = TopsManager.apple.get(i2 - 1);
                apple.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + user4.getRefilEaten());
            } else {
                apple.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder pearls = new ItemBuilder(Material.ENDER_PEARL);
        pearls.setTitle("&7&lTOP WYRZUCONYCH PEREL");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.pearls.size() >= i2) {
                User user4 = TopsManager.pearls.get(i2 - 1);
                pearls.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + user4.getPearlThrown());
            } else {
                pearls.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder arrows = new ItemBuilder(Material.ARROW);
        arrows.setTitle("&7&lTOP WYSTRZELONYCH STRZAL");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.arrows.size() >= i2) {
                User user4 = TopsManager.arrows.get(i2 - 1);
                arrows.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + user4.getArrowsShoten());
            } else {
                arrows.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder lvl = new ItemBuilder(Material.DIAMOND_PICKAXE);
        lvl.setTitle("&7&lTOP LVL'A");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.lvl.size() >= i2) {
                User user4 = TopsManager.lvl.get(i2 - 1);
                lvl.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + user4.getLvl());
            } else {
                lvl.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder easycase = new ItemBuilder(Material.CHEST);
        easycase.setTitle("&7&lTOP OTWARTYCH EASYCASE");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.easycase.size() >= i2) {
                User user4 = TopsManager.easycase.get(i2 - 1);
                easycase.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + user4.getEasycase());
            } else {
                easycase.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder case6 = new ItemBuilder(Material.CHEST);
        case6.setTitle("&7&lTOP OTWARTYCH CASE6/1/1");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.easycase.size() >= i2) {
                User user4 = TopsManager.easycase.get(i2 - 1);
                case6.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + user4.getCase611());
            } else {
                case6.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder time = new ItemBuilder(Material.getMaterial(347));
        time.setTitle("&7&lTOP SPEDZONEGO CZASU");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.time.size() >= i2) {
                User user4 = TopsManager.time.get(i2 - 1);
                time.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + DataUtil.secondsToStringNoMinus(user4.getTime()));
            } else {
                time.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder zabojstwa = new ItemBuilder(Material.DIAMOND_SWORD);
        zabojstwa.setTitle("&7&lTOP RANKINGU");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.points.size() >= i2) {
                User user4 = TopsManager.points.get(i2 - 1);
                zabojstwa.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + (user4.getPoints()));
            } else {
                zabojstwa.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder asysty = new ItemBuilder(Material.GOLD_SWORD);
        asysty.setTitle("&7&lTOP ZABOJSTW");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.kills.size() >= i2) {
                User user4 = TopsManager.kills.get(i2 - 1);
                asysty.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + (user4.getKills()));
            } else {
                asysty.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemBuilder zgony = new ItemBuilder(Material.WOOD_SWORD);
        zgony.setTitle("&7&lTOP ASYST");
        for (int i2 = 1; i2 < 11; ++i2) {
            if (TopsManager.asysty.size() >= i2) {
                User user4 = TopsManager.asysty.get(i2 - 1);
                zgony.addLore(" &7" + i2 + ". &6" + user4.getName() + ": &c" + (user4.getAsysty()));
            } else {
                zgony.addLore(" &7" + i2 + ". &cBrak");
            }
        }
        ItemStack head = new ItemBuilder(Material.SKULL_ITEM).setTitle("&7&lTwoje Statystyki")
                .addLore("")
                .addLore(" &7\u00bb &6Zabojstwa: &c"+u.getKills())
                .addLore(" &7\u00bb &6Asysty: &c"+u.getAsysty())
                .addLore(" &7\u00bb &6Smierci: &c"+u.getDeaths())
                .addLore(" &7\u00bb &6Twoj lvl: &c" + u.getLvl())
                .addLore(" &7\u00bb &6Zjedzone koxy: &c" + u.getKoxEaten())
                .addLore(" &7\u00bb &6Zjedzone refile: &c" + u.getRefilEaten())
                .addLore(" &7\u00bb &6Wyrzucone perly: &c" + u.getPearlThrown())
                .addLore(" &7\u00bb &6Wystrzelone strzaly: &c" + u.getArrowsShoten())
                .addLore(" &7\u00bb &6Wykopany stone: &c" + u.getWykStone())
                .addLore(" &7\u00bb &6Wykopany Obsydian: &c" + u.getWykObsidian())
                .addLore(" &7\u00bb &6Otwarte easycasy: &c" + u.getEasycase())
                .addLore(" &7\u00bb &6Otwarte casy6/1/1: &c" + u.getCase611())
                .addLore("&6").setGlow(true).build();//TODO zmien na postawione casy
        inv.setItem(10, zabojstwa.build());
        inv.setItem(11, asysty.build());
        inv.setItem(12, zgony.build());
        inv.setItem(13, gapple.build());
        inv.setItem(14, apple.build());
        inv.setItem(15, pearls.build());
        inv.setItem(16, arrows.build());
        inv.setItem(22, head);
        inv.setItem(28, stone.build());
        inv.setItem(29, obsidian.build());
        inv.setItem(30, lvl.build());
        inv.setItem(32, easycase.build());
        inv.setItem(33, case6.build());
        inv.setItem(34, time.build());
        p.openInventory(inv);
    }
}
