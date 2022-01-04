package pl.spigotplugin.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

public class GroupUtil
{
    public static boolean have(User u, RankType rank) {
        return u.getRankType().getPriority() >= rank.getPriority();
    }

    public static boolean have(CommandSender sender, RankType rank) {
        if (!(sender instanceof Player)) {
            return true;
        }
        User user = UserManager.getUser(sender.getName());
        return have(user, rank);
    }
}