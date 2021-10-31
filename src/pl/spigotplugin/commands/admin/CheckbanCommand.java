package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.BanManager;
import pl.spigotplugin.objects.user.Ban;
import pl.spigotplugin.utils.DataUtil;

public class CheckbanCommand extends PlayerCommand {
    public CheckbanCommand() {
        super("checkban", "checkban <gracz>", "spigot.checkban");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            core.usage(p, getUsage());
            return;
        }
        Ban b = BanManager.getBan(args[0]);
        if (b == null) {
            p.sendMessage("&cTen gracz nie ma bana!");
            return;
        }
        p.sendMessage("&fGracz: &d" + args[0]);
        p.sendMessage("");
        p.sendMessage("&fZbanowal: &d" +b.getAdmin());
        p.sendMessage("&fPowod: &d" +b.getReason());
        p.sendMessage("&fWygasa: &d" +((b.getTime() == 0L) ? "&cNigdy!" : "&cza " + DataUtil.secondsToString(b.getTime())));
        p.sendMessage("");
    }
}
