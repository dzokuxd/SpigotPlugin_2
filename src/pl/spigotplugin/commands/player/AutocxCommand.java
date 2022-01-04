package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.listeners.BlockBreakListener;
import pl.spigotplugin.utils.ChatUtil;

public class AutocxCommand extends PlayerCommand {
    public AutocxCommand() { super("autocobblex", RankType.GRACZ, "autocx"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (BlockBreakListener.playerSet.contains(p)) {
            BlockBreakListener.playerSet.remove(p);
            p.sendMessage(ChatUtil.color(core.COBBLEX_AUTOCX_TRUE));
        } else {
            BlockBreakListener.playerSet.add(p);
            p.sendMessage(ChatUtil.color(core.COBBLEX_AUTOCX_FALSE));
        }
    }
}
