package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.menu.EventMenu;

public class EventCommand extends PlayerCommand {
    public EventCommand() { super("eventy", RankType.GRACZ); }

    @Override
    public void onCommand(Player p, String[] args) {
        EventMenu.show(p);
    }
}
