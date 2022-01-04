package pl.spigotplugin.commands.admin;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.BossUtil;
import pl.spigotplugin.utils.ChatUtil;

public class SpawnBossCommand extends PlayerCommand {
    public SpawnBossCommand() {super("spawnboss", RankType.HA);}

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length != 2) {
            core.usage(p, "spawnboss <nazwa> <hp>");
            return;
        }
        String name = args[0];
        int hp;
        try {
            hp = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            p.sendMessage(ChatUtil.color("&c/spawnboss <nazwa> <hp>"));
            return;
        }
        BossUtil.spawnBoss(p.getLocation(), name, hp);
    }
}
