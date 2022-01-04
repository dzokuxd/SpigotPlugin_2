package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.menu.TopkiMenu;

public class TopkiCommand extends PlayerCommand {
    public TopkiCommand() { super("topki", RankType.GRACZ);
    }

    @Override
    public void onCommand(Player p, String[] args) {
        TopkiMenu.show(p);
    }
}
