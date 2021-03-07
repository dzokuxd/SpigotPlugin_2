package pl.spigotplugin.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.utils.ChatUtil;

public class BroadcastCommand extends Command {
    public BroadcastCommand() { super("broadcast", "broadcast chat/title text", "permission", "bc");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        if (args[0].equalsIgnoreCase("chat")) {
            Bukkit.broadcastMessage("&c&lUWAGA &7\u00bb " + StringUtils.join(args, " ",1,args.length));
        } else if (args[0].equalsIgnoreCase("title")) {
            for (Player pb : Bukkit.getOnlinePlayers()) {
                ChatUtil.sendTitleMessage(pb, "&c&lUWAGA", StringUtils.join(args, " ",1,args.length), 30, 70, 40);
            }
        } else {
            sender.sendMessage("&7\u00bb &6Poprawne uzycie: &c/broadcast chat/title <wiadomosc>");
        }
    }
}
