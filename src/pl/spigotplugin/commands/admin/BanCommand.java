package pl.spigotplugin.commands.admin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.BanManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.Ban;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.DataUtil;

public class BanCommand extends Command {
    public BanCommand() { super("ban", "ban <nick> <czas/0> <powod>", "spigot.ban"); }

    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            core.usage(sender, getUsage());
            return;
        }
        Ban b = BanManager.getBan(args[0]);
        if (b != null) {
            sender.sendMessage("&cTen gracz ma juz bana!");
            return;
        }
        User u = UserManager.getUser(sender.getName());
        if (sender instanceof Player && u == null) {
            return;
        }
        String nick = args[0];
        if (nick.equalsIgnoreCase(sender.getName())) {
            sender.sendMessage("&cNie mozesz zbanowac sam siebie!");
            return;
        }
        String dzokv = args[0];
        if (dzokv.equalsIgnoreCase("dzokv")) {
            sender.sendMessage("&cNie mozesz zbanowac dzoka");
            return;
        }
        String admin = sender.getName().equals("CONSOLE") ? "konsola" : sender.getName();
        String reason = "Dzialanie na szkode serwera.";
        if (args.length > 2) {
            reason = StringUtils.join(args, " ", 2, args.length);
        }
        long time = DataUtil.parseDateDiff(args[1], true);
        if (time > System.currentTimeMillis()) {
            Ban ban = new Ban(args[0], admin, reason, time);
            BanManager.addBan(args[0], ban);
            Bukkit.broadcastMessage("&cGracz " + args[0] + " zostal tymczasowo zbanowany przez " + sender.getName() + " do: &c" + DataUtil.getDate(time) + " powod: &c" + reason);
            return;
        }
        Ban ban = new Ban(args[0], admin, reason, 0L);
        BanManager.addBan(args[0], ban);
        Bukkit.broadcastMessage("&cGracz " + args[0] + " zostal permamentnie zbanowany przez " + sender.getName() + " powod: &c" + reason);
    }
}
