package pl.spigotplugin.commands.admin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

public class MoreCommand extends PlayerCommand {
    public MoreCommand() { super("more", RankType.ADMIN); }

    @Override
    public void onCommand(Player p, String[] args) {
        ItemStack is = p.getItemInHand();
        if (is == null || is.getType() == null || is.getType() == Material.AIR) {
            p.sendMessage(ChatUtil.color("&cMuszisz miec cos w rece!"));
            return;
        }
        is.setAmount(is.getMaxStackSize());
    }
}
