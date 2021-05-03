package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.menu.SchowekMenu;

public class SchowekCommand extends PlayerCommand {
    public SchowekCommand() { super("schowek", "schwoek", "", "depozyt"); }

    @Override
    public void onCommand(Player p, String[] args) {
        SchowekMenu.show(p);
    }
}
