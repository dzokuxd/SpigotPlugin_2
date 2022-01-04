package pl.spigotplugin.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.component.Teleporter;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.holder.LocationHolder;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.GroupUtil;

public class SpawnCommand extends PlayerCommand {

    public  SpawnCommand() {
        super("spawn", RankType.GRACZ);
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            if (!statues.MANAGE_SPAWN) {
                p.sendMessage(ChatUtil.color("&cAktualnie teleport na spawn jest wylaczony!"));
                return;
            }
            Teleporter.sendRequest(p, LocationHolder.SPAWN);
            return;
        }
        if (!GroupUtil.have(p, RankType.HELPER)) {
            p.sendMessage(ChatUtil.color("&cNie posiadasz uprawnien!"));
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            p.sendMessage(ChatUtil.color("&cGracz offline!"));
            return;
        }
        target.teleport(LocationHolder.SPAWN);
    }
}
