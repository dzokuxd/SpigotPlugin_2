package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;

public class SvipCommand extends PlayerCommand {
    public SvipCommand() { super("svip", "svip", "");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(core.MESSAGES_SVIP);
    }
}
