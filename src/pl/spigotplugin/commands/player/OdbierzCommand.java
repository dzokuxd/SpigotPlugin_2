package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.menu.OdbierzMenu;

public class OdbierzCommand extends PlayerCommand {
    public OdbierzCommand() { super("odbierz", RankType.GRACZ); }

    @Override
    public void onCommand(Player p, String[] args) {
        OdbierzMenu.show(p);
    }
}
