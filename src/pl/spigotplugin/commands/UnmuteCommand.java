package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.MuteManager;
import pl.spigotplugin.objects.user.Mute;

public class UnmuteCommand extends Command {
    public UnmuteCommand() {
        super("unmute", "unmute <gracz>", ""); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        if (!(sender instanceof Player) && args[0].equalsIgnoreCase("all")) {
            MuteManager.unmuteAll();
            sender.sendMessage("&8\u00bb &aOdciszyles wszystkich graczy!");
            return;
        }
        Mute m = MuteManager.getMute(args[0]);
        if (m == null) {
            sender.sendMessage("&4Blad: &cTen gracz nie jest wyciszony!");
            return;
        }
        if (!sender.hasPermission("core.cmd.admin") && !m.getAdmin().equalsIgnoreCase(sender.getName())) {
            sender.sendMessage("&7\u00bb &6Wyciszyl go administrator &c" + m.getAdmin());
            return;
        }
        MuteManager.unmute(m);
        Player parg = Bukkit.getPlayer(args[0]);
        sender.sendMessage("&7\u00bb &6Odciszyles gracza &c" + parg.getName() + "&7!");
        if (parg != null) {
            parg.sendMessage("&7\u00bb &6Zostales odciszony przez administatora &c" + sender.getName() + "&7!");
        }
    }
}
