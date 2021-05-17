package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.menu.GroupMenu;

public class GroupCommand extends PlayerCommand {
    public GroupCommand() { super("group", "group <nick>", "", "grupa"); }

    @Override
    public void onCommand(Player p, String[] args) {
        GroupMenu.show(p, args[0]);
    }
}
