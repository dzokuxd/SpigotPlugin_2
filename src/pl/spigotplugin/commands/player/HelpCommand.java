package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;

public class HelpCommand extends PlayerCommand {
    public HelpCommand() { super("pomoc", "pomoc", "", "help");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(core.MESSAGES_HELP);
    }
}
