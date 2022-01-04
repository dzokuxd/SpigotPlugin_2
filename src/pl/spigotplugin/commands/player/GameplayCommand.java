package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.menu.GameplayMenu;

public class GameplayCommand extends PlayerCommand {
    public GameplayCommand() { super("gameplay", RankType.GRACZ, "gp"); }

    @Override
    public void onCommand(Player p, String[] args) {
        GameplayMenu.show(p);
    }
}
