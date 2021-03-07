package pl.spigotplugin.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VoucherUtil {
    public static ItemStack vip = new ItemBuilder(Material.BOOK).addEnchantment(Enchantment.DURABILITY,10).setTitle("&6Voucher &7(&cVIP&7)").addLore(ChatUtil.color("&6Kilknij &c&lPPM &6aby aktywowac!")).build();
    public static ItemStack svip = new ItemBuilder(Material.BOOK).addEnchantment(Enchantment.DURABILITY,10).setTitle("&6Voucher &7(&cSVIP&7)").addLore(ChatUtil.color("&6Kilknij &c&lPPM &6aby aktywowac!")).build();

    public static void giveWithAmount(String type, int amount, Player sender) {
        ItemStack toGive = null;
        switch (type) {
            case "vip":{
                toGive = vip.clone();
                toGive.setAmount(amount);
                break;
            }
            case "svip":{
                toGive = svip.clone();
                toGive.setAmount(amount);
                break;
            }
            default:{
                sender.sendMessage("Nie ma vouchera: " + type);
                break;
            }
        }
        if (toGive != null)
            ChatUtil.giveItems(sender, toGive);
    }
}
