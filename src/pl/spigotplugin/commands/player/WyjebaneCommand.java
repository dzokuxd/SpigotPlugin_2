package pl.spigotplugin.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;

public class WyjebaneCommand extends PlayerCommand {
    public WyjebaneCommand() { super("wyjebane", RankType.GRACZ, "ignore"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 2) {
            core.usage(p, "wyjebane <msg/tpa> <gracz>");
            return;
        }
        User u = UserManager.getUser(p);
        Player arg = Bukkit.getPlayer(args[1]);
        switch (args[0]) {
            case "msg": {
                if (u.isIgnoreTell(arg)) {
                    u.removeIgnoreTell(arg);
                    p.sendMessage(ChatUtil.color("&fPrzestales ignorowac prywatne wiadomosci od gracza &d" + args[1]));
                    return;
                }
                u.addIgnoreTell(arg);
                p.sendMessage(ChatUtil.color("&fOd teraz ignorujesz prywatne wiadomosci od gracza &d" + args[1]));
                return;
            }
            case "tpa": {
                if (u.isIgnoreTpa(arg)) {
                    u.removeIgnoreTpa(arg);
                    p.sendMessage(ChatUtil.color("&fPrzestales ignorowac prosby o teleportacje od gracza &d" + args[1]));
                    return;
                }
                u.addIgnoreTpa(arg);
                p.sendMessage(ChatUtil.color("&fOd teraz ignorujesz prosby o teleportacje od gracza &d" + args[1]));
            }
            default:
                break;
        }
    }
}
