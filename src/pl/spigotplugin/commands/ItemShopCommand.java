package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;

public class ItemShopCommand extends Command {
    public ItemShopCommand() { super("itemshop", "is <gracz> <VIP, SVIP, UNBAN, COINS, TURBODROP, CASE, 633>", "","is"); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            GlobalMessage.usage(sender, getUsage());
            return;
        }
        String name = args[0];
        switch (args[1]) {
            case "vip": {
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &6Gracz &c" + name + " &6zakupil range &6&lVIP");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &6Nasz sklep: &cwww." + (Config.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("         &7\u00bb &cDziekujemy za wsparcie!");
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + name + " group set vip");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "voucher " + name + " turbo 1");
                return;
            }
            case "svip": {
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &6Gracz &c" + name + " &6zakupil range &6&lSvip");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &6Nasz sklep: &cwww." + (Config.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("         &7\u00bb &cDziekujemy za wsparcie!");
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + name + " group set svip");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "voucher " + name + " turbo 1");
                return;
            }
            case "unban": {
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&7\u00bb &6Gracz &c" + name + " &6zakupil &c&lUnbana");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(" &7\u00bb &6Nasz sklep: &cwww." + (Config.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("         &7\u00bb &cDziekujemy za wsparcie!");
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unban " + name);
                return;
            }
            case "coins": {
                if (args.length < 3) {
                    sender.sendMessage("/is <gracz> coins <ilosc>");
                }
                if (!ChatUtil.isInteger(args[2])) {
                    sender.sendMessage("&cWartosc nie jest liczba!");
                    return;
                }
                int amout = Integer.parseInt(args[2]);
                User u = UserManager.getUser(name);
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("&7\u00bb &6Gracz &c" + name + " &6zakupil &c&lCoinsy! x" + amout));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage((" &7\u00bb &6Nasz sklep: &cwww." + (Config.IP)));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("         &7\u00bb &cDziekujemy za wsparcie!"));
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                /*u.addCoins(amout);*/
                u.save();
                return;
            }//TODO widac
            case "turbodrop": {
                User u = UserManager.getUser(args[0]);
                if (u == null) {
                    sender.sendMessage("&4Blad: &cGracz nie istnieje!");
                    return;
                }
                if (args.length < 3) {
                    sender.sendMessage("/is <gracz> turbo <ilosc>");
                    return;
                }
                if (!ChatUtil.isInteger(args[2])) {
                    sender.sendMessage("&cWartosc nie jest liczba!");
                    return;
                }
                int amout = Integer.parseInt(args[2]);
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("&7\u00bb &6Gracz &c" + name + " &6zakupil &c&lTurboDrop " + amout + "m"));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage((" &7\u00bb &7Nasz sklep: &cwww." + (Config.IP)));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("         &7\u00bb &cDziekujemy za wsparcie!"));
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "events turbo" + name + " " + amout + "m");
                return;
            }
            default: {
                sender.sendMessage("&7\u00bb &7Dostepne uslugi: &bVIP, SVIP, UNBAN, COINS, TURBODROP, CASE, 6/3/3");
            }//TODO DODAC CASY
        }
    }
}
