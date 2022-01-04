package pl.spigotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.enums.RankType;

import java.util.Collections;
import java.util.List;

public class GuildUtil {

    public static boolean hasItems(Player p){
        if (!GroupUtil.have(p, RankType.GRACZ))
            return true;
        List<ItemStack> items = guild.CREATE_COST;
        for (ItemStack item : items) {
            int amount = (int)(!GroupUtil.have(p, RankType.VIP) ? item.getAmount() * .5 : item.getAmount());
            if(p.getInventory().containsAtLeast(item,amount))
                return true;
        }
        p.sendMessage(ChatUtil.color("&cNie posiadasz potrzebnych itemow na gildie /g itemy"));
        return false;
    }
    public static void removeItems(Player p){
        if (!GroupUtil.have(p, RankType.GRACZ))
            return;
        List<ItemStack> items = guild.CREATE_COST;
        for (ItemStack item : items) {
            int amount = (int)(!GroupUtil.have(p, RankType.VIP) ? item.getAmount() * .5 : item.getAmount());
            ItemStack cloned = item.clone();
            cloned.setAmount(amount);
            p.getInventory().removeItem(cloned);
        }
    }
    public static void openInv(Player p,boolean premium){
        Inventory inventory = Bukkit.createInventory(null,9,ChatUtil.color("&7&lItemy na gildie"));
        List<ItemStack> items = guild.CREATE_COST;
        for (ItemStack item : items) {
            int amount = (int)(premium ? item.getAmount() * .5 : item.getAmount());
            ItemStack cloned = item.clone();
            cloned.setAmount(amount);

            int ii = ItemUtil.getItemAmount(cloned.getType(), p, cloned.getDurability());
            ItemMeta meta = cloned.getItemMeta();
            meta.setLore(Collections.singletonList(ChatUtil.color(
                    "&fPosiadasz: &d"+ii+"&7/&c"+amount+""
            )));
            cloned.setItemMeta(meta);

            inventory.addItem(cloned);
        }


        p.openInventory(inventory);
    }
}
