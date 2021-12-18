package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.objects.user.Backup;

import java.sql.SQLException;

public class BackupCommand extends PlayerCommand {
    public BackupCommand() { super("backup", "backup <gracz>", "spigot.backup"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            core.usage(p, getUsage());
            return;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            p.sendMessage("&c" + args[0] + " nie istnieje!");
            return;
        }
        try {
            Backup.getList(o, p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}