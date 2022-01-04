package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.menu.GroupMenu;

public class GroupCommand extends PlayerCommand {
    public GroupCommand() { super("group", RankType.HA, "grupa"); }

    @Override
    public void onCommand(Player p, String[] args) { //TODO dodac na nick
        if (args.length == 0) {
            GroupMenu.show(p, p.getName());
            return;
        }
        GroupMenu.show(p, args[0]);
    }
}
