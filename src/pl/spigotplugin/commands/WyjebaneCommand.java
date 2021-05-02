package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

public class WyjebaneCommand extends PlayerCommand {
    public WyjebaneCommand() { super("wyjebane", "wyjebane <msg/tpa> <gracz>", "", "ignore"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 2) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        User u = UserManager.getUser(p);
        Player arg = Bukkit.getPlayer(args[1]);
        switch (args[0]) {
            case "msg": {
                if (u.isIgnoreTell(arg)) {
                    u.removeIgnoreTell(arg);
                    p.sendMessage("&7\u00bb &6Przestales ignorowac prywatne wiadomosci od gracza &c" + args[1] + "&7!");
                    return;
                }
                u.addIgnoreTell(arg);
                p.sendMessage("&7\u00bb &6Od teraz ignorujesz prywatne wiadomosci od gracza &c" + args[1] + "&7!");
                return;
            }
            case "tpa": {
                if (u.isIgnoreTpa(arg)) {
                    u.removeIgnoreTpa(arg);
                    p.sendMessage("&7\u00bb &6Przestales ignorowac prosby o teleportacje od gracza &c" + args[1] + "&7!");
                    return;
                }
                u.addIgnoreTpa(arg);
                p.sendMessage("&7\u00bb &6Od teraz ignorujesz prosby o teleportacje od gracza &c" + args[1] + "&7!");
            }
            default:
                break;
        }
    }
}
