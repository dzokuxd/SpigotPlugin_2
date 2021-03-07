package pl.spigotplugin.commands;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.utils.TNTUtil;

public class TNTStatusCommand extends PlayerCommand {
    public TNTStatusCommand() { super("tnt", "tnt", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage("&cTnT &7dziala w godzinach od:&c "+ Config.CUBOID_TNT_OD + " &7do: &c" + Config.CUBOID_TNT_DO);
        p.sendMessage("&cTnT &7wybucha ponizej: &c50 poziomu");
        p.sendMessage("&7Status: " + (TNTUtil.isBetween() ? "&aWlaczone" : "&cWylaczone"));
    }
}
