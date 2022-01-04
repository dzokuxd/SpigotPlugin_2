package pl.spigotplugin.commands.admin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

public class AutoMsgCommand extends PlayerCommand {
    public AutoMsgCommand() { super("automsg", RankType.HA); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            core.usage(p, "automsg <add/remove/list>");
            return;
        }
        switch (args[0]) {
            case "add": {
                String msg = StringUtils.join(args, " ", 1, args.length);
                core.MESSAGES_AUTOMSG.add(msg);
                core.saveLang();
                p.sendMessage(ChatUtil.color("&cDodales do auto msg " + msg));
                return;
            }
            case "remove": {
                if (args.length < 2) {
                    p.sendMessage(ChatUtil.color("&c/automsg remove <id>"));
                    return;
                }
                if (!ChatUtil.isInteger(args[1])) {
                    p.sendMessage(ChatUtil.color("&cTo nie jest id"));
                    return;
                }
                if (core.MESSAGES_AUTOMSG.size() == 0) {
                    p.sendMessage(ChatUtil.color("&cBrak automsg!"));
                    return;
                }
                int i = Integer.parseInt(args[1]);
                if (core.MESSAGES_AUTOMSG.size() <=i) {
                    p.sendMessage(ChatUtil.color("&cZle id!"));
                    return;
                }
                p.sendMessage(ChatUtil.color("&cUsunales automsg " + core.MESSAGES_AUTOMSG.get(i)));
                core.MESSAGES_AUTOMSG.remove(i);
                core.saveLang();
                return;
            }
            case "list": {
                if (core.MESSAGES_AUTOMSG.size() == 0) {
                    p.sendMessage(ChatUtil.color("&cBrak automsg!"));
                    return;
                }
                int id = 0;
                p.sendMessage(ChatUtil.color("&cAutomsg \n"));
                for (String s : core.MESSAGES_AUTOMSG) {
                    p.sendMessage(ChatUtil.color("(" + id + ") &r" + s + "\n"));
                    id++;
                }
                return;
            }
            default: {
                core.usage(p, getUsage());
                break;
            }
        }
    }
}
