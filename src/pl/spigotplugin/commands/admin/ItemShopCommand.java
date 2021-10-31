package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.spigotplugin.api.Command;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;

public class ItemShopCommand extends Command {
    public ItemShopCommand() { super("itemshop", "is <gracz> <VIP, SVIP, UNBAN, COINS, TURBODROP, EASYCASE, CASE611>", "spigot.itemshop","is"); }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            core.usage(sender, getUsage());
            return;
        }
        String name = args[0];
        switch (args[1]) {
            case "vip": {
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&fGracz &d" + name + " &fzakupil range &6&lVIP");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&fNasz sklep: &dwww." + (statues.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("         &aDziekujemy za wsparcie!");
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + name + " group set vip");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "voucher " + name + " turbo 1");
                return;
            }
            case "svip": {
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&fGracz &d" + name + " &fzakupil range &6&lSvip");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&fNasz sklep: &dwww." + (statues.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("         &aDziekujemy za wsparcie!");
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + name + " group set svip");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "voucher " + name + " turbo 1");
                return;
            }
            case "unban": {
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&fGracz &d" + name + " &fzakupil &c&lUnbana");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("&fNasz sklep: &dwww." + (statues.IP));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("         &aDziekujemy za wsparcie!");
                Bukkit.broadcastMessage("&7&m-------------------------------------");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unban " + name);
                return;
            }
            case "coins": {
                if (args.length < 3) {
                    sender.sendMessage("&c/is <gracz> coins <ilosc>");
                }
                if (!ChatUtil.isInteger(args[2])) {
                    sender.sendMessage("&cWartosc nie jest liczba!");
                    return;
                }
                int amout = Integer.parseInt(args[2]);
                User u = UserManager.getUser(name);
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("&fGracz &d" + name + " &fzakupil &c&lCoinsy! x" + amout));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("&fNasz sklep: &dwww." + (statues.IP)));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("         &aDziekujemy za wsparcie!"));
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                u.addCoins(amout);
                u.save();
                return;
            }
            case "easycase": {
                if (args.length < 3) {
                    sender.sendMessage("&c/is <gracz> easycase <ilosc>");
                }
                if (!ChatUtil.isInteger(args[2])) {
                    sender.sendMessage("&cWartosc nie jest liczba!");
                    return;
                }
                int amout = Integer.parseInt(args[2]);
                User u = UserManager.getUser(name);
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("&fGracz &d" + name + " &fzakupil &c&lEasyCase'y! x" + amout));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("&fNasz sklep: &dwww." + (statues.IP)));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("         &aDziekujemy za wsparcie!"));
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                u.addEasycase(amout);
                u.save();
                return;
            }
            case "case611": {
                if (args.length < 3) {
                    sender.sendMessage("&c/is <gracz> case633 <ilosc>");
                }
                if (!ChatUtil.isInteger(args[2])) {
                    sender.sendMessage("&cWartosc nie jest liczba!");
                    return;
                }
                int amout = Integer.parseInt(args[2]);
                User u = UserManager.getUser(name);
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("&fGracz &d" + name + " &fzakupil &c&lCase'y 6/1/1! x" + amout));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("&fNasz sklep: &dwww." + (statues.IP)));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("         &aDziekujemy za wsparcie!"));
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                u.addCase611(amout);
                u.save();
                return;
            }
            case "turbodrop": {
                User u = UserManager.getUser(args[0]);
                if (u == null) {
                    sender.sendMessage("&cGracz nie istnieje!");
                    return;
                }
                if (args.length < 3) {
                    sender.sendMessage("&c/is <gracz> turbo <ilosc>");
                    return;
                }
                if (!ChatUtil.isInteger(args[2])) {
                    sender.sendMessage("&cWartosc nie jest liczba!");
                    return;
                }
                int amout = Integer.parseInt(args[2]);
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("&fGracz &d" + name + " &fzakupil &c&lTurboDrop " + amout + "m"));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("&fNasz sklep: &dwww." + (statues.IP)));
                Bukkit.broadcastMessage((""));
                Bukkit.broadcastMessage(("         &aDziekujemy za wsparcie!"));
                Bukkit.broadcastMessage(("&7&m-------------------------------------"));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "events turbo" + name + " " + amout + "m");
                return;
            }
            default: {
                sender.sendMessage("&7\u00bb &7Dostepne uslugi: &bVIP, SVIP, UNBAN, COINS, TURBODROP, EASYCASE, 6/1/1");
            }
        }
    }
}
