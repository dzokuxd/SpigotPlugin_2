package pl.spigotplugin.commands.premium;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

public class ResetujRankingCommand extends PlayerCommand {
    public ResetujRankingCommand() {super("resetujranking", "resetujranking", "spigotplugin.resetujranking", "rs");}

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            core.usage(p, getUsage());
        }
        User u = UserManager.getUser(args[0]);
        if (u == null) {
            return;
        }
        u.setPoints(1000);
        u.setKills(0);
        u.setDeaths(0);
        u.setAsysty(0);
    }
}
