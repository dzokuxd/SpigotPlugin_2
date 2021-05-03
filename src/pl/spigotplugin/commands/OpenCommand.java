package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

import java.util.Locale;

public class OpenCommand extends PlayerCommand {
    public OpenCommand() { super("open", "open <nick> <inv/ender/armor>", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length != 2) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage("&4Blad: &cGracz nie jest online!");
            return;
        }
        switch (args[1].toLowerCase()) {
            case "inv":
                p.openInventory(o.getInventory());
                break;
            case "ender":
                p.openInventory(o.getEnderChest());
                break;
            case "armor":
                Inventory eq = Bukkit.createInventory(null, 9, "Zbroja gracza: " + p.getName());
                o.getInventory().getArmorContents();
                p.openInventory(eq);
                break;
            default:
                GlobalMessage.usage(p, getUsage());
                break;
        }
    }
}