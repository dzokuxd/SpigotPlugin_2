package pl.spigotplugin.commands;

import org.bukkit.command.CommandSender;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.configs.GlobalMessage;

public class ConfigCommand extends Command {
    public ConfigCommand() { super("config", "config <reload>", "", "cfg");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        switch (args[0]) {
            case "reload": {
                Config.reloadConfig();
                GlobalMessage.reloadLang();
                sender.sendMessage("&8\u00bb &aConfig save!");
                return;
            }
            default: {
                sender.sendMessage(GlobalMessage.USAGE);
            }
        }
    }
}
