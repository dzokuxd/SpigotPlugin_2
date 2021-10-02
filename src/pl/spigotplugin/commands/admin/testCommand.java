package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.Backup;
import pl.spigotplugin.objects.user.User;

public class testCommand extends PlayerCommand {
    public testCommand() {super("test", "", "");}

    @Override
    public void onCommand(Player p, String[] args) {
        User u = UserManager.getUser(p);
        p.sendMessage("1");
        new Backup(p, desc(p));
        p.sendMessage("2");
        u.putForSave();
        p.sendMessage("3");
        u.save();
        p.sendMessage("4");
    }
    private String desc(Player p) {
        EntityDamageEvent e = p.getLastDamageCause();
        String cause;
        if (e == null) {
            cause = "logout";
        } else if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (p.getKiller() != null) {
                cause = p.getKiller().getName();
            } else {
                cause = "mob";
            }
        } else {
            cause = p.getLastDamageCause().getCause().name().toLowerCase();
        }
        return cause;
    }
}
