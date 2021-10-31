package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand extends PlayerCommand {
    public VanishCommand() {
        super("vanish", "vanish", "spigot.vanish", "v");
    }

    public static final List<Player> using = new ArrayList<>();

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            if (using.contains(p)) {
                using.remove(p);
                if (p.getGameMode() == GameMode.SPECTATOR) {
                    p.setGameMode(GameMode.SURVIVAL);
                    if (p.hasPermission("spigot.vanishsee"))
                        p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(core.VANISH_FALSE);
                    for (Player admins : Bukkit.getOnlinePlayers()) {
                        if (admins.hasPermission("vanish")) {
                            admins.sendMessage(core.VANISH_SEEFALSE.replace("{VANISHPLAYER}", p.getName()));
                        }
                    }
                }
            } else {
                using.add(p);
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(core.VANISH_TRUE);
                for (Player admins : Bukkit.getOnlinePlayers()) {
                    if (admins.hasPermission("spigot.vanishsee")) {
                        admins.sendMessage(core.VANISH_SEETRUE.replace("{VANISHPLAYER}", p.getName()));
                    }
                }
            }
        }
    }
}
