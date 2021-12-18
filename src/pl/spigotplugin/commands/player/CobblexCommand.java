package pl.spigotplugin.commands.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.Settings;

public class CobblexCommand extends PlayerCommand {
    public CobblexCommand() { super("cobblex", "cx", "", "cx"); }

    @Override
    public void onCommand(Player p, String[] args) {
        ItemStack item = Settings.cobblexItem.clone();
        if (!p.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE),64 * 9)) {
            p.sendMessage("&fNie posiadasz &d9*64 cobblestone");
            return;
        }
        p.getInventory().removeItem(new ItemStack(Material.COBBLESTONE,64 * 9));
        p.getInventory().addItem(item);
        p.sendMessage("&aPoprawnie stworzyles CobbbleX");
    }
}