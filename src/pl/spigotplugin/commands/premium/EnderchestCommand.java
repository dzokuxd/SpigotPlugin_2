package pl.spigotplugin.commands.premium;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.utils.EnderChestUtil;

public class EnderchestCommand extends PlayerCommand {
    public EnderchestCommand() {super("enderchest", "ec", "spigot.powiekszonyec", "ec");}

    @Override
    public void onCommand(Player p, String[] args) {
        EnderChestUtil.open(p);
        if (p.hasPermission("ezhc.admin.enderchest") && args.length == 1) {
            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage("&cGracz jest offline!");
                return;
            }
            EnderChestUtil.openOther(p, target);
        }
    }
}
