package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.menu.SchowekMenu;

public class SchowekCommand extends PlayerCommand {
    public SchowekCommand() { super("schowek", RankType.GRACZ, "depozyt"); }

    @Override
    public void onCommand(Player p, String[] args) {
        SchowekMenu.show(p);
    }
}
