package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.menu.IsMenu;

public class OdbierzCommand extends PlayerCommand {
    public OdbierzCommand() { super("odbierz", "odbierz", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        IsMenu.show(p);
    }
}
