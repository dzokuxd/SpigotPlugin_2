package pl.spigotplugin.commands.admin;

import org.bukkit.command.CommandSender;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.listeners.BorderListener;
import pl.spigotplugin.utils.ChatUtil;

public class BorderCommand extends Command {
    public BorderCommand() { super("border", "border (world/gtp) <liczba>", ""); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        if (!ChatUtil.isInteger(args[1])) {
            sender.sendMessage("&cTo nie liczba");
            return;
        }
        int border = Integer.parseInt(args[1]);
        switch (args[0]) {
            case "world":
                Config.BORDER_WORLD = border;
                break;
            case "gtp":
                Config.BORDER_GTP = border;
                break;
            default:
                GlobalMessage.usage(sender, getUsage());
                return;
        }
        Config.saveConfig();
        BorderListener.setBorder();
        sender.sendMessage("&6Ustawiles border "+args[0]+" na &c"+border);
    }
}
