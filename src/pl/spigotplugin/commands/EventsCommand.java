package pl.spigotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.Config;
import pl.spigotplugin.configs.GlobalMessage;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.DataUtil;

public class EventsCommand extends PlayerCommand {
    public EventsCommand() { super("events", "events (<turbo all/gracz czas>/case/kill/beacon) set (czas)", ""); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 2) {
            GlobalMessage.usage(p, getUsage());
            return;
        }
        switch (args[0].toLowerCase()) {
            case "beacon": {
                if (args.length <=2) {
                    p.sendMessage("/events beacon set czas");
                    return;
                }
                long time = Config.EVENTS_BEACON = DataUtil.parseDateDiff(args[2], true);
                Config.saveConfig();
                Bukkit.broadcastMessage("&6Na serwerze zostal aktywowany event &cDrop beacona za zabicie gracza z gildii &6do &c" + DataUtil.getDate(time));
                return;
            }
            case "kill": {
                if (args.length <=2) {
                    p.sendMessage("/events kill set czas");
                    return;
                }
                long time = Config.EVENTS_KILL = DataUtil.parseDateDiff(args[2], true);
                Config.saveConfig();
                Bukkit.broadcastMessage("&6Na serwerze zostal aktywowany event &cDrop skrzynek za zabicie gracza z gildii &6do &c" + DataUtil.getDate(time));
                return;
            }
            case "case": {
                if (args.length <=2) {
                    p.sendMessage("/events case set czas");
                    return;
                }
                long time = Config.EVENTS_CASE = DataUtil.parseDateDiff(args[2], true);
                Config.saveConfig();
                Bukkit.broadcastMessage("&6Na serwerze zostal aktywowany event &cDrop skrzynek ze stone &6do &c" + DataUtil.getDate(time));
                return;
            }
            case "turbo": {
                if (args.length <= 2) {
                    p.sendMessage("&c/events turbo all/gracz czas");
                    return;
                }
                if (args[1].equalsIgnoreCase("all")) {
                    if (args.length != 3) {
                        p.sendMessage("/events turbo all czas");
                    }
                    long time = DataUtil.parseDateDiff(args[2], true);
                    Config.EVENTS_TURBO = time;
                    Config.saveConfig();
                    Bukkit.broadcastMessage("&6Na serwerze zostal aktywowany &c&lTurboDrop &6do &c" + DataUtil.getDate(time));
                    return;
                }
                User u = UserManager.getUser(args[1]);
                if (u == null) {
                    p.sendMessage("&cGracz nie istnieje!");
                    return;
                }
                long turboDropHave = 0L;
                long currentTurboDrop = u.getTurboDrop();
                if(currentTurboDrop >System.currentTimeMillis()){
                    turboDropHave = currentTurboDrop-System.currentTimeMillis();
                }
                long givenTurboDrop = DataUtil.parseDateDiff(args[2], true);
                u.setTurboDrop(givenTurboDrop+turboDropHave);
                u.save();
                p.sendMessage("&6Dodales: &c"+DataUtil.secondsToString(givenTurboDrop)+"");
                p.sendMessage("&c&lTurboDrop &6dla gracza &c" + args[1] + " &6do &c" + DataUtil.getDate(u.getTurboDrop()));
                return;
            }
            default: {
                GlobalMessage.usage(p, getUsage());
                break;
            }
        }
    }
}
