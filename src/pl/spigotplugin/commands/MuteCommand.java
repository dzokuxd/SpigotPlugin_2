package pl.spigotplugin.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.MuteManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.Mute;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.DataUtil;

public class MuteCommand extends Command {
    public MuteCommand() {
        super("mute", "mute <gracz> [czas] [powod]", "");
    }
    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        Mute m = MuteManager.getMute(args[0]);
        if (m != null) {
            sender.sendMessage("&4Blad: &cTen gracz jest juz wyciszony!");
            return;
        }
        User u = UserManager.getUser(sender.getName());
        if (sender instanceof Player && u == null) {
            return;
        }
        String dzokv = args[0];
        if (dzokv.equalsIgnoreCase("dzokv")) {
            sender.sendMessage("&4Blad: &cNie mozesz zmutowac dzoka");
            return;
        }
        String admin = sender.getName().equals("CONSOLE") ? "konsola" : sender.getName();
        String reason = "Brak!";
        if (args.length > 2) {
            reason = StringUtils.join(args, " ", 2, args.length);
        }
        long time = DataUtil.parseDateDiff(args[1], true);
        if (time > System.currentTimeMillis()) {
            Mute mute = new Mute(args[0], admin, reason, time);
            MuteManager.addMute(args[0], mute);
            Bukkit.broadcastMessage("&4Gracz &c" + args[0] + " &4zostal tymczasowo wyciszony przez &c" + sender.getName() + " &4do: &c" + DataUtil.getDate(time) + " &4powod: &c" + reason);
            return;
        }
        Mute mute = new Mute(args[0], admin, reason, 0L);
        MuteManager.addMute(args[0], mute);
        Bukkit.broadcastMessage("&4Gracz &c" + args[0] + " &4zostal permamentnie wyciszony przez &c" + sender.getName() + " &4powod: &c" + reason);
    }
}