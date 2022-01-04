package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.objects.user.Backup;
import pl.spigotplugin.utils.ChatUtil;

import java.sql.SQLException;

public class BackupCommand extends PlayerCommand {
    public BackupCommand() { super("backup", RankType.ADMIN); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            core.usage(p, "backup <gracz>");
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage(ChatUtil.color("&c" + args[0] + " nie istnieje!"));
            return;
        }
        try {
            Backup.getList(o, p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}