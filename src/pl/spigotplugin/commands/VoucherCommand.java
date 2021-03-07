package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.utils.VoucherUtil;

public class VoucherCommand extends PlayerCommand {
    public VoucherCommand() { super("voucher", "voucher vip/svip ilosc", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length != 2) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        int size = Integer.parseInt(args[1]);
        VoucherUtil.giveWithAmount(args[0].toLowerCase(), size, p);
    }
}