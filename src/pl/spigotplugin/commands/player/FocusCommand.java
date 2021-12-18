package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.TagUtil;

public class FocusCommand extends PlayerCommand {
    public FocusCommand() {super("focus", "focus <nick>", "");}

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            core.usage(p, getUsage());
            return;
        }
        User u = UserManager.getUser(args[0]);
        if (u == null) {
            p.sendMessage("&cGracz nie istnieje!");
            return;
        }
        UserManager.getUser(p).focused = u.getName();
        TagUtil.updateBoard(p);
    }
}
