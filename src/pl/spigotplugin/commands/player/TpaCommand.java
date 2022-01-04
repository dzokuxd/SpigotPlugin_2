package pl.spigotplugin.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.GroupUtil;

public class TpaCommand extends PlayerCommand {
    public TpaCommand() { super("tpa", RankType.GRACZ); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length != 1) {
            core.usage(p, "tpa <gracz>");
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage(ChatUtil.color("&cGracz offline!"));
            return;
        }
        User u = UserManager.getUser(o);
        if (u == null) {
            return;
        }
        String nickja = args[0];
        if (nickja.equalsIgnoreCase(p.getName())) {
            p.sendMessage(ChatUtil.color("&cNie mozesz teleportowac sie sam do siebie! ;("));
            return;
        }
        if (!statues.MANAGE_TPA) {
            p.sendMessage(ChatUtil.color("&cAktualnie tpa jest wylaczone"));
            return;
        }
        if (u.getTpa().contains(p)) {
            p.sendMessage(ChatUtil.color("&cWyslales juz zaproszenie o teleport do gracza " + o.getName() + "!"));
            return;
        }
        if (u.isIgnoreTpa(p) && !GroupUtil.have(p, RankType.HELPER)) {
            p.sendMessage(ChatUtil.color("&cTen gracz zablokowal od Ciebie prosby o teleportacje!"));
            return;
        }
        u.getTpa().add(p);
        p.sendMessage(ChatUtil.color("&fWyslales zaproszenie o teleport do gracza &c" + o.getName()));
        o.sendMessage(ChatUtil.color("&fGracz &d" + p.getName() + " &fchce sie przeteleportowac do Ciebie!"));
        o.sendMessage(ChatUtil.color("&fWpisz &d/tpaccept " + p.getName() + " &faby zaakceptowac!"));
    }
}
