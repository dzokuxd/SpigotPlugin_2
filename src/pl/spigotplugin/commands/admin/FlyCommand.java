package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;

public class FlyCommand extends PlayerCommand {
    public FlyCommand() { super("fly", "/fly <gracz>", "spigot.fly"); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.setAllowFlight(!p.getAllowFlight());
            p.sendMessage(core.FLY_YOU.replace("{FLY}", (p.getAllowFlight() ? "&awlaczone" : "&cwylaczone")));
            return;
        }
        Player x = Bukkit.getPlayer(args[0]);
        if (x == null) {
            p.sendMessage("&cGracz jest offline");
            return;
        }
        x.setAllowFlight(!x.getAllowFlight());
        x.sendMessage(core.FLY_PLAYER.replace("{FLYPLAYER}",x.getName().replace("{FLYSTATUS}",(x.getAllowFlight() ? "&awlaczone" : "&cwylaczone"))));
    }
}
