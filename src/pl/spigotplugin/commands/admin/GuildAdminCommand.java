package pl.spigotplugin.commands.admin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.managers.BanManager;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.Ban;
import pl.spigotplugin.utils.ChatUtil;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.TagUtil;

public class GuildAdminCommand extends PlayerCommand {
    public GuildAdminCommand() {super("ga", "ga", "spigot.guildadmin", "guilda");}

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length < 1) {
            usage(p);
            return;
        }
        Guild g = GuildManager.getGuild(args[1]);
        if (g == null) {
            p.sendMessage("&cGildia nie istnieje!");
        }
        switch (args[0]) {
            case "tp": {
                if (args.length < 3) {
                    p.sendMessage("/ga tp <gildia>");
                    return;
                }
                double y = Bukkit.getWorlds().get(0).getHighestBlockYAt(g.getRegion().getX(), g.getRegion().getZ()) + 1.5f;
                p.teleport(new Location(Bukkit.getWorlds().get(0), g.getRegion().getLocation().getX(), y, g.getRegion().getLocation().getZ()));
                p.sendMessage("&6Zostales przeteleportowany do gildii &7[&c" + g.getTag() + "&7]");
                ChatUtil.sendTitleMessage(p, "&6Witamy w gildii &c" + g.getTag(), "&6Zostales przeteleportowany do gildii &c" + g.getTag(), 30, 70, 40);
            }
            case "ban": {
                if (args.length < 3) {
                    p.sendMessage("/ga ban <gildia> <czas> <powod>");
                    return;
                }
                if (!p.hasPermission("spigotplugin.gaother")) {
                    p.sendMessage("&cNie masz dostepu!");
                    return;
                }
                String reason = "Brak!";
                if (args.length > 3) {
                    reason = StringUtils.join(args, " ", 3, args.length);
                }
                for (String s : g.getMembers()) {
                    Ban b = BanManager.getBan(s);
                    if (b == null) {
                        long time = DataUtil.parseDateDiff(args[2], true);
                        Ban ban = new Ban(s, p.getName(), "(GILDIA) " + reason, time);
                        BanManager.addBan(s, ban);
                    }
                }
                long time = DataUtil.parseDateDiff(args[2], true);
                Bukkit.broadcastMessage("&4Gildia &7[&c" + g.getTag() + "&7] &4zostala tymczasowo zbanowana przez &c" + p.getName() + " &4do: &c" + DataUtil.getDate(time) + " &4powod: &c" + reason + "");
                return;
            }
            case "unban": {
                if (args.length < 1) {
                    p.sendMessage("/ga unban <gildia>");
                    return;
                }
                if (!p.hasPermission("spigotplugin.gaother")) {
                    p.sendMessage("&cNie msaz dostepu!");
                    return;
                }
                for (String s : g.getMembers()) {
                    Ban b = BanManager.getBan(s);
                    if (b != null && b.getReason().contains("(GILDIA)")) {
                        BanManager.unban(b);
                    }
                }
                Bukkit.broadcastMessage("&4Gildia &7[&c" + g.getTag() + "&7] &4zostala odbanowan przez &c" + p.getName() + "&4!");
                return;
            }
            case "usun": {
                if (!p.hasPermission("spigotplugin.gaother")) {
                    p.sendMessage("&cNie msaz dostepu!");
                    return;
                }
                GuildManager.deleteGuild(g);
                Bukkit.broadcastMessage("&4Gildia &7[&c" + g.getTag() + "&7] &4zostala usunieta przez &c" + p.getName() + "&4!");
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    TagUtil.updateBoard(onlinePlayer);
                }
            }//TODO dodac zmiane lidera dodawnie i usuwanie pkt i nic kurwa nie dziala
        }
    }
    private void usage(CommandSender p) {
        p.sendMessage("");
    }
}
