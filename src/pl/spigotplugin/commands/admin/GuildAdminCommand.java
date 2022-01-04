package pl.spigotplugin.commands.admin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.enums.RankType;
import pl.spigotplugin.managers.BanManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.Ban;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.GroupUtil;

public class GuildAdminCommand extends PlayerCommand {
    public GuildAdminCommand() {
        super("ga",  RankType.HELPER, "guilda");
    }

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 2) {
            usage(p);
            return;
        }
        Guild g = GuildManager.getGuild(args[1]);
        if (g == null) {
            p.sendMessage("&cGildia nie istniej!");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "tp": {
                double y = Bukkit.getWorlds().get(0).getHighestBlockYAt(g.getRegion().getX(), g.getRegion().getZ()) + 1.5f;
                p.teleport(new Location(Bukkit.getWorlds().get(0), g.getRegion().getLocation().getX(), y, g.getRegion().getLocation().getZ()));
                p.sendMessage("&fZostales przeteleportowany do gildii: &d" + g.getTag());
                return;
            }
            case "zastepca": {
                if (!GroupUtil.have(p, RankType.ADMIN)) {
                    p.sendMessage("&cNie masz dostepu");
                    return;
                }
                if (args.length < 3) {
                    p.sendMessage("/ga zastepca <gildia> <gracz>");
                    return;
                }
                Guild og = GuildManager.getGuild(p);
                if (og == null) {
                    p.sendMessage("&cGracz nie posiada gildii!");
                    return;
                }
                if (!g.isMember(args[2])) {
                    p.sendMessage("&cGracz nie jest w tej gildii!");
                    return;
                }
                if (g.isLeader(args[2])) {
                    p.sendMessage("&cGracz jest liderem gildii!");
                    return;
                }
                if (g.isDeputy(args[2])) {
                    g.setDeputy("Brak");
                    g.putForSave();
                    p.sendMessage( "&fZmieniles zastepce gildii &d"+args[1]+ " &fna &dNikt");
                    return;
                }
                g.setDeputy(args[2]);
                p.sendMessage( "&fZmieniles zastepce gildii &d"+args[1]+ " &fna &d"+args[2]);
                return;
            }
            case "lider": {
                if (!GroupUtil.have(p, RankType.ADMIN)) {
                    p.sendMessage("&cNie masz dostepu");
                    return;
                }
                if (args.length < 3) {
                    p.sendMessage("/ga lider <gildia> <gracz>");
                    return;
                }
                Guild og = GuildManager.getGuild(p);
                if (og == null) {
                    p.sendMessage("&cGracz nie posiada gildii!");
                    return;
                }
                if (!g.isMember(args[2])) {
                    p.sendMessage("&cGracz nie jest w tej gildii!");
                    return;
                }
                if (g.isLeader(args[2])) {
                    p.sendMessage("&cGracz jest liderem gildii!");
                    return;
                }
                g.setLeader(args[2]);
                g.putForSave();
                p.sendMessage( "&fZmieniles lidera gildii &d"+args[1]+ " &fna &d"+args[2]);
                return;
            }
            case "ban": {
                if (!GroupUtil.have(p, RankType.ADMIN)) {
                    p.sendMessage("&cNie masz dostepu!");
                    return;
                }
                String reason = "Brak!";
                if (args.length > 3) {
                    reason = StringUtils.join(args, " ", 3, args.length);
                }
                for (String s : g.getMembers()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"ban " +s+ " "+args[2]+" "+reason);
                }
                long time = DataUtil.parseDateDiff(args[2], true);
                Bukkit.broadcastMessage("&4Gildia " + g.getTag() + " zostala tymczasowo zbanowana przez " + p.getName() + " do: " + DataUtil.getDate(time) + " powod: " + reason + "");
                return;
            }
            case "unban": {
                if (!GroupUtil.have(p, RankType.ADMIN)) {
                    p.sendMessage("&cNie masz dostepu!");
                    return;
                }
                for (String s : g.getMembers()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"unban " +s);
                }
                Bukkit.broadcastMessage("Gildia " + g.getTag() + " zostala odbanowan przez " + p.getName());
            }
        }
    }

    private void usage(Player p) {
        p.sendMessage("ga tp <tag>");
        p.sendMessage("ga ban <tag> <czas> <powod>");
        p.sendMessage("ga unban <tag>");
        p.sendMessage("ga usun <tag>");
        p.sendMessage("ga lider <tag> <nick>");
        p.sendMessage("ga zastepca <tag> <nick>");
        p.sendMessage("ga points <tag> <ilosc>");
        p.sendMessage("ga kills <tag> <ilosc>");
        p.sendMessage("ga deaths <tag> <ilosc>");
        p.sendMessage("ga tnt?");
    }
}
