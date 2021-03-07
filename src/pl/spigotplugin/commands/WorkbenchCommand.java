package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;

public class WorkbenchCommand extends PlayerCommand {
    public WorkbenchCommand() { super("workbench", "/workbench", "", "wb"); }

    @Override
    public void onCommand(Player p, String[] args) {
        p.openWorkbench(null, true);
    }
}
