package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.menu.ChatMenu;

public class ChatManagerCommand extends PlayerCommand {
    public ChatManagerCommand() { super("chatmanager",  RankType.GRACZ, "cm"); }

    @Override
    public void onCommand(Player p, String[] args) {
        ChatMenu.show(p);
    }
}
