package pl.spigotplugin.commands.player;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pl.spigotplugin.api.PlayerCommand;
import pl.spigotplugin.configs.guild;
import pl.spigotplugin.managers.GuildManager;
import pl.spigotplugin.managers.UserManager;
import pl.spigotplugin.objects.guild.Guild;
import pl.spigotplugin.objects.user.User;
import pl.spigotplugin.utils.DataUtil;
import pl.spigotplugin.utils.TimeUtil;

import java.util.List;

public class GuildInfoCommand extends PlayerCommand {
    public GuildInfoCommand() {super("gildia", "gildia <tag>", "");}

    public static String[] getMemberList(List<String> members) {
        String[] s = new String[members.size()];
        int i = 0;
        for (String u : members) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(u);
            String name = op.getName();
            User uu = UserManager.getUser(op.getName());
            if (uu.isIncognito()) {
                name = "&k" + op.getName();
            }
            s[i] = (op.isOnline() ? "&a" : "&c") + name;
            ++i;
        }
        return s;

    }

    @Override
    public void onCommand(Player p, String[] args) {
        Guild g;
        if (args.length == 0) {
            g = GuildManager.getGuild(p);
        } else {
            g = GuildManager.getGuild(args[0]);
        }
        if (g == null && args.length == 0) {
            p.sendMessage("&cNie posiadasz gildii!");
            return;
        } else if (g == null) {
            p.sendMessage("&cGildia o takim tagu nie istnieje!");
            return;
        }
        p.sendMessage("&7&m-----------[&r &c&l" + g.getTag() + " &7&m-&r &c&l" + g.getName() + " &7]&7&m-----------");
        p.sendMessage("&7\u00bb &6Kordy: &cx"+g.getRegion().getX()+"&7/&cy "+g.getRegion().getZ());
        p.sendMessage("&7\u00bb &6Zalozyciel: &c" + g.getLeader());
        p.sendMessage("&7\u00bb &6Zastepca: &c" + (g.getDeputy() == null || g.getLeader().equalsIgnoreCase("null") ? "Brak" : g.getLeader()));
        p.sendMessage("&7\u00bb &6Punkty: &c" + g.getPoints() + " &6TOP: &c(&6" + "&c)");
        p.sendMessage("&7\u00bb &6Zabojstwa: &c" + g.getKills());
        p.sendMessage("&7\u00bb &6Smierci: &c" + g.getDeaths());
        p.sendMessage("&7\u00bb &6HP: &c" + g.getHp() + " &6Zycia: &c" + g.getLife());
        int size = g.getRegion().getSize() * 2 + 1;
        p.sendMessage("&7\u00bb &6Teren &c" + size + "&7x&c" + size);
        p.sendMessage("&7\u00bb &6Utworzona: &c" + DataUtil.getDate(g.getCreateTime()));
        p.sendMessage("&7\u00bb &6Ochrona tnt: &c" + (g.isProtected() ? DataUtil.secondsToString(g.getCreateTime() + TimeUtil.HOUR.getTime(guild.CUBOID_PROTECTION_HOWHOUR)) : "&cNie"));
        p.sendMessage("&7\u00bb &6Wygasa " + (g.isExits() ? "&6za: &c" + DataUtil.secondsToString(g.getProlong()) : " &cWygasla"));
        p.sendMessage("&7\u00bb &6Czlonkow: &c" + g.getMembers().size() + "&7/&c" + g.getPlayersLimit() + "&7, &6Online: &c" + g.getOnlineMembers().size());
        p.sendMessage("&7\u00bb &6Czlonkowie: " + StringUtils.join(GuildInfoCommand.getMemberList(g.getOnlineMembersNames()), "&7, "));
        p.sendMessage(g.getALlyList().replaceFirst("&6, ", "") + " &7[&c" + g.getAlly().size() + "&7/&c" + 2 + "&7]");
        p.sendMessage("&7\u00bb &6Wojny: &c" + (g.getGuildWar().isEmpty() ? "BRAK" : g.getwojnatags()));
        p.sendMessage("&7&m-----------[&r &c&l" + g.getTag() + " &7&m-&r &c&l" + g.getName() + " &7]&7&m-----------");
    }
}
