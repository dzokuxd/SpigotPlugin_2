package pl.spigotplugin.commands.player;

import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;

public class PayCommand extends PlayerCommand {
    public PayCommand() { super("pay", "pay <nick> <ilosc coinsow>", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 2) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        if (!ChatUtil.isInteger(args[1])) {
            p.sendMessage("&cWartosc nie jest liczba!");
            return;
        }
        int value = Integer.parseInt(args[1]);
        if (value <= 0) {
            p.sendMessage("&cWartosc musi byc wieksza od 0!");
            return;
        }
        User sender = UserManager.getUser(p);
        if (args[0].equalsIgnoreCase(p.getName())) {
            p.sendMessage("&cNie mozesz przelac coinsow samemu sobie!");
            return;
        }
        User user = UserManager.getUser(args[0]);
        if (user == null) {
            p.sendMessage("&4Gracz nie ma w bazie danych!");
            return;
        }
        if (sender.getCoins() > value) {
            user.addCoins(value);
            sender.removeCoins(value);
            p.sendMessage("&6Przelales &c" + args[1] + " &6coinsow dla gracza &c" + user.getName() + "&7!");
        } else {
            p.sendMessage("&cNie posiadasz wystarczajacej liczby coinsow!");
        }
    }
}
