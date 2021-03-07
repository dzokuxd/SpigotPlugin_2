package pl.spigotplugin.commands;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class TopCommand extends PlayerCommand {
    public TopCommand() { super("top ", "/top", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        Location location = p.getLocation();
        int y = location.getWorld().getHighestBlockYAt(location);
        if (y == 0) {
            y = 256;
        }
        location.setY(y);
        p.teleport(location);
        p.sendMessage(GlobalMessage.MESSAGES_TOP);
    }
}
