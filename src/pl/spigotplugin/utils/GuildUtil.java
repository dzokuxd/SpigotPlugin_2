package pl.spigotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.spigotplugin.configs.GuildConfig;

import java.util.Collections;
import java.util.List;

public class GuildUtil {

    public static boolean hasItems(Player p){
        if (p.hasPermission("spigotplugin.itemy"))
            return true;
        List<ItemStack> items = GuildConfig.COST_CREATE;
        for (ItemStack item : items) {
            int amount = (int)(p.hasPermission("spigotplugin.premium") ? item.getAmount() * .5 : item.getAmount());
            if(p.getInventory().containsAtLeast(item,amount))
                return true;
        }

        return false;
    }
    public static void removeItems(Player p){
        if (p.hasPermission("spigotplugin.itemy"))
            return;
        List<ItemStack> items = GuildConfig.COST_CREATE;
        for (ItemStack item : items) {
            int amount = (int)(p.hasPermission("spigotplugin.premium") ? item.getAmount() * .5 : item.getAmount());
            ItemStack cloned = item.clone();
            cloned.setAmount(amount);
            p.getInventory().removeItem(cloned);
        }
    }
    public static void openInv(Player p,boolean premium){
        Inventory inventory = Bukkit.createInventory(null,9,ChatUtil.color("&7&lItemy na gildie"));

        List<ItemStack> items = GuildConfig.COST_CREATE;
        for (ItemStack item : items) {
            int amount = (int)(premium ? item.getAmount() * .5 : item.getAmount());
            ItemStack cloned = item.clone();
            cloned.setAmount(amount);

            int ii = ItemUtil.getItemAmount(cloned.getType(), p, cloned.getDurability());
            ItemMeta meta = cloned.getItemMeta();
            meta.setLore(Collections.singletonList(ChatUtil.color(
                    "&7\u00bb &6Posiadasz: &c"+ii+"&7/&c"+amount+" &a"+(ii / amount * 100.0)+"%"
            )));
            cloned.setItemMeta(meta);

            inventory.addItem(cloned);
        }


        p.openInventory(inventory);
    }
}
