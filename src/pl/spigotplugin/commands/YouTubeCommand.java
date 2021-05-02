package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class YouTubeCommand extends PlayerCommand {
    public YouTubeCommand() { super("youtube", "yt", "", "yt"); }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(GlobalMessage.MESSAGES_YT);
    }
}
