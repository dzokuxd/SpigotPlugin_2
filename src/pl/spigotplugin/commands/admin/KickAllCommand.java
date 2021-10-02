package pl.spigotplugin.commands.admin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.CombatManager;
import pl.spigotplugin.utils.ChatUtil;

public class KickAllCommand extends Command {
    public KickAllCommand() { super("kickall", "/kickall powod", ""); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        String kick = "\n&cZostales wyrzocony z serwera przez: " + sender.getName() + "\n&cPowod: " + StringUtils.join(args, " ");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("core.cmd.mod")) {
                CombatManager.getFightMap().clear();
                p.kickPlayer(ChatUtil.color(kick));
            }
        }
        sender.sendMessage("&7\u00bb &aWyrzucono wszystkich graczy z serwera!");
    }
}
