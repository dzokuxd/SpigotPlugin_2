package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class SvipCommand extends PlayerCommand {
    public SvipCommand() { super("svip", "/svip", "");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(GlobalMessage.MESSAGES_SVIP);
    }
}
