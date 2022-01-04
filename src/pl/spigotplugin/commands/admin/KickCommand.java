package pl.spigotplugin.commands.admin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.GroupUtil;

public class KickCommand extends Command {
    public KickCommand() { super("kick", RankType.ADMIN); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            core.usage(sender, "kick <gracz> [powod]");
            return;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            sender.sendMessage("&cGracz nie jest online!");
            return;
        }
        if (!GroupUtil.have(p, RankType.ADMIN)) {
            sender.sendMessage("&cNie mozesz wyrzucic tego gracza!");
            return;
        }
        if (sender.getName().equalsIgnoreCase(p.getName())) {
            sender.sendMessage("&cNie mozesz wyrzucic sam siebie!");
            return;
        }
        String reason = "Brak!";
        if (args.length > 1) {
            reason = StringUtils.join(args, " ", 1, args.length);
        }
        String kick = "\n&8&m------===------" +
                "\n&7Zostales wyrzocony z serwera przez &c" + sender.getName() +
                "\n&7Powod: &c" + reason +
                "\n&8&m------===------";
        CombatManager.clear(p.getPlayer());
        p.kickPlayer(ChatUtil.color(kick));
        Bukkit.broadcastMessage("&cGracz " + args[0] + " zostal wyrzucony z serwera przez " + sender.getName() + " z powodem " + reason);
    }
}