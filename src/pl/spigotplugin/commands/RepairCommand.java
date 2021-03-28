package pl.spigotplugin.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.spigotplugin.api.PlayerCommand;

import java.util.HashMap;
import java.util.UUID;

public class RepairCommand extends PlayerCommand {
    private static HashMap<UUID, Long> times;

    static {
        times = new HashMap<UUID, Long>();
    }

    public RepairCommand() { super("repair", "/repair", ""); }

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
        Long t = RepairCommand.times.get(p.getUniqueId());
        if (t !=null && System.currentTimeMillis() - t < 10000L) {
            p.sendMessage("&cPrzedmiot mozesz naprawiac co 10 sekund!");
            return;
        }
        RepairCommand.times.put(p.getUniqueId(), System.currentTimeMillis());
        is.setDurability((short) 0);
        p.sendMessage("&6Naprawiles przedmiot &c" + (is.getType()));
    }
}
