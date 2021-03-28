package pl.spigotplugin.objects.user;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.SpigotPlugin;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.ItemBuilder;
import pl.spigotplugin.utils.ItemSerializer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Backup {
    private String name;
    private long time;
    private int ping;
    private final String killer;
    private ItemStack[] inventory;
    private final ItemStack[] armor;
    private final ItemStack[] enderchest;

    public Backup(Player player, String killer) {
        this.name = player.getName();
        this.time = System.currentTimeMillis();
        this.killer = killer;
        this.inventory = player.getInventory().getContents();
        this.armor = player.getInventory().getArmorContents();
        this.enderchest = player.getEnderChest().getContents();
        this.insert();
    }

    public static void getList(Player p, Player o) throws SQLException {
        ResultSet rs = SpigotPlugin.getMySQL().query("SELECT * FROM backups WHERE name ='" + p.getName() + "' ORDER BY id DESC LIMIT 27;");
        Inventory inventory = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lBackup'y gracza"));
        while (rs.next()) {
            String player = rs.getString("name");
            long time = rs.getLong("time");
            int ping = rs.getInt("ping");
            String killer = rs.getString("killer");
            ItemBuilder i = new ItemBuilder(Material.WRITTEN_BOOK).setTitle(ChatUtil.color("ID: " + time));
            i.addLore(ChatUtil.color("&6Gracz: &c" + player));
            i.addLore(ChatUtil.color("&6Ping: &c" + ping));
            i.addLore(ChatUtil.color("&6Killer: &c" + killer));
            i.addLore(ChatUtil.color("&6Data: &c" + DataUtil.getDate(time)));
            inventory.addItem(i.build());
        }
        o.openInventory(inventory);
        rs.close();
    }

    public static void restore(Player p, long time, int mode, Player o) throws SQLException {
        ResultSet rs = SpigotPlugin.getMySQL().query("SELECT *, abs(" + time + " - time) as delta FROM backups WHERE name = '" + p.getName() + "' ORDER BY delta LIMIT 1");
        while (rs.next()) {
            switch (mode) {
                case 0: {
                    p.getInventory().setArmorContents(ItemSerializer.stringToItems(rs.getString("armor")));
                    p.getInventory().setContents(ItemSerializer.stringToItems(rs.getString("inventory")));
                    p.sendMessage("&6Twoj ekwipunek zostal cofniety do &c" + DataUtil.getDate(time) + " &6przez &c" + o.getName());
                    p.sendMessage("&6Cofneles ekwipunek do &c" + DataUtil.getDate(time) + " &6graczowi &c" + p.getName());
                    break;
                }
                case 1: {
                    p.getInventory().setArmorContents(ItemSerializer.stringToItems(rs.getString("armor")));
                    p.getInventory().setContents(ItemSerializer.stringToItems(rs.getString("inventory")));
                    p.getEnderChest().setContents(ItemSerializer.stringToItems(rs.getString("enderchest")));
                    p.sendMessage("&6Twoj ekwipunek + enderchest zostal cofniety do &c" + DataUtil.getDate(time) + " &6przez &c" + o.getName());
                    p.sendMessage("&6Cofneles ekwipunek + enderchest do &c" + DataUtil.getDate(time) + " &6graczowi &c" + p.getName());
                    break;
                }
                case 2: {
                    p.getEnderChest().setContents(ItemSerializer.stringToItems(rs.getString("enderchest")));
                    p.sendMessage("&6Twoj enderchest zostal cofniety do &c" + DataUtil.getDate(time) + " &6przez &c" + o.getName());
                    p.sendMessage("&6Cofneles enderchest do &c" + DataUtil.getDate(time) + " &6graczowi &c" + p.getName());
                    break;
                }
            }
        }
        rs.close();
    }

    private void insert() {
        SpigotPlugin.getMySQL().update("INSERT INTO backups(name, time, killer, ping, inventory, armor, enderchest) VALUES ('" + name + "','" + time + "','" + killer + "','" + ping + "','" + ItemSerializer.itemsToString(this.getInventory()) + "','" + ItemSerializer.itemsToString(this.getArmor()) + "','" + ItemSerializer.itemsToString(this.getEnderchest()) + "');");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getPing() {
        return ping;
    }

    public String getKiller() {
        return killer;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public ItemStack[] getEnderchest() {
        return enderchest;
    }
}
