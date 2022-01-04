package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;

public class FlyCommand extends PlayerCommand {
    public FlyCommand() { super("fly", RankType.HELPER); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.setAllowFlight(!p.getAllowFlight());
            p.sendMessage(core.FLY_YOU.replace("{FLY}", (p.getAllowFlight() ? "&awlaczone" : "&cwylaczone")));
            return;
        }
        Player x = Bukkit.getPlayer(args[0]);
        if (x == null) {
            p.sendMessage(ChatUtil.color("&cGracz jest offline"));
            return;
        }
        x.setAllowFlight(!x.getAllowFlight());
        x.sendMessage(core.FLY_PLAYER.replace("{FLYPLAYER}",x.getName().replace("{FLYSTATUS}",(x.getAllowFlight() ? "&awlaczone" : "&cwylaczone"))));
    }
}
