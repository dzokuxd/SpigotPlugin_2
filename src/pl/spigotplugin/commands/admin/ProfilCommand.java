package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.menu.ProfilMenu;
import pl.spigotplugin.objects.user.User;

public class ProfilCommand extends PlayerCommand {
    public ProfilCommand() {
        super("profil", RankType.HA);
    }

    @Override
    public void onCommand(Player p, String[] args) {
        User u = (args.length==1 ? UserManager.getUser(args[0]) : UserManager.getUser(p));
        if(u==null){
            p.sendMessage("&cTakiego gracza nie bylo nigdy na serwerze");
            return;
        }
        ProfilMenu.show(p, u);
    }
}
