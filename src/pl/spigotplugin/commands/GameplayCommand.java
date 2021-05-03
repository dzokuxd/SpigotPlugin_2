package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.menu.GameplayMenu;

public class GameplayCommand extends PlayerCommand {
    public GameplayCommand() { super("gameplay", "gameplay", "", "gp"); }

    @Override
    public void onCommand(Player p, String[] args) {
        GameplayMenu.show(p);
    }
}
