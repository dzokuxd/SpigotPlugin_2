package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.menu.StoneMenu;

public class StoneCommand extends PlayerCommand {
    public StoneCommand() { super("stone", "", "", "drop"); }

    @Override
    public void onCommand(Player p, String[] args) {
        StoneMenu.show(p);
    }
}
