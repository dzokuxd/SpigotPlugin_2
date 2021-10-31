package pl.spigotplugin.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VoucherUtil {
    public static ItemStack vip = new ItemBuilder(
            Material.BOOK)
            .addEnchantment(Enchantment.DURABILITY,10)
            .setTitle("&fVoucher &7(&dVIP&7)")
            .addLore(ChatUtil.color("&fKilknij &d&lPPM &faby aktywowac!"))
            .build();
    public static ItemStack svip = new ItemBuilder(
            Material.BOOK)
            .addEnchantment(Enchantment.DURABILITY,10)
            .setTitle("&fVoucher &7(&dSVIP&7)")
            .addLore(ChatUtil.color("&fKilknij &d&lPPM &faby aktywowac!"))
            .build();
    public static ItemStack turbo = new ItemBuilder(
            Material.BOOK)
            .addEnchantment(Enchantment.DURABILITY,10)
            .setTitle("&fVoucher &7(&dTURBODROP 10M&7)")
            .addLore(ChatUtil.color("&fKilknij &d&lPPM &faby aktywowac!")).build();

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
            case "turbo":{
                toGive = turbo.clone();
                toGive.setAmount(amount);
                break;
            }
            default:{
                sender.sendMessage("&cNie ma vouchera: " + type);
                break;
            }
        }
        if (toGive != null)
            ItemUtil.giveItems(sender, toGive);
    }
}
