package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.utils.VoucherUtil;

public class VoucherCommand extends Command {
    public VoucherCommand() {
        super("voucher", "voucher <nick> <vip/svip/turbo> <ilosc>", "");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length != 3) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("&cGracz jest offline");
            return;
        }
        int size = Integer.parseInt(args[2]);
        VoucherUtil.giveWithAmount(args[1].toLowerCase(), size, target);
    }
}