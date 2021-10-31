package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;

public class StpCommand extends PlayerCommand {
    public StpCommand() { super("stp", "stp <gracz>", "spigot.stp"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            core.usage(p, getUsage());
            return;
        }
        String nickja = args[0];
        if (nickja.equalsIgnoreCase(p.getName())) {
            p.sendMessage("&cNie mozesz przeteleportowac sie sam do siebie!");
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage("&cGracz jest offline!");
            return;
        }
        o.teleport(p.getLocation());
        p.sendMessage("&fPrzeteleportowales gracza &d" + o.getName() + " &fdo gracza &d" + p.getName());
        o.sendMessage("&fZostales przeteleportowany do gracza &d" + o.getName() + " &fprzez &d" + p.getName());
    }
}
