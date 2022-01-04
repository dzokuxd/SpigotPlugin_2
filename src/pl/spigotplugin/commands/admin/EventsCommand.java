package pl.spigotplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.statues;
import pl.spigotplugin.configs.core;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;

public class EventsCommand extends PlayerCommand {
    public EventsCommand() { super("events", RankType.HA); }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 2) {
            core.usage(p, "events (<turbo all/gracz czas>/case/kill/beacon) set (czas)");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "beacon": {
                if (args.length <=2) {
                    p.sendMessage(ChatUtil.color("&c/events beacon set czas"));
                    return;
                }
                long time = statues.EVENTS_BEACON = DataUtil.parseDateDiff(args[2], true);
                statues.saveLang();
                Bukkit.broadcastMessage(ChatUtil.color("&fNa serwerze zostal aktywowany event &dDrop beacona za zabicie gracza z gildii &fdo &d" + DataUtil.getDate(time)));
                return;
            }
            case "kill": {
                if (args.length <=2) {
                    p.sendMessage(ChatUtil.color("&c/events kill set czas"));
                    return;
                }
                long time = statues.EVENTS_KILL = DataUtil.parseDateDiff(args[2], true);
                statues.saveLang();
                Bukkit.broadcastMessage(ChatUtil.color("&fNa serwerze zostal aktywowany event &dDrop skrzynek za zabicie gracza z gildii &fdo &d" + DataUtil.getDate(time)));
                return;
            }
            case "case": {
                if (args.length <=2) {
                    p.sendMessage(ChatUtil.color("&c/events case set czas"));
                    return;
                }
                long time = statues.EVENTS_CASE = DataUtil.parseDateDiff(args[2], true);
                statues.saveLang();
                Bukkit.broadcastMessage(ChatUtil.color("&fNa serwerze zostal aktywowany event &dDrop skrzynek ze stone &fdo &d" + DataUtil.getDate(time)));
                return;
            }
            case "turbo": {
                if (args.length <= 2) {
                    p.sendMessage(ChatUtil.color("&c/events turbo all/gracz czas"));
                    return;
                }
                if (args[1].equalsIgnoreCase("all")) {
                    if (args.length != 3) {
                        p.sendMessage(ChatUtil.color("/events turbo all czas"));
                    }
                    long time = DataUtil.parseDateDiff(args[2], true);
                    statues.EVENTS_TURBO = time;
                    statues.saveLang();
                    Bukkit.broadcastMessage(ChatUtil.color("&fNa serwerze zostal aktywowany &d&lTurboDrop &fdo &d" + DataUtil.getDate(time)));
                    return;
                }
                User u = UserManager.getUser(args[1]);
                if (u == null) {
                    p.sendMessage(ChatUtil.color("&cGracz nie istnieje!"));
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
                p.sendMessage(ChatUtil.color("Dodales: &d"+DataUtil.secondsToString(givenTurboDrop)+""));
                p.sendMessage(ChatUtil.color("TurboDrop dla gracza &d" + args[1] + " &fdo &d" + DataUtil.getDate(u.getTurboDrop())));
                return;
            }
            default: {
                core.usage(p, getUsage());
                break;
            }
        }
    }
}
