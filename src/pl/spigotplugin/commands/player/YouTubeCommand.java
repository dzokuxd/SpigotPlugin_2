package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;

public class YouTubeCommand extends PlayerCommand {
    public YouTubeCommand() { super("youtube", "yt", "", "yt"); }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(core.MESSAGES_YT);
    }
}
