package pl.spigotplugin.commands.player;

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
            p.sendMessage("&cGracz nie istnieje!");
            return;
        }
        int wzor = (u.getLvl() * 100 * u.getLvl()) - u.getExp();
        if (u.getName().equalsIgnoreCase(p.getName())) {
            p.sendMessage("&7&m------&r &fTwoje Statystyki &7&m------&r");
            p.sendMessage("&7\u00bb &fAktualnie posiadasz &d" + u.getExp() + " &fpkt czyli &d" + u.getLvl() + " &fpoziom!");
            p.sendMessage("&7\u00bb &fDo nastepnego poziomu brakuje Ci: &d" + wzor);
            p.sendMessage("&7&m------&r &fTwoje Statystyki &7&m------&r");
        } else {
            p.sendMessage("&7&m------&r &fStatystyki &c" + u.getName() + " &7&m------&r");
            p.sendMessage("&7\u00bb &fAktualnie posiada &d" + u.getExp() + " &fpkt czyli &d" + u.getLvl() + " &fpoziom!");
            p.sendMessage("&7\u00bb &fDo nastepnego poziomu brakuje mu &d" + wzor);
            p.sendMessage("&7&m------&r &fStatystyki &c" + u.getName() + " &7&m------&r");
        }
    }
}