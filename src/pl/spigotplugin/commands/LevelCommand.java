package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;

public class LevelCommand extends PlayerCommand {
    public LevelCommand() { super("level", "lvl", "", "lvl"); }

    @Override
    public void onCommand(Player p, String[] args) {
        User u = null;
        if (args.length == 0) {
            u = UserManager.getUser(p);
        } else {
            u = UserManager.getUser(args[0]);
        }
        if (u == null) {
            p.sendMessage("&4Blad: &cGracz nie istnieje!");
            return;
        }
        int wzor = (u.getLvl() * 100 * u.getLvl()) - u.getExp();
        if (u.getName().equalsIgnoreCase(p.getName())) {
            p.sendMessage("&7&m------&r &cTwoje Statystyki &7&m------&r");
            p.sendMessage("&7\u00bb &6Aktualnie posiadasz &c" + u.getExp() + " &6pkt czyli &c" + u.getLvl() + " &6poziom!");
            p.sendMessage("&7\u00bb &6Do nastepnego poziomu brakuje Ci: &c" + wzor);
            p.sendMessage("&7&m------&r &cTwoje Statystyki &7&m------&r");
        } else {
            p.sendMessage("&7&m------&r &cStatystyki Gracza &c" + u.getName() + " &7&m------&r");
            p.sendMessage("&7\u00bb &6Aktualnie posiada &c" + u.getExp() + " &6pkt czyli &c" + u.getLvl() + " &6poziom!");
            p.sendMessage("&7\u00bb &6Do nastepnego poziomu brakuje mu &c" + wzor);
            p.sendMessage("&7&m------&r &cStatystyki Gracza &c" + u.getName() + " &7&m------&r");
        }
    }
}