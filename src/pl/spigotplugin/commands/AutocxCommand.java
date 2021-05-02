package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.listeners.BlockBreakListener;

public class AutocxCommand extends PlayerCommand {
    public AutocxCommand() { super("autocobblex", "autocx", "", "autocx"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (BlockBreakListener.playerSet.contains(p)) {
            BlockBreakListener.playerSet.remove(p);
            p.sendMessage(GlobalMessage.COBBLEX_AUTOCX_TRUE);
        } else {
            BlockBreakListener.playerSet.add(p);
            p.sendMessage(GlobalMessage.COBBLEX_AUTOCX_FALSE);
        }
    }
}
