package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

public class YouTubeCommand extends PlayerCommand {
    public YouTubeCommand() { super("youtube", RankType.GRACZ, "yt"); }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(ChatUtil.color(core.MESSAGES_YT));
    }
}
