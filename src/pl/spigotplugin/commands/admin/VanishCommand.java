package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.GroupUtil;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand extends PlayerCommand {
    public VanishCommand() {
        super("vanish", RankType.HELPER, "v");
    }

    public static final List<Player> using = new ArrayList<>();

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            if (using.contains(p)) {
                using.remove(p);
                if (p.getGameMode() == GameMode.SPECTATOR) {
                    p.setGameMode(GroupUtil.have(p, RankType.MOD) ? GameMode.CREATIVE : GameMode.SURVIVAL);
                    p.sendMessage(core.VANISH_FALSE);
                    for (Player admins : Bukkit.getOnlinePlayers()) {
                        if (GroupUtil.have(admins, RankType.HELPER)) {
                            admins.sendMessage(core.VANISH_SEEFALSE.replace("{VANISHPLAYER}", p.getName()));
                        }
                    }
                }
            } else {
                using.add(p);
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(core.VANISH_TRUE);
                for (Player admins : Bukkit.getOnlinePlayers()) {
                    if (GroupUtil.have(admins, RankType.HELPER)) {
                        admins.sendMessage(core.VANISH_SEETRUE.replace("{VANISHPLAYER}", p.getName()));
                    }
                }
            }
        }
    }
}
