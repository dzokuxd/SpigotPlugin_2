package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class OpenCommand extends PlayerCommand {
    public OpenCommand() { super("open", "open <nick>", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        switch (args[0]) {
            case "inv":
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    p.sendMessage("&4Blad: &cGracz nie jest online!");
                    return;
                }
                Player other = Bukkit.getPlayer(args[0]);
                if (other != null) {
                    p.openInventory(other.getInventory());
                    return;
                }
            case "ender":
                p.openInventory(p.getEnderChest());
        }
    }
}
