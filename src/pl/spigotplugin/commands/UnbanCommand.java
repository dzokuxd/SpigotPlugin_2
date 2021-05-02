package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.BanManager;
import pl.spigotplugin.objects.user.Ban;

public class UnbanCommand extends Command {
    public UnbanCommand() { super("unban", "unban <gracz>", ""); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        if (!(sender instanceof Player) && args[0].equalsIgnoreCase("all")) {
            BanManager.unbanAll();
            sender.sendMessage("&aOdbanowales wszystkich graczy!");
            return;
        }
        Ban b = BanManager.getBan(args[0]);
        if (b == null) {
            sender.sendMessage("&4Blad: &cTen gracz nie ma bana!");
            return;
        }
        if (!sender.hasPermission("core.cmd.admin") && !b.getAdmin().equalsIgnoreCase(sender.getName())) {
            sender.sendMessage("&7Ban nalezy do &c" + b.getAdmin());
            return;
        }
        BanManager.unban(b);
        Bukkit.broadcastMessage("&c" + b.getName() + " &4zostal odbanowany przez &c" + sender.getName() + "");
    }
}
