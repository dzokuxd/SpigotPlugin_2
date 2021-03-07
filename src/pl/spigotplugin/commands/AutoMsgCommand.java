package pl.spigotplugin.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.utils.ChatUtil;

public class AutoMsgCommand extends PlayerCommand {
    public AutoMsgCommand() { super("automsg", "automsg <add/remove/list>", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        switch (args[0]) {
            case "add": {
                String msg = StringUtils.join(args, " ", 1, args.length);
                GlobalMessage.MESSAGES_AUTOMSG.add(msg);
                GlobalMessage.saveLang();
                p.sendMessage("&6Dodales do auto msg &c" + msg);
                return;
            }
            case "remove": {
                if (args.length < 2) {
                    p.sendMessage("&c/automsg remove <id>");
                    return;
                }
                if (!ChatUtil.isInteger(args[1])) {
                    p.sendMessage("&cTo nie jest id");
                    return;
                }
                if (GlobalMessage.MESSAGES_AUTOMSG.size() == 0) {
                    p.sendMessage("&cBrak automsg!");
                    return;
                }
                int i = Integer.parseInt(args[1]);
                if (GlobalMessage.MESSAGES_AUTOMSG.size() <=i) {
                    p.sendMessage("&cZle id!");
                    return;
                }
                p.sendMessage("&6Usunales automsg &c" + GlobalMessage.MESSAGES_AUTOMSG.get(i));
                GlobalMessage.MESSAGES_AUTOMSG.remove(i);
                GlobalMessage.saveLang();
                return;
            }
            case "list": {
                if (GlobalMessage.MESSAGES_AUTOMSG.size() == 0) {
                    p.sendMessage("&cBrak automsg!");
                    return;
                }
                int id = 0;
                p.sendMessage("&6Automsg \n");
                for (String s : GlobalMessage.MESSAGES_AUTOMSG) {
                    p.sendMessage("&7(" + id + "&7) &r" + s + "\n");
                    id++;
                }
                return;
            }
            default: {
                GlobalMessage.usage(p, getUsage());
                break;
            }
        }
    }
}
