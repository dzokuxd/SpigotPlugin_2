package pl.spigotplugin.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.component.Teleporter;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.holder.LocationHolder;

public class SpawnCommand extends PlayerCommand {

    public  SpawnCommand() {
        super("spawn", "spawn", "");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            if (!Config.MANAGE_SPAWN) {
                p.sendMessage("&4Blad: &cAktualnie teleport na spawn jest wylaczony!");
                return;
            }
            Teleporter.sendRequest(p, LocationHolder.SPAWN);
            return;
        }
        if (!p.hasPermission("df")) {
            p.sendMessage("&cNie posiadasz uprawnien!");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            p.sendMessage("&4Blad: &cGracz offline!");
            return;
        }
        target.teleport(LocationHolder.SPAWN);
    }
}
