package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.utils.DajUtil;

public class DajCommand extends Command {
    public DajCommand() { super("daj", "daj <gracz|*> <boyfarmer|antynogi|stoniarka|easycase|case611> <ilosc>", "spigot.daj", ""); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length != 3) {
            core.usage(sender, getUsage());
            return;
        }
        if (args[1].equalsIgnoreCase("boyfarmer")) {
            if (args[0].equals("*")) {
                int size = Integer.parseInt(args[2]);
                for (Player pp : Bukkit.getOnlinePlayers()) {
                    DajUtil.giveWithAmount(args[1].toLowerCase(), size, pp);
                }
            } else {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    sender.sendMessage("&cGracz jest offline");
                    return;
                }
                int size = Integer.parseInt(args[2]);
                DajUtil.giveWithAmount(args[1].toLowerCase(), size, o);
            }
            return;
        }
        if (args[1].equalsIgnoreCase("antynogi")) {
            if (args[0].equals("*")) {
                int size = Integer.parseInt(args[2]);
                for (Player pp : Bukkit.getOnlinePlayers()) {
                    DajUtil.giveWithAmount(args[1].toLowerCase(), size, pp);
                }
            } else {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    sender.sendMessage("&cGracz jest offline");
                    return;
                }
                int size = Integer.parseInt(args[2]);
                DajUtil.giveWithAmount(args[1].toLowerCase(), size, o);
            }
            return;
        }
        if (args[1].equalsIgnoreCase("stoniarka")) {
            if (args[0].equals("*")) {
                int size = Integer.parseInt(args[2]);
                for (Player pp : Bukkit.getOnlinePlayers()) {
                    DajUtil.giveWithAmount(args[1].toLowerCase(), size, pp);
                }
            } else {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    sender.sendMessage("&cGracz jest offline");
                    return;
                }
                int size = Integer.parseInt(args[2]);
                DajUtil.giveWithAmount(args[1].toLowerCase(), size, o);
            }
            return;
        }
        if (args[1].equalsIgnoreCase("easycase")) {
            if (args[0].equals("*")) {
                int size = Integer.parseInt(args[2]);
                for (Player pp : Bukkit.getOnlinePlayers()) {
                    DajUtil.giveWithAmount(args[1].toLowerCase(), size, pp);
                }
            } else {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    sender.sendMessage("&cGracz jest offline");
                    return;
                }
                int size = Integer.parseInt(args[2]);
                DajUtil.giveWithAmount(args[1].toLowerCase(), size, o);
            }
            return;
        }
        if (args[1].equalsIgnoreCase("case611")) {
            if (args[0].equals("*")) {
                int size = Integer.parseInt(args[2]);
                for (Player pp : Bukkit.getOnlinePlayers()) {
                    DajUtil.giveWithAmount(args[1].toLowerCase(), size, pp);
                }
            } else {
                Player o = Bukkit.getPlayer(args[0]);
                if (o == null) {
                    sender.sendMessage("&cGracz jest offline");
                    return;
                }
                int size = Integer.parseInt(args[2]);
                DajUtil.giveWithAmount(args[1].toLowerCase(), size, o);
            }
        }
    }
}
