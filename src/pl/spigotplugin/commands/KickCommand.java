package pl.spigotplugin.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.CombatUtil;

public class KickCommand extends Command {
    public KickCommand() { super("kick", "/kick <gracz> [powod]", ""); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            sender.sendMessage("&4Blad: &cGracz nie jest online!");
            return;
        }
        if (p.hasPermission("core.cmd.admin")) {
            sender.sendMessage("&4Blad: &cNie mozesz wyrzucic tego gracza!");
            return;
        }
        if (sender.getName().equalsIgnoreCase(p.getName())) {
            sender.sendMessage("&4Blad: &cNie mozesz wyrzucic sam siebie!");
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
        CombatUtil combat = CombatManager.getCombat(p);
        combat.setLastAsystTime(0L);
        combat.setPlayer(null);
        combat.setLastAsystPlayer(null);
        p.kickPlayer(ChatUtil.color(kick));
        Bukkit.broadcastMessage("&cGracz " + args[0] + " &czostal wyrzucony z serwera przez " + sender.getName() + " &cz powodem " + reason);
    }
}