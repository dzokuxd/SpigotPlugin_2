package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.component.Teleporter;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

public class TpacceptCommand extends PlayerCommand {
    public TpacceptCommand() { super("tpaccept", "", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage("&4Blad: &cGracz jest offline!");
            return;
        }
        User u = UserManager.getUser(p);
        if (u == null) {
            return;
        }
        if (u.getTpa().contains(o)) {
            Teleporter.sendRequest(o, p.getLocation());
            u.getTpa().remove(o);
            p.sendMessage("&6Zaakceptowales prosbe o teleport do ciebie od gracza &c" + o.getName() + "&7!");
            o.sendMessage("&6Gracz &c" + p.getName() + " &6zaakceptowal twoja prosbe o teleport do niego!");
            return;
        }
        p.sendMessage("&4Blad: &cNie masz zaproszenia do teleportacji od gracza " + o.getName() + "&7!");
    }
}
