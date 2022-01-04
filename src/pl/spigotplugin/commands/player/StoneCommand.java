package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.menu.StoneMenu;

public class StoneCommand extends PlayerCommand {
    public StoneCommand() { super("stone", RankType.GRACZ, "drop"); }

    @Override
    public void onCommand(Player p, String[] args) {
        StoneMenu.menu(p);
    }
}
