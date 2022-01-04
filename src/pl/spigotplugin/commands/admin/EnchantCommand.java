package pl.spigotplugin.commands.admin;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.EnchantManager;
import pl.spigotplugin.utils.ChatUtil;

public class EnchantCommand extends PlayerCommand {
    public EnchantCommand() {
        super("enchant", RankType.ADMIN);
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 2) {
            core.usage(p, "enchant <zaklecie> [poziom]");
            return;
        }
        ItemStack item = p.getItemInHand();
        String enchantmentName = args[0];
        Enchantment enchant = EnchantManager.get(enchantmentName);
        if (enchant == null) {
            p.sendMessage(ChatUtil.color("&cNie znaleziono podanego enchantu!"));
            return;
        }
        int level = enchant.getMaxLevel();
        if (args.length == 2) {
            level = Integer.parseInt(args[1]);
        }
        item.addUnsafeEnchantment(enchant, level);
        p.sendMessage(ChatUtil.color("&fZaklecie &d" + enchant.getName().toLowerCase().replace("_", " ") + " &fzostalo dodane do przedmiotu w twojej rece!"));
    }
}
