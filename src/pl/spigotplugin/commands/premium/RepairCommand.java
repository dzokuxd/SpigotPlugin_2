package pl.spigotplugin.commands.premium;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;

public class RepairCommand extends PlayerCommand {
    public RepairCommand() { super("repair", "repair", "spigot.repair"); }

    @Override
    public void onCommand(Player p, String[] args) {
        ItemStack is = p.getItemInHand();
        if (is.getType().isBlock() || is.getType() == Material.AIR || is.getType() == Material.GOLDEN_APPLE) {
           p.sendMessage("&cTego przedmiotu nie mozesz naprawic!");
            return;
        }
        p.sendMessage(is.getDurability()+"");
        if (is.getDurability() == 0) {
            p.sendMessage("&cTen przedmiot jest naprawiony!");
            return;
        }
        is.setDurability((short) 0);
        p.playSound(p.getLocation(), Sound.ANVIL_USE, 5.0f, 3.0f);
        p.sendMessage("&fNaprawiles przedmiot &d" + (is.getType()));
    }
}
