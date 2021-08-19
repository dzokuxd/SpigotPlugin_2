package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.menu.ManageMenu;

public class ManageCommand extends PlayerCommand {
    public ManageCommand() { super("manage", "/manage", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        ManageMenu.openMenu(p);
    }
}
