package pl.spigotplugin.commands.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.Settings;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

public class CobblexCommand extends PlayerCommand {
    public CobblexCommand() { super("cobblex", RankType.GRACZ, "cx"); }

    @Override
    public void onCommand(Player p, String[] args) {
        ItemStack item = Settings.cobblexItem.clone();
        if (!p.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE),64 * 9)) {
            p.sendMessage(ChatUtil.color("&fNie posiadasz &d9*64 cobblestone"));
            return;
        }
        p.getInventory().removeItem(new ItemStack(Material.COBBLESTONE,64 * 9));
        p.getInventory().addItem(item);
        p.sendMessage(ChatUtil.color("&aPoprawnie stworzyles CobbbleX"));
    }
}