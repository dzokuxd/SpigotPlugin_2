package pl.spigotplugin.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.component.Teleporter;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;

public class TpacceptCommand extends PlayerCommand {
    public TpacceptCommand() { super("tpaccept", RankType.GRACZ); }

    @Override
    public void onCommand(Player p, String[] args) {
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage(ChatUtil.color("&cGracz jest offline!"));
            return;
        }
        User u = UserManager.getUser(p);
        if (u == null) {
            return;
        }
        if (u.getTpa().contains(o)) {
            Teleporter.sendRequest(o, p.getLocation());
            u.getTpa().remove(o);
            p.sendMessage(ChatUtil.color("&fZaakceptowales prosbe o teleport do ciebie od gracza &d" + o.getName()));
            o.sendMessage(ChatUtil.color("&fGracz &d" + p.getName() + " &fzaakceptowal twoja prosbe o teleport do niego!"));
            return;
        }
        p.sendMessage(ChatUtil.color("&cNie masz zaproszenia do teleportacji od gracza " + o.getName()));
    }
}
