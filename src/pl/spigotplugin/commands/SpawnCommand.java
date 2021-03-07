package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.component.Teleporter;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.configs.GlobalMessage;

public class SpawnCommand extends PlayerCommand {

    public SpawnCommand() {
        super("spawn", "/spawn", "");
    }

    private static final Location loc = new Location(Bukkit.getWorld("world"), 0, 90, 0);

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            GlobalMessage.usage(p, getUsage());
        }
        if (!p.hasPermission("core.cmd.admin")) {
            p.sendMessage("&cNie masz dostepu!");
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage("&4Blad: &cGracz offline!");
            return;
        }
        if (!Config.MANAGE_SPAWN) {
            p.sendMessage("&4Blad: &cAktualnie teleport na spawn jest wylaczony");
            return;
        }
        Teleporter.sendRequest(p, loc);
    }
}
