package pl.spigotplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.spigotplugin.objects.user.DataTrade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TradeManager {
    public static ConcurrentHashMap<Player, DataTrade> trades = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Player, Player> offers = new ConcurrentHashMap<>();
    public static List<DataTrade> tradeList = new LinkedList<>();

    public static Inventory createInventory(Player player, Player trader) {
        Inventory inventory = Bukkit.createInventory(player, 54, color("&8Handel z &9" + trader.getName()));
        ItemStack readyPlayer = new ItemStack(Material.GHAST_TEAR, 1);
        ItemMeta readyPlayerMeta = readyPlayer.getItemMeta();
        readyPlayerMeta.setDisplayName(color("&7Gracz " + player.getName() + " nie jest gotowy."));
        readyPlayer.setItemMeta(readyPlayerMeta);
        ItemStack odrzuc = new ItemStack(Material.MAGMA_CREAM, 1);
        ItemMeta odrzucMeta = odrzuc.getItemMeta();
        odrzucMeta.setDisplayName(color("&cOdrzuc wymiane."));
        odrzuc.setItemMeta(odrzucMeta);
        ItemStack akceptuj = new ItemStack(Material.SLIME_BALL, 1);
        ItemMeta akceptujMeta = akceptuj.getItemMeta();
        akceptujMeta.setDisplayName(color("&aAkceptuj wymiane."));
        akceptuj.setItemMeta(akceptujMeta);
        ItemStack readyTrader = new ItemStack(Material.GHAST_TEAR, 1);
        ItemMeta readyTraderMeta = readyTrader.getItemMeta();
        readyTraderMeta.setDisplayName(color("&7Gracz " + trader.getName() + " nie jest gotowy."));
        readyTrader.setItemMeta(readyTraderMeta);
        int emptySlots = 0;
        for (ItemStack item : trader.getInventory().getContents()) {
            if (item == null) {
                ++emptySlots;
            }
        }
        ItemStack full = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta fullMeta = full.getItemMeta();
        fullMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "x");
        fullMeta.setLore(Arrays.asList(ChatColor.GRAY + trader.getName() + " ma zbyt malo miejsca w ekwipunku."));
        full.setItemMeta(fullMeta);
        ItemStack barrier = new ItemStack(Material.VINE, 1);
        for (int i = 0; i < inventory.getSize(); ++i) {
            if (i % 9 == 4) {
                inventory.setItem(i, barrier);
            } else if (i % 9 >= 0 && i % 9 < 4) {
                int slot = (i / 9 - 1) * 4 + i % 9;
                if (slot >= emptySlots) {
                    inventory.setItem(i, full);
                }
            }
        }
        inventory.setItem(0, readyPlayer);
        inventory.setItem(8, readyTrader);
        inventory.setItem(2, odrzuc);
        inventory.setItem(3, akceptuj);
        return inventory;
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void openTrade(Player player, Player trader) {
        DataTrade trade = new DataTrade();
        trade.player1 = player;
        trade.player2 = trader;
        trade.inv1 = createInventory(player, trader);
        trade.inv2 = createInventory(trader, player);
        trade.playerReady1 = false;
        trade.playerReady2 = false;
        player.openInventory(trade.inv1);
        trader.openInventory(trade.inv2);
        trades.put(player, trade);
        trades.put(trader, trade);
        tradeList.add(trade);
        offers.remove(player);
        offers.remove(trader);
    }

    public static void endTrade(DataTrade trade) {
        trades.remove(trade.player1);
        trades.remove(trade.player2);
        tradeList.remove(trade);
        List<ItemStack> result1 = new ArrayList<ItemStack>();
        List<ItemStack> result2 = new ArrayList<ItemStack>();
        for (int i = 0; i < trade.inv1.getSize(); ++i) {
            if (i > 8 && i % 9 < 4 && trade.inv1.getItem(i + 5) != null) {
                result1.add(trade.inv1.getItem(i + 5));
            }
        }
        for (int i = 0; i < trade.inv2.getSize(); ++i) {
            if (i > 8 && i % 9 < 4 && trade.inv2.getItem(i + 5) != null) {
                result2.add(trade.inv2.getItem(i + 5));
            }
        }
        trade.player1.closeInventory();
        trade.player2.closeInventory();
        trade.player1.sendMessage(color("&6Zakonczono handel z graczem &c" + trade.player2.getName() + "&6!"));
        trade.player2.sendMessage(color("&6Zakonczono handel z graczem &c" + trade.player1.getName() + "&6!"));
        for (ItemStack item : result1) {
            if (item == null || item.getItemMeta().getDisplayName() == null || !item.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "" + ChatColor.BOLD + "x")) {
                trade.player1.getInventory().addItem(item);
            }
        }
        for (ItemStack item : result2) {
            if (item == null || item.getItemMeta().getDisplayName() == null || !item.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "" + ChatColor.BOLD + "x")) {
                trade.player2.getInventory().addItem(item);
            }
        }
        if (trade.player1.getItemOnCursor() != null) {
            result1.add(trade.player1.getItemOnCursor());
            trade.player1.setItemOnCursor(null);
        }
        if (trade.player2.getItemOnCursor() != null) {
            result2.add(trade.player2.getItemOnCursor());
            trade.player2.setItemOnCursor(null);
        }
        trade.player1.updateInventory();
        trade.player2.updateInventory();
    }

    public static void abortTrade(DataTrade trade) {
        trades.remove(trade.player1);
        trades.remove(trade.player2);
        tradeList.remove(trade);
        List<ItemStack> result1 = new ArrayList<ItemStack>();
        List<ItemStack> result2 = new ArrayList<ItemStack>();
        for (int i = 0; i < trade.inv1.getSize(); ++i) {
            if (i > 8 && i % 9 < 4 && trade.inv1.getItem(i) != null) {
                result1.add(trade.inv1.getItem(i));
            }
        }
        for (int i = 0; i < trade.inv2.getSize(); ++i) {
            if (i > 8 && i % 9 < 4 && trade.inv2.getItem(i) != null) {
                result2.add(trade.inv2.getItem(i));
            }
        }
        if (trade.player1.getItemOnCursor() != null) {
            result1.add(trade.player1.getItemOnCursor());
            trade.player1.setItemOnCursor(null);
        }
        if (trade.player2.getItemOnCursor() != null) {
            result2.add(trade.player2.getItemOnCursor());
            trade.player2.setItemOnCursor(null);
        }
        for (ItemStack item : result1) {
            if (item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null || !item.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "" + ChatColor.BOLD + "x")) {
                trade.player1.getInventory().addItem(item);
            }
        }
        for (ItemStack item : result2) {
            if (item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null || !item.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "" + ChatColor.BOLD + "x")) {
                trade.player2.getInventory().addItem(item);
            }
        }
        trade.player1.closeInventory();
        trade.player2.closeInventory();
        trade.player1.updateInventory();
        trade.player2.updateInventory();
    }

    public static void sendTrade(Player player, Player trader) {
        if (offers.get(trader) == null || !offers.get(trader).equals(player)) {
            offers.put(trader, player);
            trader.sendMessage(color("&6Otrzymano prosbe o handel od gracza &c" + player.getName() + "! Kliknij SHIFT + prawym, aby zaakceptowac."));
            player.sendMessage(color("&6Wyslano prosbe o handel do gracza &c" + trader.getName() + "."));
        } else {
            player.sendMessage(color("&cNie mozesz wyslac prosby o handel do tego gracza!"));
        }
    }

    public static void updateTrade(DataTrade trade) {
        for (int i = 0; i < trade.inv1.getSize(); ++i) {
            if (i > 8 && i % 9 < 4) {
                ItemStack item = trade.inv1.getItem(i);
                if (item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null || !item.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "" + ChatColor.BOLD + "x")) {
                    trade.inv2.setItem(i + 5, trade.inv1.getItem(i));
                }
            }
        }
        for (int i = 0; i < trade.inv2.getSize(); ++i) {
            if (i > 8 && i % 9 < 4) {
                ItemStack item = trade.inv2.getItem(i);
                if (item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null || !item.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "" + ChatColor.BOLD + "x")) {
                    trade.inv1.setItem(i + 5, trade.inv2.getItem(i));
                }
            }
        }
        trade.player1.updateInventory();
        trade.player2.updateInventory();
    }

    public static void readyTrade(DataTrade trade, Player player) {
        if (trade.player1.equals(player)) {
            trade.playerReady1 = !trade.playerReady1;
        } else {
            trade.playerReady2 = !trade.playerReady2;
        }
        trade.inv1.setItem(0, new ItemStack(trade.playerReady1 ? Material.GOLD_NUGGET : Material.GHAST_TEAR, 1));
        ItemMeta meta1 = trade.inv1.getItem(0).getItemMeta();
        meta1.setDisplayName(color(trade.playerReady1 ? ("&6Gracz " + trade.player1.getName() + " jest gotowy.") : ("&7Gracz " + trade.player1.getName() + " nie jest gotowy.")));
        trade.inv1.getItem(0).setItemMeta(meta1);
        trade.inv1.setItem(8, new ItemStack(trade.playerReady2 ? Material.GOLD_NUGGET : Material.GHAST_TEAR, 1));
        ItemMeta meta2 = trade.inv1.getItem(8).getItemMeta();
        meta2.setDisplayName(color(trade.playerReady2 ? ("&6Gracz " + trade.player2.getName() + " jest gotowy.") : ("&7Gracz " + trade.player2.getName() + " nie jest gotowy.")));
        trade.inv1.getItem(8).setItemMeta(meta2);
        trade.inv2.setItem(0, new ItemStack(trade.playerReady2 ? Material.GOLD_NUGGET : Material.GHAST_TEAR, 1));
        ItemMeta meta3 = trade.inv2.getItem(0).getItemMeta();
        meta3.setDisplayName(color(trade.playerReady2 ? ("&6Gracz " + trade.player2.getName() + " jest gotowy.") : ("&7Gracz " + trade.player2.getName() + " nie jest gotowy.")));
        trade.inv2.getItem(0).setItemMeta(meta3);
        trade.inv2.setItem(8, new ItemStack(trade.playerReady1 ? Material.GOLD_NUGGET : Material.GHAST_TEAR, 1));
        ItemMeta meta4 = trade.inv2.getItem(8).getItemMeta();
        meta4.setDisplayName(color(trade.playerReady1 ? ("&6Gracz " + trade.player1.getName() + " jest gotowy.") : ("&7Gracz " + trade.player1.getName() + " nie jest gotowy.")));
        trade.inv2.getItem(8).setItemMeta(meta4);
        trade.player1.updateInventory();
        trade.player2.updateInventory();
        if (trade.playerReady1 && trade.playerReady2) {
            endTrade(trade);
        }
    }
}
