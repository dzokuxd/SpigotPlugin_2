package pl.spigotplugin.commands.admin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;

public class MoreCommand extends PlayerCommand {
    public MoreCommand() { super("more", "", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        ItemStack is = p.getItemInHand();
        if (is == null || is.getType() == null || is.getType() == Material.AIR) {
            p.sendMessage("&cMuszisz miec cos w rece!");
            return;
        }
        is.setAmount(is.getMaxStackSize());
    }
}
