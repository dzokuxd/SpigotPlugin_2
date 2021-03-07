package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.utils.ChatUtil;

public class TrashCommand extends PlayerCommand {
    public TrashCommand() { super("smietnik", "/smietnik", "", "kosz");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        Inventory inventory = Bukkit.createInventory(p, 27, ChatUtil.color("&7&lKosz"));
        p.openInventory(inventory);
    }
}