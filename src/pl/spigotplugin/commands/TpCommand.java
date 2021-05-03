package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;

public class TpCommand extends PlayerCommand {
    public TpCommand() { super("tp", "tp <do kogo/x,y,z>", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        switch (args.length) {
            case 1: {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    p.sendMessage("&4Blad: &cGracz jest offline!");
                    return;
                }
                p.teleport(o.getLocation());
                p.sendMessage("&6Zostales przeteleportowany do gracza &c" + o.getName());
                return;
            }
            case 2: {
                if (!p.hasPermission("core.cmd.admin")) {
                    p.sendMessage("&8\u00bb &cNie masz dostepu!");
                    return;
                }
                Double x = Double.parseDouble(args[0]);
                Double y = Double.parseDouble(args[1]);
                Double z = Double.parseDouble(args[2]);
                if (x.isNaN() && y.isNaN() && z.isNaN()) {
                    p.sendMessage("&4Blad: &cKoordynaty musza byc liczbami!");
                    return;
                }
                p.teleport(new Location(p.getWorld(), x, y, z));
                p.sendMessage("&6Zostales przeteleportowany na kordy &7X: &c" + x + " &7Y: &c" + y + " &7Z: &c" + z);
                return;
            }
            case 3: {
                if (!p.hasPermission("core.cmd.admin")) {
                    p.sendMessage("&8\u00bb &cNie masz dostepu!");
                    return;
                }
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    p.sendMessage("&4Blad: &cGracz jest offline!");
                    return;
                }
                Double x = Double.parseDouble(args[1]);
                Double y = Double.parseDouble(args[2]);
                Double z = Double.parseDouble(args[3]);
                if (x.isNaN() && y.isNaN() && z.isNaN()) {
                    p.sendMessage("&4Blad: &cKoordynaty musza byc liczbami!");
                    return;
                }
                o.teleport(new Location(o.getWorld(), x, y, z));
                o.sendMessage("&6Zostales przeteleportowany na kordy &7X: &c" + x + " &7Y: &c" + y + " &7Z: &c" + z + " &6przez &c" + p.getName());
                p.sendMessage("&7\u00bb &6Przeteleportowales gracza &c" + o.getName() + " &ena kordy &7X: &c" + x + " &7Y: &c" + y + " &7Z: &c" + z);
                return;
            }
            default: {
                GlobalMessage.usage(p, getUsage());
                break;
            }
        }
    }
}
