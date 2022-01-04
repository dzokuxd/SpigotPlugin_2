package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.BanManager;
import pl.spigotplugin.objects.user.Ban;

public class UnbanCommand extends Command {
    public UnbanCommand() { super("unban", RankType.MOD); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            core.usage(sender, "unban <gracz>");
            return;
        }
        if (!(sender instanceof Player) && args[0].equalsIgnoreCase("all")) {
            BanManager.unbanAll();
            sender.sendMessage("&aOdbanowales wszystkich graczy!");
            return;
        }
        Ban b = BanManager.getBan(args[0]);
        if (b == null) {
            sender.sendMessage("&cTen gracz nie ma bana!");
            return;
        }
        BanManager.unban(b);
        Bukkit.broadcastMessage("&c" + b.getName() + " zostal odbanowany przez " + sender.getName());
    }
}
