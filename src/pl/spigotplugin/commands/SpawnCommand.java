package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.component.Teleporter;
import pl.spigotplugin.configs.Config;

public class SpawnCommand extends PlayerCommand {

    public SpawnCommand() {
        super("spawn", "spawn", "");
    }

    private final Location loc = new Location(Bukkit.getWorld("world"), 0, 90, 0);

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            if (!Config.MANAGE_SPAWN) {
                p.sendMessage("&4Blad: &cAktualnie teleport na spawn jest wylaczony!");
                return;
            }
            Teleporter.sendRequest(p, loc);
            return;
        }
        if (!p.hasPermission("df")) {
            p.sendMessage("&4Blad: &cGracz offline!");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            p.sendMessage("&4Blad: &cGracz offline!");
            return;
        }
        target.teleport(loc);
    }
}
