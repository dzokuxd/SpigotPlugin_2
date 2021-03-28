package pl.spigotplugin.commands;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.EnchantManager;

public class EnchantCommand extends PlayerCommand {
    public EnchantCommand() {
        super("enchant", "/enchant <zaklecie> [poziom]", "");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 2) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        ItemStack item = p.getItemInHand();
        String enchantmentName = args[0];
        Enchantment enchant = EnchantManager.get(enchantmentName);
        if (enchant == null) {
            p.sendMessage("&4Blad: &cNie znaleziono podanego enchantu!");
            return;
        }
        int level = enchant.getMaxLevel();
        if (args.length == 2) {
            level = Integer.parseInt(args[1]);
        }
        item.addUnsafeEnchantment(enchant, level);
        p.sendMessage("&7\u00bb &6Zaklecie &c" + enchant.getName().toLowerCase().replace("_", " ") + " &6zostalo dodane do przedmiotu w twojej rece!");
    }
}
