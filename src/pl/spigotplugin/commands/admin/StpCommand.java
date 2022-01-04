package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

public class StpCommand extends PlayerCommand {
    public StpCommand() { super("stp", RankType.MOD); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            core.usage(p, "stp <gracz>");
            return;
        }
        String nickja = args[0];
        if (nickja.equalsIgnoreCase(p.getName())) {
            p.sendMessage(ChatUtil.color("&cNie mozesz przeteleportowac sie sam do siebie!"));
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage(ChatUtil.color(ChatUtil.color("&cGracz jest offline!")));
            return;
        }
        o.teleport(p.getLocation());
        p.sendMessage(ChatUtil.color("&fPrzeteleportowales gracza &d" + o.getName() + " &fdo gracza &d" + p.getName()));
        o.sendMessage(ChatUtil.color("&fZostales przeteleportowany do gracza &d" + o.getName() + " &fprzez &d" + p.getName()));
    }
}
