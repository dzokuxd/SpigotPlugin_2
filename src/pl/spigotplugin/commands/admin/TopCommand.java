package pl.spigotplugin.commands.admin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

public class TopCommand extends PlayerCommand {
    public TopCommand() { super("top ", RankType.MOD); }

    @Override
    public void onCommand(Player p, String[] args) {
        Location location = p.getLocation();
        int y = location.getWorld().getHighestBlockYAt(location);
        if (y == 0) {
            y = 256;
        }
        location.setY(y);
        p.teleport(location);
        p.sendMessage(ChatUtil.color(core.TOP));
    }
}
