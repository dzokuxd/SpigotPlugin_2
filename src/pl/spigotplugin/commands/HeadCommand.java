package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.utils.ChatUtil;

public class HeadCommand extends PlayerCommand {
    public HeadCommand() { super("head", "/head [gracz]", "");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            ChatUtil.giveItems(p, ChatUtil.getPlayerHead(p.getName()));
            p.updateInventory();
            p.sendMessage("&7\u00bb &aOtrzymales swoja glowe!");
            return;
        }
        ChatUtil.giveItems(p, ChatUtil.getPlayerHead(args[0]));
        p.sendMessage("&7\u00bb &7Otrzymales glowe gracza &c" + args[0]);
    }
}
