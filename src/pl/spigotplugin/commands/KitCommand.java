package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.menu.KitMenu;

public class KitCommand extends PlayerCommand {
    public KitCommand() { super("kit", "/kit", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        KitMenu.show(p);
    }
}
