package pl.spigotplugin.commands.premium;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.menu.DiscoMenu;

public class DiscoCommand extends PlayerCommand {
    public DiscoCommand() {super("disco", RankType.VIP);}

    @Override
    public void onCommand(Player p, String[] args) {
        DiscoMenu.show(p);
    }
}
