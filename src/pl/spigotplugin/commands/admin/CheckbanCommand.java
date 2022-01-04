package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.BanManager;
import pl.spigotplugin.objects.user.Ban;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;

public class CheckbanCommand extends PlayerCommand {
    public CheckbanCommand() {
        super("checkban", RankType.HELPER);
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            core.usage(p, "checkban <gracz>");
            return;
        }
        Ban b = BanManager.getBan(args[0]);
        if (b == null) {
            p.sendMessage(ChatUtil.color("&cTen gracz nie ma bana!"));
            return;
        }
        p.sendMessage(ChatUtil.color("&fGracz: &d" + args[0]));
        p.sendMessage(ChatUtil.color("&fZbanowal: &d" +b.getAdmin()));
        p.sendMessage(ChatUtil.color("&fPowod: &d" +b.getReason()));
        p.sendMessage(ChatUtil.color("&fWygasa: &d" +((b.getTime() == 0L) ? "&cNigdy!" : "&cza " + DataUtil.secondsToString(b.getTime()))));
    }
}
