package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.utils.DajUtil;

public class DajCommand extends Command {
    public DajCommand() { super("daj", "daj gracz|* boyfarmer|antynogi|stoniarka|case|633  ilosc", "", ""); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length != 3) {
            sender.sendMessage("&7\u00bb &6Poprawne uzycie: &c" + getUsage());
            return;
        }
        if (args[1].equalsIgnoreCase("boyfarmer")) {
            if (args[0].equals("*")) {
                for (Player pp : Bukkit.getOnlinePlayers()) {
                    int size = Integer.parseInt(args[1]);
                    DajUtil.giveWithAmount(args[2].toLowerCase(), size, pp);
                }
            } else {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    sender.sendMessage("&4Blad: &cGracz jest offline");
                    return;
                }
                int size = Integer.parseInt(args[1]);
                DajUtil.giveWithAmount(args[2].toLowerCase(), size, o);
            }
            return;
        }
        if (args[1].equalsIgnoreCase("antynogi")) {
            if (args[0].equals("*")) {
                for (Player pp : Bukkit.getOnlinePlayers()) {
                    int size = Integer.parseInt(args[1]);
                    DajUtil.giveWithAmount(args[2].toLowerCase(), size, pp);
                }
            } else {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    sender.sendMessage("&4Blad: &cGracz jest offline");
                    return;
                }
                int size = Integer.parseInt(args[1]);
                DajUtil.giveWithAmount(args[2].toLowerCase(), size, o);
            }
            return;
        }
        if (args[1].equalsIgnoreCase("stoniarka")) {
            if (args[0].equals("*")) {
                for (Player pp : Bukkit.getOnlinePlayers()) {
                    int size = Integer.parseInt(args[1]);
                    DajUtil.giveWithAmount(args[2].toLowerCase(), size, pp);
                }
            } else {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    sender.sendMessage("&4Blad: &cGracz jest offline");
                    return;
                }
                int size = Integer.parseInt(args[1]);
                DajUtil.giveWithAmount(args[2].toLowerCase(), size, o);
            }
            return;
        }
        if (args[1].equalsIgnoreCase("case")) {
            if (args[0].equals("*")) {
                for (Player pp : Bukkit.getOnlinePlayers()) {
                    int size = Integer.parseInt(args[1]);
                    DajUtil.giveWithAmount(args[2].toLowerCase(), size, pp);
                }
            } else {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    sender.sendMessage("&4Blad: &cGracz jest offline");
                    return;
                }
                int size = Integer.parseInt(args[1]);
                DajUtil.giveWithAmount(args[2].toLowerCase(), size, o);
            }
            return;
        }
        if (args[1].equalsIgnoreCase("633")) {
            if (args[0].equals("*")) {
                for (Player pp : Bukkit.getOnlinePlayers()) {
                    int size = Integer.parseInt(args[1]);
                    DajUtil.giveWithAmount(args[2].toLowerCase(), size, pp);
                }
            } else {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    sender.sendMessage("&4Blad: &cGracz jest offline");
                    return;
                }
                int size = Integer.parseInt(args[1]);
                DajUtil.giveWithAmount(args[2].toLowerCase(), size, o);
            }//TODO napraw all
        }
    }
}
