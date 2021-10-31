package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;

public class VipCommand extends PlayerCommand {
    public VipCommand() { super("vip", "vip", "");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(core.MESSAGES_VIP);
    }
}
