package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.GroupUtil;

public class TpCommand extends PlayerCommand {
    public TpCommand() { super("tp", RankType.HELPER); }

    @Override
    public void onCommand(Player p, String[] args) {
        switch (args.length) {
            case 0: {
                core.usage(p, "tp <do kogo/x,y,z>");
                return;
            }
            case 1: {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    p.sendMessage("&cGracz jest offline!");
                    return;
                }
                p.teleport(o.getLocation());
                p.sendMessage("&fZostales przeteleportowany do gracza &d" + o.getName());
                return;
            }
            case 2: {
                if (!GroupUtil.have(p, RankType.MOD)) {
                    p.sendMessage("&cNie masz dostepu!");
                    return;
                }
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    p.sendMessage("&cGracz jest offline!");
                    return;
                }
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);
                if (Double.isNaN(x) && Double.isNaN(y) && Double.isNaN(z)) {
                    p.sendMessage("&fKoordynaty musza byc liczbami!");
                    return;
                }
                p.teleport(new Location(p.getWorld(), x, y, z));
                p.sendMessage("&fZostales przeteleportowany na kordy &dX: " + x + "Y: " + y + "Z: " + z);
                return;
            }
            case 3: {
                if (!GroupUtil.have(p, RankType.MOD)) {
                    p.sendMessage("&cNie masz dostepu!");
                    return;
                }
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);
                if (Double.isNaN(x) && Double.isNaN(y) && Double.isNaN(z)) {
                    p.sendMessage("&cKoordynaty musza byc liczbami!");
                    return;
                }
                p.teleport(new Location(p.getWorld(), x, y, z));
                p.sendMessage("&fZostales przeteleportowany na kordy &dX: " + x + " Y: " + y + " Z: " + z);
                return;
            }
            case 4: {
                if (!GroupUtil.have(p, RankType.MOD)) {
                    p.sendMessage("&cNie masz dostepu!");
                    return;
                }
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    p.sendMessage("&cGracz jest offline!");
                    return;
                }
                double x = Double.parseDouble(args[1]);
                double y = Double.parseDouble(args[2]);
                double z = Double.parseDouble(args[3]);
                if (Double.isNaN(x) && Double.isNaN(y) && Double.isNaN(z)) {
                    p.sendMessage("&cKoordynaty musza byc liczbami!");
                    return;
                }
                o.teleport(new Location(o.getWorld(), x, y, z));
                o.sendMessage("&fZostales przeteleportowany na kordy &dX: " + x + " Y: " + y + " Z: " + z + " &fprzez &d" + p.getName());
                p.sendMessage("&fPrzeteleportowales gracza &d" + o.getName() + " &fna kordy &dX: " + x + " Y: " + y + " Z: " + z);
                return;
            }
            default: {
                core.usage(p, getUsage());
                break;
            }
        }
    }
}
