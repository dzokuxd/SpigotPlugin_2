package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class VanishCommand extends PlayerCommand {
    public VanishCommand() {
        super("vanish", "/vanish", "", "v");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            if (p.getGameMode() == GameMode.SPECTATOR) {
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(GlobalMessage.MESSAGES_VANISH_FALSE);
                for (Player admins : Bukkit.getOnlinePlayers()) {
                    if (admins.hasPermission("vanish")) {
                        admins.sendMessage(GlobalMessage.MESSAGES_VANISH_ATRUE.replace("{VANISHPLAYER}", p.getName()));
                    }
                }
            } else {
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(GlobalMessage.MESSAGES_VANISH_TRUE);
                for (Player admins : Bukkit.getOnlinePlayers()) {
                    if (admins.hasPermission("vanish")) {
                        admins.sendMessage(GlobalMessage.MESSAGES_VANISH_AFALSE.replace("{VANISHPLAYER1}", p.getName()));
                    }
                }
            }
        }
    }
}
