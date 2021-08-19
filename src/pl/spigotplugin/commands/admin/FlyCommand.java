package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class FlyCommand extends PlayerCommand {
    public FlyCommand() { super("fly", "/ly <gracz>", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.setAllowFlight(!p.getAllowFlight());
            p.sendMessage(GlobalMessage.FLY_YOU.replace("{FLY}", (p.getAllowFlight() ? "&aon" : "&coff")));
            return;
        }
        Player x = Bukkit.getPlayer(args[0]);
        if (x == null) {
            p.sendMessage("&cGracz jest offline");
            return;
        }
        x.setAllowFlight(!x.getAllowFlight());
        x.sendMessage(GlobalMessage.FLY_PLAYER.replace("{FLYPLAYER}", (x.getAllowFlight() ? "&aon" : "&coff")));
    }
}
