package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class HelpCommand extends PlayerCommand {
    public HelpCommand() { super("pomoc", "pomoc", "", "help");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(GlobalMessage.MESSAGES_HELP);
    }
}
