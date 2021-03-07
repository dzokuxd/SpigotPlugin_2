package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.menu.ChatMenu;

public class ChatManagerCommand extends PlayerCommand {
    public ChatManagerCommand() { super("chatmanager", "", "", "cm"); }

    @Override
    public void onCommand(Player p, String[] args) {
        ChatMenu.show(p);
    }
}
