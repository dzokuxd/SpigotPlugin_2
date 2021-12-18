package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.utils.BossUtil;

public class SpawnBossCommand extends PlayerCommand {
    public SpawnBossCommand() {super("spawnboss", "spawnboss <nazwa> <hp>", "spigot.spawnboss");}

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length != 2) {
            core.usage(p, getUsage());
            return;
        }
        String name = args[0];
        int hp;
        try {
            hp = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            p.sendMessage("&c/spawnboss <nazwa> <hp>");
            return;
        }
        BossUtil.spawnBoss(p.getLocation(), name, hp);
    }
}
