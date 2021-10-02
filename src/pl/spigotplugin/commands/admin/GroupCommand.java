package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.menu.GroupMenu;

public class GroupCommand extends PlayerCommand {
    public GroupCommand() { super("group", "group <nick>", "", "grupa"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            GroupMenu.show(p, p.getName());
            return;
        }
        GroupMenu.show(p, args[0]);
    }
}
