package pl.spigotplugin.commands.admin;

import org.bukkit.command.CommandSender;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.listeners.BorderListener;
import pl.spigotplugin.utils.ChatUtil;

public class BorderCommand extends Command {
    public BorderCommand() { super("border", RankType.ADMIN); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            core.usage(sender, "border <world/gtp> <liczba>");
            return;
        }
        if (!ChatUtil.isInteger(args[1])) {
            sender.sendMessage("&cTo nie liczba");
            return;
        }
        int border = Integer.parseInt(args[1]);
        switch (args[0]) {
            case "world":
                statues.BORDER_WORLD = border;
                break;
            case "gtp":
                statues.BORDER_GTP = border;
                break;
            default:
                core.usage(sender, getUsage());
                return;
        }
        statues.reloadLang();
        BorderListener.setBorder();
        sender.sendMessage("&cUstawiles border "+args[0]+" na "+border);
    }
}
