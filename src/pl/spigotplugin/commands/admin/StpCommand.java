package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class StpCommand extends PlayerCommand {
    public StpCommand() { super("stp", "stp <gracz>", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        String nickja = args[0];
        if (nickja.equalsIgnoreCase(p.getName())) {
            p.sendMessage("&cNie mozesz przeteleportowac sie sam do siebie! ;(");
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage("&cGracz jest offline!");
            return;
        }
        o.teleport(p.getLocation());
        p.sendMessage("&7\u00bb &6Przeteleportowales gracza &c" + o.getName() + " &6do gracza &c" + p.getName());
        o.sendMessage("&7\u00bb &6Zostales przeteleportowany do gracza &c" + o.getName() + " &6przez &c" + p.getName());
    }
}
