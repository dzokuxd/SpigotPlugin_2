package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

public class SvipCommand extends PlayerCommand {
    public SvipCommand() { super("svip", RankType.GRACZ);
    }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(ChatUtil.color(core.MESSAGES_SVIP));
    }
}
