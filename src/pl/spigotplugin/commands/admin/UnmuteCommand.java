package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.MuteManager;
import pl.spigotplugin.objects.user.Mute;

public class UnmuteCommand extends Command {
    public UnmuteCommand() {
        super("unmute", RankType.HELPER); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            core.usage(sender, "unmute <gracz>");
            return;
        }
        if (!(sender instanceof Player) && args[0].equalsIgnoreCase("all")) {
            MuteManager.unmuteAll();
            sender.sendMessage("&aOdciszyles wszystkich graczy!");
            return;
        }
        Mute m = MuteManager.getMute(args[0]);
        if (m == null) {
            sender.sendMessage("&cTen gracz nie jest wyciszony!");
            return;
        }
        MuteManager.unmute(m);
        sender.sendMessage("&fOdciszyles gracza &d" + args[0]);
        Player parg = Bukkit.getPlayer(args[0]);
        if (parg != null) {
            parg.sendMessage("&fZostales odciszony przez administatora &d" + sender.getName());
        }
    }
}
