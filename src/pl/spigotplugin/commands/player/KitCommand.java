package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.menu.KitMenu;

public class KitCommand extends PlayerCommand {
    public KitCommand() { super("kit",  RankType.GRACZ); }

    @Override
    public void onCommand(Player p, String[] args) {
        KitMenu.show(p);
    }
}
