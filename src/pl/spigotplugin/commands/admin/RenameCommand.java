package pl.spigotplugin.commands.admin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.utils.ChatUtil;

public class RenameCommand extends PlayerCommand {
    public RenameCommand() { super("rename", "rename <nazwa>", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length != 1) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        ItemStack is = p.getItemInHand();
        if (is == null || is.getType() == Material.AIR) {
            p.sendMessage("&cNie masz nic w rece do nazwania!");
            return;
        }
        String name = StringUtils.join(args, " ");
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(ChatUtil.color(name));
        is.setItemMeta(meta);
        p.sendMessage("&6Zmieniles nazwe przedmiotu na &c " + name);
    }
}