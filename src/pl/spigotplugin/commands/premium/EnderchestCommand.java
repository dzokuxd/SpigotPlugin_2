package pl.spigotplugin.commands.premium;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.EnderChestUtil;
import pl.spigotplugin.utils.GroupUtil;

public class EnderchestCommand extends PlayerCommand {
    public EnderchestCommand() {super("enderchest", RankType.VIP, "ec");}

    @Override
    public void onCommand(Player p, String[] args) {
        EnderChestUtil.open(p);
        if (!GroupUtil.have(p, RankType.ADMIN) && args.length == 1) {
            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ChatUtil.color("&cGracz jest offline!"));
                return;
            }
            EnderChestUtil.openOther(p, target);
        }
    }
}
