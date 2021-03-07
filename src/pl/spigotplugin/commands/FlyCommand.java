package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class FlyCommand extends PlayerCommand {
    public FlyCommand() { super("fly", "/fly (gracz)", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.setAllowFlight(!p.getAllowFlight());
            p.sendMessage(GlobalMessage.MESSAGES_FLYSTATUS.replace("{FLY}", (p.getAllowFlight() ? "&aon" : "&coff")));
            return;
        }
        Player x = Bukkit.getPlayer(args[0]);
        if (x == null) {
            p.sendMessage("&cGracz jest offline");
            return;
        }
        x.setAllowFlight(!x.getAllowFlight());
        x.sendMessage(GlobalMessage.MESSAGES_FLYPLAYER.replace("{FLYPLAYER}", (x.getAllowFlight() ? "&aon" : "&coff")));
    }
}
