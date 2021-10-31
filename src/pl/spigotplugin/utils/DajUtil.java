package pl.spigotplugin.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.configs.statues;

public class DajUtil {
    public static ItemStack boyfarmer = new ItemBuilder(
            Material.ENDER_PORTAL_FRAME)
            .setTitle("&a&lBoyFarmer")
            .setGlow(true)
            .build();
    public static ItemStack antynogi = new ItemBuilder(
            Material.NAME_TAG)
            .setTitle("&6&lAnty Nogi")
            .addLore(ChatUtil.color("&7\u00bb &2Kliknij PPM, aby uratowac czlonka gildii!"))
            .setGlow(true)
            .build();
    public static ItemStack stoniarka = new ItemBuilder(
            Material.ENDER_STONE)
            .addEnchantment(Enchantment.DURABILITY,10)
            .setTitle("&a&lStoniarka")
            .addEnchantment(Enchantment.THORNS, 10)
            .build();
    public static ItemStack casenormal = new ItemBuilder(
            Material.CHEST)
            .setTitle("&c&lSkrzynia "+ statues.IP)
            .build();
    public static ItemStack case611 = new ItemBuilder(
            Material.CHEST)
            .setTitle("&c&lSkrzynia Easy6/1/1")
            .build();

    public static void giveWithAmount(String type, int amount, Player sender) {
        ItemStack toGive = null;
        switch (type) {
            case "boyfarmer":{
                toGive = boyfarmer.clone();
                toGive.setAmount(amount);
                break;
            }
            case "antynogi":{
                toGive = antynogi.clone();
                toGive.setAmount(amount);
                break;
            }
            case "stoniarka":{
                toGive = stoniarka.clone();
                toGive.setAmount(amount);
                break;
            }
            case "easycase":{
                toGive = casenormal.clone();
                toGive.setAmount(amount);
                break;
            }
            case "case611":{
                toGive = case611.clone();
                toGive.setAmount(amount);
                break;
            }
            default:{
                sender.sendMessage("&cNie ma takiego itemu: " + type);
                break;
            }
        }
        if (toGive != null)
            ItemUtil.giveItems(sender, toGive);
    }
}
