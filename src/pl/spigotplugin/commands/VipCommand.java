package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class VipCommand extends PlayerCommand {
    public VipCommand() { super("vip", "/vip", "");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(GlobalMessage.MESSAGES_VIP);
    }
}
