package pl.spigotplugin.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

public class TpaCommand extends PlayerCommand {
    public TpaCommand() { super("tpa", "tpa <gracz>", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length != 1) {
            core.usage(p, getUsage());
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage("&cGracz offline!");
            return;
        }
        User u = UserManager.getUser(o);
        if (u == null) {
            return;
        }
        String nickja = args[0];
        if (nickja.equalsIgnoreCase(p.getName())) {
            p.sendMessage("&cNie mozesz teleportowac sie sam do siebie! ;(");
            return;
        }
        if (!statues.MANAGE_TPA) {
            p.sendMessage("&cAktualnie tpa jest wylaczone");
            return;
        }
        if (u.getTpa().contains(p)) {
            p.sendMessage("&cWyslales juz zaproszenie o teleport do gracza " + o.getName() + "!");
            return;
        }
        if (u.isIgnoreTpa(p) && !p.hasPermission("spigotplugin.ignore")) {
            p.sendMessage("&cTen gracz zablokowal od Ciebie prosby o teleportacje!");
            return;
        }
        u.getTpa().add(p);
        p.sendMessage("&7\u00bb &6Wyslales zaproszenie o teleport do gracza &c" + o.getName() + "&7!");
        o.sendMessage("&7\u00bb &6Gracz &c" + p.getName() + " &6chce sie przeteleportowac do Ciebie!");
        o.sendMessage("&7\u00bb &6Wpisz &c/tpaccept " + p.getName() + " &6aby zaakceptowac!");
    }
}
