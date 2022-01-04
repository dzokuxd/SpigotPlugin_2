package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;

public class WorkbenchCommand extends PlayerCommand {
    public WorkbenchCommand() { super("workbench", RankType.GRACZ, "wb"); }

    @Override
    public void onCommand(Player p, String[] args) {
        p.openWorkbench(null, true);
    }
}
